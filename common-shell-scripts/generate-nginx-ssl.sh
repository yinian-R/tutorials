#!/bin/bash

# 设置变量
BASE_DIR=`cd $(dirname $0)/; pwd`
DOMAIN="www.custom.com"
CERT_DIR="$BASE_DIR/ssl"
mkdir -p $CERT_DIR

# 创建目录（如果不存在）
mkdir -p $CERT_DIR

# 生成私钥
openssl genrsa -out $CERT_DIR/${DOMAIN}.key 2048

# 生成自签名证书
openssl req -new -x509 -key $CERT_DIR/${DOMAIN}.key -out $CERT_DIR/${DOMAIN}.crt -days 365 <<EOF
CN
Guangdong
Guangzhou
Your Organization
IT Department
${DOMAIN}
your-email@example.com
EOF

echo "证书已生成至 ${CERT_DIR}/${DOMAIN}.key 和 ${CERT_DIR}/${DOMAIN}.crt"
