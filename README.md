# 系统环境
* jdk25
* docker
## 切换分支
`git switch ob`

## 启动容器
`docker compose up -d`

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


