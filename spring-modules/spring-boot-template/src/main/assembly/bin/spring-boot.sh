#!/usr/bin/env bash
# 修复文件
sed -i 's/\r$//' $(cd `dirname $0`; pwd)/spring-boot.sh
sed -i 's/\r$//' $(cd `dirname $0`; pwd)/setenv.conf

# 脚本路径
APPLICATION_HOME=$(cd `dirname $0`;pwd)
# shellcheck disable=SC1090
. "$APPLICATION_HOME/setenv.conf"

# 项目主目录
cd $APPLICATION_HOME/..
APPLICATION_HOME=$(pwd)

JAR_FILE=$APPLICATION_HOME/$JAR_NAME

# CONFIG_OPTS
CONFIG_OPTS="--spring.profiles.active=$PROFILE"
CONFIG_OPTS="${CONFIG_OPTS} $SPRING_BOOT_OPTS"

PID_NUM=

function start() {
  findPid
  if [ ${PID_NUM} ]; then
    echo ${JAR_NAME} has been running!!!
  else
    java $JAVA_OPTS -jar $JAR_FILE $CONFIG_OPTS >/dev/null 2>&1 &
    echo start finished. pid num: $!
  fi
}

function stop() {
  findPid
  if [ ${PID_NUM} ]; then
    echo pid num: ${PID_NUM}
    echo kill ${JAR_NAME}
    kill ${PID_NUM}
    echo 'stopping...'
    while [ -n "$PID_NUM" ]; do
      sleep 1
      findPid
    done
    echo stop finished.
  else
    echo ${JAR_NAME} not running
  fi
}

function status() {
  findPid
  if [ ${PID_NUM} ]; then
    echo ${JAR_NAME} is running: ${PID_NUM}
    echo '-----------------------------------'
    ps aux | grep "${JAR_FILE}" | grep -v "grep"
  else
    echo ${JAR_NAME} not running
  fi
}

function deleteLogs() {
  rm -rf $APPLICATION_HOME/logs/*
}

function findPid() {
  PID_NUM=$(ps aux | grep "$JAR_FILE" | grep -v "grep" | awk '{print $2}')
}

case "$1" in

'start')
  start
  ;;

'stop')
  stop
  ;;

'restart')
  stop
  start
  ;;

'status')
  status
  ;;

'list')
  status
  ;;
*)
  echo "Usage: $0 { start | stop | restart | status or list}"
  exit 1
  ;;
esac
