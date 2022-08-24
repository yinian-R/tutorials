<template>
  <h1>web socket</h1>
  <div class="web-socket">
    <p v-for="item in eventList" v-bind:key="item">
      {{item}}}
    </p>
  </div>
</template>

<script>
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

export default {
  name: "SocketTemplate",
  data() {
    return {
      stompClient: null,
      stompLiveInterval: null,
      eventList: []
    };
  },
  created() {
    this.initWebSocket();
    // 使用定时器作为 socket 重连机制
    this.stompLiveInterval = setInterval(() => {
      if (this.stompClient === null || this.stompClient.connected === false) {
        this.initWebSocket();
      }
    }, 3000);
  },
  methods: {
    initWebSocket() {
      let socket = new SockJS('http://127.0.0.1:10009/videocloud-subway-proxy/websocket');
      this.stompClient = Stomp.over(socket);
      this.stompClient.hasDebug = false;
      this.stompClient.connect({}, () => {
        // 订阅事件
        this.stompClient.subscribe('/topic/station/person/stat/alarm', (info) => {
          console.info("socket event:" + info)
          this.eventList.push(info)
        });
      }, () => {
        console.error(' websocket connect error');
      });
    },
  }
}
</script>

<style scoped>

</style>