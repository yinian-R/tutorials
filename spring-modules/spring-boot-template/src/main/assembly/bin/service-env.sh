#!/bin/bash


base_dir=`cd $(dirname $0)/..; pwd`

SERVICE_NAME="spring-boot-template"
SERVICE_VERSION="0.0.1-SNAPSHOT"
JAR_NAME="${SERVICE_NAME}-${SERVICE_VERSION}.jar"

SPRING_CONFIG_OPTS="--spring.profiles.active=pro"

# Log directory to use
#if [ "x$LOG_DIR" = "x" ]; then
#  LOG_DIR="$base_dir/logs"
#fi

# Set Debug options if enabled, default false
#SERVICE_DEBUG=false

if [ "x$SERVICE_HEAP_OPTS" = "x" ]; then
  SERVICE_HEAP_OPTS="-Xms256m -Xmx2g"
fi

