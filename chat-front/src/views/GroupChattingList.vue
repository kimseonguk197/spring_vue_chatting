<template>
    <v-container>
        <v-row>
            <v-col>
                <v-card>
                    <v-card-title class="text-center text-h5">
                        채팅방목록
                        <div class="d-flex justify-end">
                            <v-btn color="secondary" @click="showCreateRoomModal = true">
                                채팅방 생성
                            </v-btn>
                        </div>
                    </v-card-title>
                    <v-card-text>
                        <v-table>
                            <thead>
                                <tr>
                                    <th>방번호</th>
                                    <th>방제목</th>
                                    <th>채팅</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="chat in chatRoomList" :key="chat.roomId">
                                    <td>{{chat.roomId}}</td>
                                    <td>{{chat.roomName}}</td>
                                    <td>
                                        <v-btn color="primary" @click="joinChatRoom(chat.roomId)">
                                            참여하기
                                        </v-btn>
                                    </td>
                                </tr>
                            </tbody>
                        </v-table>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
        <v-dialog v-model="showCreateRoomModal" max-width="500px">
            <v-card>
                <v-card-title class="text-h6">
                    채팅방 생성
                </v-card-title>
                <v-card-text>
                    <v-text-field label="방제목" v-model="newRoomTitle"/>
                </v-card-text>
                <v-card-actions>
                    <v-btn color="grey" @click="showCreateRoomModal = false">
                        취소
                    </v-btn>
                    <v-btn color="primary" @click="createChatRoom">
                        생성
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

    </v-container>
</template>

<script>
import axios from 'axios';
export default{
    data(){
        return {
            chatRoomList: [],
            showCreateRoomModal: false,
            newRoomTitle: "",

        }
    },
    async created(){
        this.loadChatRoom();
    },
    methods: {
        async joinChatRoom(roomId){
            await axios.post(`${process.env.VUE_APP_API_BASE_URL}/chat/room/group/${roomId}/join`);
            this.$router.push(`/chatpage/${roomId}`);
        },
        async createChatRoom(){
            await axios.post(`${process.env.VUE_APP_API_BASE_URL}/chat/room/group/create?roomName=${this.newRoomTitle}`, null);
            this.showCreateRoomModal = false;
            this.loadChatRoom();
            
        },
        async loadChatRoom(){
            const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/chat/room/group/list`);
            this.chatRoomList = response.data;
        }
    },
}
</script>