# Perform the extraction in a separate builder container
FROM eclipse-temurin:25-jre-alpine AS builder
WORKDIR /builder

# This points to the built jar file in the target folder
# Adjust this to 'build/libs/*.jar' if you're using Gradle
ARG JAR_FILE=./build/libs/*.jar

# Copy the jar file to the working directory and rename it to application.jar
COPY ${JAR_FILE} application.jar

# Extract the jar file using an efficient layout
RUN java -Djarmode=tools -jar application.jar extract --layers --destination extracted

# This is the runtime container
FROM eclipse-temurin:25-jre-alpine

# 设置时区为东八区（解决日志时间差8小时问题）
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# ---------- 创建非 root 用户（安全基线） ----------
# 固定 UID/GID = 1001，避免与宿主机挂载卷权限冲突
RUN addgroup -g 1001 appgroup && adduser -u 1001 -G appgroup -s /bin/sh -D appuser

WORKDIR /application

# Copy the extracted jar contents from the builder container into the working directory in the runtime container
# Every copy step creates a new docker layer
# This allows docker to only pull the changes it really needs
COPY --from=builder --chown=appuser:appgroup /builder/extracted/dependencies/ ./
COPY --from=builder --chown=appuser:appgroup /builder/extracted/spring-boot-loader/ ./
COPY --from=builder --chown=appuser:appgroup /builder/extracted/snapshot-dependencies/ ./
COPY --from=builder --chown=appuser:appgroup /builder/extracted/application/ ./

# 创建临时目录并赋权（Spring Boot 默认临时文件目录）
RUN mkdir -p /tmp ./logs && chown -R appuser:appgroup /tmp ./logs

# ---------- 切换到非 root 用户运行（安全要求） ----------
USER appuser

# Execute the AOT cache training run
#RUN java -XX:AOTCacheOutput=app.aot -Dspring.context.exit=onRefresh -jar application.jar

EXPOSE 9090

# Start the application jar with AOT cache enabled - this is not the uber jar used by the builder
# This jar only contains application code and references to the extracted jar files
# This layout is efficient to start up and AOT cache friendly
#ENTRYPOINT ["java", "-XX:AOTCache=app.aot", "-jar", "application.jar"]
ENTRYPOINT ["java", "-jar", "application.jar"]