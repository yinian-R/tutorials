使用JDK工具，keytool生成证书文件

```
keytool -genkey -alias 别名 
 -storetype 创库类型 
 -keysize 长度
 -keystore 文件名
 -validity 有效期

keytool -genkey -alias springsample -storetype PKCS12 -keysize 2048 -keystore springsample.p12 -validity 365
```


