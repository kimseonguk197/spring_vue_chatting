<template>
    <v-container>
        <v-row>
            <v-col>
                <v-card>
                    <v-card-title class="text-center text-h5">
                        회원목록
                    </v-card-title>
                    <v-card-text>
                        <v-data-table
                            :headers="tableHeaders"
                            :items="memberList"
                        >
                            <template v-slot:[`item.action`]="{ item }">
                                <v-btn color="primary" @click="startChat(item.id)">
                                    채팅
                                </v-btn>
                            </template>
                        </v-data-table>
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
            tableHeaders: [
                { text: 'ID', value: 'id', align: 'start' },
                { text: '이름', value: 'name', align: 'start' },
                { text: 'EMAIL', value: 'email', align: 'start' },
                { text: '채팅', value: 'action', align: 'center' },
            ],
            memberList: [],
        };
    },
    async created() {
        try {
            const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/member/list`);
            this.memberList = response.data.result;
        } catch (e) {
            console.log(e);
        }
    },
    methods: {
        async startChat(memberId) {
            // 채팅 시작 로직 (예: 채팅 페이지로 이동하거나 WebSocket 연결 등)
            console.log(`채팅 시작: 회원 ID ${memberId}`);
            // 예: 채팅 페이지로 이동
            const response = await axios.post(
                `${process.env.VUE_APP_API_BASE_URL}/chat/room/private?otherMemberId=${memberId}`,
                null // POST 요청의 본문은 비워둠
            );
            this.roomId = response.data; // 채팅방 ID 설정
            this.$router.push(`/chatpage/${this.roomId}`);
        },
    },
};
</script>

<style>
</style>
