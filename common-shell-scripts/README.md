
## 生成 SSL 的配置 - generate-ssl.sh

该脚本主要的产出是 4 个文件，分别是：server.keystore.jks、server.truststore.jks、client.keystore.jks 和 client.truststore.jks。
你需要把以 server 开头的两个文件，拷贝到集群中的所有 Broker 机器上，把以 client 开头的两个文件，拷贝到所有要连接 Kafka 集群的客户端应用程序机器上。