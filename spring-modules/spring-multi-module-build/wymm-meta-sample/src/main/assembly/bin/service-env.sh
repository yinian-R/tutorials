#!/bin/bash


base_dir=`cd $(dirname $0)/..; pwd`

SERVICE_NAME="wymm-meta-sample"
SERVICE_VERSION="0.0.1-SNAPSHOT"
JAR_NAME="${SERVICE_NAME}-${SERVICE_VERSION}.jar"

# 优先级：命令>spring配置文件（application.yml）
SPRING_CONFIG_OPTS="--spring.profiles.active=pro"

# 设置 Spring 配置目录路径或配置文件路径。默认读取路径 ./config
#SPRING_CONFIG_OPTS="${SPRING_CONFIG_OPTS} --spring.config.location=file:./config/"

# 设置日志配置外部文件路径，也可以写在 Spring 配置文件 application.yml 中
#SPRING_CONFIG_OPTS="${SPRING_CONFIG_OPTS} --logging.config=file:./config/logback-spring.xml"

# 设置程序是否开启 remote jvm debug, default false
#SERVICE_DEBUG=false

if [ "x$SERVICE_HEAP_OPTS" = "x" ]; then
  SERVICE_HEAP_OPTS="-Xms256m -Xmx2g"
fi

