//package org.example.demo.db.config;
//
//import io.grpc.netty.GrpcSslContexts;
//import io.grpc.netty.NettyServerBuilder;
//import io.netty.handler.ssl.ClientAuth;
//import io.netty.handler.ssl.SslContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.grpc.server.ServerBuilderCustomizer;
//
//import javax.net.ssl.KeyManagerFactory;
//import javax.net.ssl.TrustManagerFactory;
//import java.io.FileInputStream;
//import java.security.KeyStore;
//
//@Configuration
//public class BootGrpcSSLConfig {
//
//    @Bean
//    public ServerBuilderCustomizer<NettyServerBuilder> grpcServerBuilderCustomizer() {
//        return builder -> {
//            try {
//                // 1. 初始化 KeyManager (服务端私钥+证书)
//                KeyManagerFactory kmf = createKeyManagerFactory();
//
//                // 2. 初始化 TrustManager (CA证书，用于验证客户端)
//                TrustManagerFactory tmf = createTrustManagerFactory();
//
//                // 3. 构建 SslContext
//                SslContext sslContext = GrpcSslContexts.forServer(kmf).trustManager(tmf)
//                        .clientAuth(ClientAuth.REQUIRE) // 开启双向认证
//                        .protocols("TLSv1.3")           // 强制 TLS 1.3
//                        .build();
//
//                // 4. 应用 SSL 上下文
//                builder.sslContext(sslContext);
//            } catch (Exception e) {
//                throw new RuntimeException("gRPC SSL configuration failed", e);
//            }
//        };
//    }
//
//    private KeyManagerFactory createKeyManagerFactory() throws Exception {
//        KeyStore ks = KeyStore.getInstance("PKCS12");
//        try (FileInputStream fis = new FileInputStream("path/to/server.p12")) {
//            ks.load(fis, "password".toCharArray());
//        }
//        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//        kmf.init(ks, "password".toCharArray());
//        return kmf;
//    }
//
//    private TrustManagerFactory createTrustManagerFactory() throws Exception {
//        KeyStore ts = KeyStore.getInstance("JKS"); // 信任库常用 JKS 或 PKCS12
//        try (FileInputStream fis = new FileInputStream("path/to/truststore.jks")) {
//            ts.load(fis, "password".toCharArray());
//        }
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//        tmf.init(ts);
//        return tmf;
//    }
//}
