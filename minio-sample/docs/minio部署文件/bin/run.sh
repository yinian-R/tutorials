#!/bin/bash

#----------------------------------------------------------
# arg
#----------------------------------------------------------
base_dir=`cd $(dirname $0)/..; pwd`

SERVICE_NAME="minio"

export MINIO_ACCESS_KEY=minio
export MINIO_SECRET_KEY=Suntek123

#----------------------------------------------------------
# setup
#----------------------------------------------------------
# 判断是否存在日志目录，若不存在则创建并建立软连接
function setup() {
  if [ -d "/data/minio/log" ]; then
    echo "info: /data/minio/log exists"
  else
    mkdir -p /data/minio/log
  fi
}

#----------------------------------------------------------
# findPid
#----------------------------------------------------------
PIDS=""
function findPid() {
  PIDS=$(ps ax | grep -i "${base_dir}/${SERVICE_NAME}" | grep server | grep -v grep | awk '{print $1}')
}

#----------------------------------------------------------
# function print usage
#----------------------------------------------------------
if [ "$1" = "start" ] ; then
  setup
  findPid
  if [ "x${PIDS}" != 'x' ]; then
    echo ${SERVICE_NAME} has been running!!!!!
    exit 1
  fi

  nohup ${base_dir}/minio server /data/minio --config-dir ${base_dir}/config --console-address :22122 --address :23000 >> /data/minio/log/minio.log 2>&1 &

  findPid
  if [ "x${PIDS}" = 'x' ]; then
    echo error! ${SERVICE_NAME} not running!!!!!
  else
    echo "start finish PID: $PIDS"
  fi

elif [ "$1" = "stop" ] ; then
  findPid
  if [ "x${PIDS}" != 'x' ]; then
    echo pid: $PIDS
    echo kill -9 ${SERVICE_NAME}
    kill -9 ${PIDS}
    echo 'stopping...'
    while [ -n "$PIDS" ]; do
      sleep 3
      findPid
    done
    echo stop finished.
  else
    echo ${SERVICE_NAME} not running!!!!!
  fi
elif [ "$1" = "status" ] ; then
  findPid
  if [ "x${PIDS}" = 'x' ]; then
    echo ${SERVICE_NAME} not running
  else
    echo ${SERVICE_NAME} is running: ${PIDS}
    echo '-----------------------------------'
    ps ax | grep -i "${base_dir}/${SERVICE_NAME}" | grep server | grep -v grep
  fi
else
    printf "Usage: $0 <command>
  Optional command:
       start             Start Service
       stop              Stop Service, waiting up to 3 seconds for the process to end
       status            show Service status
"
fi