# 系统环境
* jdk25
* docker

### 数据库准备
**启动mysql容器**  
`docker compose up mysql-9 -d`

## 项目编译
`.\gradlew.bat clean build -x test`

## 创建表
* 路径: boot-db/src/test/resources/schema.sql
## 导入数据
`.\gradlew.bat  test --tests BootDemoDBApplicationTest.dbInit`

## 运行protobuf插件
`.\gradlew.bat :boot-api:generateProto`

## 运行grpc server
`.\gradlew.bat :boot-db:bootRun`

## 运行grpc client
`.\gradlew.bat :boot-web:bootRun`

## 端口

| 应用 | 端口 | 说明|
|------|------|-----|
|boot-db|9090 |gRPC服务端|
|boot-web|8080|gRPC客户端|
|grafana|3000|[grafana首页](http://localhost:3000/)|
|prometheus|9000|[指标查询](http://localhost:9000/query)|

