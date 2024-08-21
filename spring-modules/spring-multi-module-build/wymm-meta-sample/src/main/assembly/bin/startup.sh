#!/bin/bash

base_dir=$(dirname $0)

# --name    程序通用命名，目前仅 GC 日志名称使用到
# --loggc   是否打印 GC 日志
EXTRA_ARGS=${EXTRA_ARGS-' --name Server --loggc'}
COMMAND=$1

if [[ "$1" = "-h" || "$1" = "-help" || "$1" = "--help" ]]; then
  echo "USAGE:"
  echo "$0                                Start Catalina in a separate window"
  echo "$0           --daemon=false       Start Catalina in the current window"
  echo "$0       -h  --help"
  exit 1
elif [ "$1" = "--daemon=false" ] ; then
  exec $base_dir/service.sh start $EXTRA_ARGS
else
  EXTRA_ARGS="--daemon "$EXTRA_ARGS
  exec $base_dir/service.sh start $EXTRA_ARGS
fi