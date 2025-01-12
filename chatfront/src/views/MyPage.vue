<template>
    <v-container>
        <v-row justify="center">
            <v-col cols="12" md="8">
                <v-card>
                    <v-card-title class="text-center text-h5">
                        내 채팅 목록
                    </v-card-title>
                    <v-card-text>
                        <v-table>
                            <thead>
                                <tr>
                                    <th>채팅방 이름</th>
                                    <th>마지막 메시지</th>
                                    <th>액션</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="chat in chatList" :key="chat.roomId">
                                    <td>{{ chat.roomName }}</td>
                                    <td>{{ chat.lastMessage }}</td>
                                    <td>
                                        <v-btn color="primary" @click="enterChat(chat.roomId)">
                                            입장
                                        </v-btn>
                                    </td>
                                </tr>
                            </tbody>
                        </v-table>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            chatList: [] // 채팅 목록 데이터를 저장할 배열
        };
    },
    async created() {
        try {
            const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/chat/list`);
            this.chatList = response.data.result; // 서버에서 반환된 채팅 목록
        } catch (error) {
            console.error("Failed to load chat list:", error);
        }
    },
    methods: {
        enterChat(roomId) {
            // 채팅방 입장 로직 (예: 채팅방 화면으로 이동)
            this.$router.push({ name: "ChatPage", params: { roomId } });
        }
    }
};
</script>

<style>
</style>
