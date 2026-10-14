# 系统环境
* jdk25
* docker

### 数据库准备
**启动mysql容器**  
`docker compose up mysql-9 -d`

**新建数据库**
CREATE DATABASE `boot-db`;

**创建表**
* 路径: boot-db/src/test/resources/schema.sql

## 项目编译
`.\gradlew.bat clean build -x test`

## 导入数据
`.\gradlew.bat  test --tests BootDemoDBApplicationTest.dbInit`

## 运行protobuf插件
`.\gradlew.bat :boot-api:generateProto`

## 运行grpc server
`.\gradlew.bat :boot-db:bootRun`

## 运行grpc client
`.\gradlew.bat :boot-web:bootRun`


