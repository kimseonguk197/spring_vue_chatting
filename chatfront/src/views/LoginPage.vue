<template>
    <v-container>
        <v-row justify="center">
            <v-col cols="12" sm="2" md="4">
                <v-card>
                    <v-card-title class="text-h5 text-center">
                        로그인
                    </v-card-title>
                    <v-card-text>
                        <v-form @submit.prevent="doLogin">
                            <v-text-field
                              label="email"
                              v-model="email"
                              prepend-icon="mdi-email"
                              type="email"
                              required                            
                            ></v-text-field>
                            <v-text-field
                             label="패스워드"
                             v-model="password"
                             prepend-icon="mdi-lock"
                             type="password"
                             required
                            ></v-text-field>
                            <v-row>
                                <v-col cols="6">
                                    <v-btn color="red" @click="showPasswordModal" block>비밀번호 변경</v-btn>
                                </v-col>   
                                <v-col cols="6">
                                    <v-btn type="submit" color="primary" block>로그인</v-btn> 
                                </v-col>  
                            </v-row>
                        </v-form>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>

        <!-- resetPassword가 true가 될때 해당 모달창이 보여짐 -->
        <!-- @update:dialog는 modal컴포넌트가 update:dialog라는 이벤트를 발생시킬 때 실행될 이벤트 핸들러를 정의 -->
         <!-- $event는 자식요소로부터 전달된 값. true/false가 모달로부터 전달 -->
        <ResetPasswordModal 
         v-model="resetPassword"
         @update:dialog="resetPassword = $event"
        >
        </ResetPasswordModal>

    </v-container>

</template>

<script>
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';
import ResetPasswordModal from './ResetPasswordModal.vue';

export default{
    components:{
        ResetPasswordModal
    },
    data(){
        return{
            email:"",
            password:"",
            resetPassword:false
        }
    },
    methods:{
        async doLogin(){
            // try catch 묶어서 login성공시 token을 console.log로 출력
            try{
                const loginData = {email:this.email, password:this.password};
                console.log(`${process.env.VUE_APP_API_BASE_URL}`)
                const response =  await axios.post(`${process.env.VUE_APP_API_BASE_URL}/doLogin`, loginData);
                // localstorage라는 브라우저의 특정 공간에 서버로부터 받아온 토큰값 저장
                const token = response.data.result.token;
                const refreshToken = response.data.result.refreshToken;
                console.log(jwtDecode(token));
                const role = jwtDecode(token).role;
                const email = jwtDecode(token).sub;
                localStorage.setItem('token', token);
                localStorage.setItem('email', email);
                localStorage.setItem('refreshToken', refreshToken);
                localStorage.setItem('role', role);
                // this.$router.push("/");


                window.location.href = "/";
            }catch(error){
                console.log('로그인 요청 중 에러 발생:', error);
                console.error('에러 객체의 전체 구조:', JSON.stringify(error, null, 2));
            }
        },
        showPasswordModal(){
            this.resetPassword = true;
        }
    }
}
</script>