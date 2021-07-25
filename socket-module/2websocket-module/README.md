


- [x] 入门
- 入门参考 WebSocket：https://www.baeldung.com/rest-vs-websockets

示例： 1gs-messaging-stomp-websocket
  
- [ ] 根据权限推送 

示例：2websockets-send-message-to-user

```
Topics – 对任何客户或用户开放的常见对话或聊天主题，
Queues – 为特定用户及其当前会话保留
Endpoints – 通用端点
在STOMP规范中，目的地的含义被有意地保留为不透明的。它可以是任何字符串，并且完全由STOMP服务器定义它们支持的目的地的语义和语法。
但是，目标像/topic/所在的路径字符串一样是非常常见的。/topic/表示发布-订阅（一对多）和/queue/表示点对点（一对一）消息交换。
```
需要注意的是， 我们必须使用 Queues 向特定用户发送消息，因为 Topics 和 Endpoints 不支持此功能
- [ ] 定时推送
- [ ] 总结

