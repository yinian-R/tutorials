#!/bin/bash

source "`dirname "$0"`"/service-env.sh

if [ -r "/etc/systemd/system/$SERVICE_NAME.service" ]; then
  echo "error: /etc/systemd/system/$SERVICE_NAME.service already exists"
else
cat > /etc/systemd/system/$SERVICE_NAME.service <<EOF
[Unit]
Description=$SERVICE_NAME
After=network-online.target
Wants=network-online.target

[Service]
Type=forking
WorkingDirectory=$base_dir
ExecStart=/bin/bash -c "source /etc/profile && /bin/sh $base_dir/bin/startup.sh"
ExecStop=/bin/bash -c "source /etc/profile  && /bin/sh $base_dir/bin/shutdown.sh"
ExecStartPost=/usr/bin/sleep 5
Restart=always
RestartSec=60s

[Install]
WantedBy=multi-user.target
EOF

  echo "created /etc/systemd/system/$SERVICE_NAME.service"
  systemctl daemon-reload;
  systemctl start $SERVICE_NAME.service;
  systemctl enable $SERVICE_NAME.service;
  echo "开机自启动功能:"
  systemctl is-enabled $SERVICE_NAME.service;
fi
