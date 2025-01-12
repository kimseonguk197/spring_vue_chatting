<template>
  <v-container>
      <v-row justify="center">
          <v-col cols="12" md="8">
              <v-card>
                  <v-card-title class="text-center text-h5">채팅</v-card-title>
                  <v-card-text>
                      <div class="chat-box">
                          <!-- 채팅 메시지 표시 -->
                          <div
                            v-for="(msg, index) in messages"
                            :key="index"
                            :class="['chat-message', msg.sender === this.sender ? 'sent' : 'received']"
                            >
                          <strong>{{ msg.sender }}:</strong> {{ msg.content }}
                        </div>
                      </div>
                      <!-- 메시지 입력 -->
                      <v-text-field
                          v-model="newMessage"
                          label="메시지 입력"
                          outlined
                          dense
                          @keyup.enter="sendMessage"
                      />
                      <v-btn color="primary" block @click="sendMessage">전송</v-btn>
                  </v-card-text>
              </v-card>
          </v-col>
      </v-row>
  </v-container>
</template>

<script>
// import axios from "axios";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  data() {
      return {
          stompClient: null,
          messages: [], // 모든 메시지 (이전 메시지 + 실시간 메시지)
          newMessage: "",
          roomId: null, // 채팅방 ID
          sender: null,
      };
  },
  async created() {
      // memberId를 라우터 params에서 가져오기
      this.roomId = this.$route.params.roomId;
      console.log("채팅방 roomId iD : ", this.roomId)

      // // 이전 채팅 기록 가져오기
      // try {
      //     const response = await axios.get(
      //         `${process.env.VUE_APP_API_BASE_URL}/chat/history/${this.roomId}`
      //     );
      //     this.messages = response.data.result; // 이전 메시지 설정
      // } catch (error) {
      //     console.error("Failed to load chat history:", error);
      // }

      // WebSocket 연결
      this.connectWebSocket();
  },
  beforeUnmount() {
      this.disconnect();
  },
  methods: {
      connectWebSocket() {
          if (this.stompClient && this.stompClient.connected) return;

          const socket = new SockJS("http://localhost:8080/ws");
          this.stompClient = Stomp.over(socket);

          const token = localStorage.getItem("token");

          this.stompClient.connect(
              { Authorization: `Bearer ${token}` },
              () => {
                  console.log("Connected to WebSocket");
                  // 채팅방 구독
                  this.stompClient.subscribe(`/sub/messages/${this.roomId}`, (message) => {
                      const parsedMessage = JSON.parse(message.body);
                      this.messages.push(parsedMessage); // 새로운 메시지 추가
                  });
              }
          );
      },
      sendMessage() {
          if (this.newMessage.trim() === "") return;
          this.sender = localStorage.getItem("email")
          const message = {
              content: this.newMessage,
              sender: this.sender, // 현재 사용자 이름
          };

          // 서버로 메시지 전송
          this.stompClient.send(`/pub/${this.roomId}`, JSON.stringify(message));
          this.newMessage = ""; // 입력창 초기화
      },
      disconnect() {
          if (this.stompClient && this.stompClient.connected) {
              this.stompClient.unsubscribe(`/sub/messages/${this.roomId}`);
              this.stompClient.disconnect();
          }
      },
  },
};
</script>

<style scoped>
.chat-box {
  height: 300px;
  overflow-y: auto;
  border: 1px solid #ddd;
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 5px;
}
.chat-message {
  margin-bottom: 10px;
}

.sent {
    margin-left: auto;
    text-align: right;
}
  
.received {
margin-right: auto;
text-align: left;
}
  
</style>
