### 文件大小
```
message.max.bytes broker 全局设置 topic 接收文件大小
max.message.bytes  topic 设置 topic 接收文件大小
max.request.size producer 设置发送消息限制大小，超过的发送失败

# 发送超大消息
cat test.txt | ./kafka-console-producer.sh  --broker-list 172.23.9.53:9092 --sync --topic TEST  --producer-property max.request.size=10485760

```
