# 基于官方的 Java 运行环境
FROM xiyangai/alpine-jre17

# 指定维护者信息
LABEL maintainer="wkk@qq.com"

# 在镜像中创建一个目录存放我们的应用
VOLUME /opt/app
workdir /opt/app

# 将jar包添加到容器中并更名为app.jar
ADD target/service-neo4j.jar service-neo4j.jar

# 暴露容器内的端口给外部访问
EXPOSE 8081

# 定义环境变量
ENV JAVA_OPTS=""

# 在容器启动时运行jar包
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /opt/app/service-neo4j.jar

# docker build -t service-neo4j:1.0.0 .
# docker run --name service-neo4j -p 8096:8081 -d service-neo4j:1.0.0