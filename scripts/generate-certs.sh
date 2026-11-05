#!/bin/bash

# ==============================================================================
# PEM 证书生成脚本 (适用于 Spring Boot gRPC / mTLS)
# ==============================================================================

# 配置变量
VALIDITY_DAYS=3650          # 证书有效期（天）
KEY_SIZE=2048               # RSA 密钥长度
OUTPUT_DIR="./ssl-certs"    # 输出目录

# 颜色输出
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${GREEN}开始生成 PEM 证书...${NC}"

# 1. 创建输出目录
mkdir -p $OUTPUT_DIR
cd $OUTPUT_DIR

# ==============================================================================
# 步骤 1: 生成 CA (Certificate Authority) 根证书
# ==============================================================================
echo "[-] 生成 CA 私钥和根证书..."

# 生成 CA 私钥 (PKCS#8 格式，无密码)
openssl genrsa -out ca.key $KEY_SIZE 2>/dev/null
# 转换为 PKCS#8 格式 (Java/Spring Boot 推荐格式)
openssl pkcs8 -topk8 -inform PEM -outform PEM -in ca.key -out ca-key-pkcs8.pem -nocrypt

# 生成自签名 CA 根证书
openssl req -new -x509 -key ca.key -out ca-cert.pem -days $VALIDITY_DAYS \
    -subj "/C=CN/ST=Beijing/L=Beijing/O=MyCompany/OU=Dev/CN=MyRootCA"

echo -e "${GREEN}[+] CA 证书生成完成: ca-cert.pem${NC}"

# ==============================================================================
# 步骤 2: 生成服务端 (Server) 证书
# ==============================================================================
echo "[-] 生成服务端私钥和证书..."

# 生成服务端私钥
openssl genrsa -out server.key $KEY_SIZE 2>/dev/null
# 转换为 PKCS#8 格式
openssl pkcs8 -topk8 -inform PEM -outform PEM -in server.key -out server-key-pkcs8.pem -nocrypt

# 生成服务端 CSR (证书签名请求)
# 注意：CN 应设置为服务端的域名或 IP，SAN 扩展用于支持多域名/IP
openssl req -new -key server.key -out server.csr \
    -subj "/C=CN/ST=Beijing/L=Beijing/O=MyCompany/OU=Service/CN=localhost" \
    -addext "subjectAltName=DNS:localhost,DNS:*.local,IP:127.0.0.1,IP:0.0.0.0"

# 使用 CA 证书签发服务端证书
openssl x509 -req -in server.csr -CA ca-cert.pem -CAkey ca.key \
    -CAcreateserial -out server-cert.pem -days $VALIDITY_DAYS \
    -extfile <(printf "subjectAltName=DNS:localhost,DNS:*.local,IP:127.0.0.1,IP:0.0.0.0")

echo -e "${GREEN}[+] 服务端证书生成完成: server-cert.pem, server-key-pkcs8.pem${NC}"

# ==============================================================================
# 步骤 3: 生成客户端 (Client) 证书 (用于 mTLS)
# ==============================================================================
echo "[-] 生成客户端私钥和证书..."

# 生成客户端私钥
openssl genrsa -out client.key $KEY_SIZE 2>/dev/null
# 转换为 PKCS#8 格式
openssl pkcs8 -topk8 -inform PEM -outform PEM -in client.key -out client-key-pkcs8.pem -nocrypt

# 生成客户端 CSR
openssl req -new -key client.key -out client.csr \
    -subj "/C=CN/ST=Beijing/L=Beijing/O=MyCompany/OU=Client/CN=client-app"

# 使用 CA 证书签发客户端证书
openssl x509 -req -in client.csr -CA ca-cert.pem -CAkey ca.key \
    -CAcreateserial -out client-cert.pem -days $VALIDITY_DAYS

echo -e "${GREEN}[+] 客户端证书生成完成: client-cert.pem, client-key-pkcs8.pem${NC}"

# ==============================================================================
# 步骤 4: 清理临时文件 & 验证
# ==============================================================================
rm -f *.csr *.srl

echo ""
echo "=================================================="
echo "证书生成完毕！文件位于: $OUTPUT_DIR"
echo "=================================================="
echo "文件说明:"
echo "  ca-cert.pem           : CA 根证书 (信任库)"
echo "  server-cert.pem       : 服务端公钥证书"
echo "  server-key-pkcs8.pem  : 服务端私钥 (PKCS#8 格式，Spring Boot 推荐)"
echo "  client-cert.pem       : 客户端公钥证书"
echo "  client-key-pkcs8.pem  : 客户端私钥 (PKCS#8 格式，Spring Boot 推荐)"
echo "=================================================="

# 简单验证：检查私钥和证书是否匹配 (Modulus MD5)
echo "[-] 验证服务端密钥与证书匹配性..."
SERVER_KEY_MD5=$(openssl rsa -in server-key-pkcs8.pem -noout -modulus 2>/dev/null | openssl md5)
SERVER_CERT_MD5=$(openssl x509 -in server-cert.pem -noout -modulus | openssl md5)

if [ "$SERVER_KEY_MD5" == "$SERVER_CERT_MD5" ]; then
    echo -e "${GREEN}[+] 服务端密钥与证书匹配成功${NC}"
else
    echo -e "${RED}[-] 警告：服务端密钥与证书不匹配！${NC}"
fi

echo "[-] 验证客户端密钥与证书匹配性..."
CLIENT_KEY_MD5=$(openssl rsa -in client-key-pkcs8.pem -noout -modulus 2>/dev/null | openssl md5)
CLIENT_CERT_MD5=$(openssl x509 -in client-cert.pem -noout -modulus | openssl md5)

if [ "$CLIENT_KEY_MD5" == "$CLIENT_CERT_MD5" ]; then
    echo -e "${GREEN}[+] 客户端密钥与证书匹配成功${NC}"
else
    echo -e "${RED}[-] 警告：客户端密钥与证书不匹配！${NC}"
fi
