<template>
    <v-container>
        <v-row justify="center">
            <v-col cols="12" sm="4" md="6">
                <v-card>
                    <v-card-title class="text-h5 text-center">로그인</v-card-title>
                    <v-card-text>
                        <v-form @submit.prevent="doLogin">
                            <v-text-field 
                            label="email"
                            v-model="email"
                            type="email"
                            required
                            ></v-text-field>
                            
                            <v-text-field 
                            label="password"
                            v-model="password"
                            type="password"
                            required
                            >
                            </v-text-field>
                            <v-btn type="submit" color="primary" block>로그인</v-btn>
                        </v-form>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>

    </v-container>
</template>

<script>
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
export default{
    data(){
        return{
            email: "",
            password: "",
        }
    },
    methods:{
        async doLogin(){
            const loginData = {email:this.email, password:this.password};
            const response = await axios.post(`${process.env.VUE_APP_API_BASE_URL}/member/doLogin`, loginData);
            const token = response.data.token;
            const role = jwtDecode(token).role;
            const email = jwtDecode(token).sub;
            localStorage.setItem("token", token);
            localStorage.setItem("role", role);
            localStorage.setItem("email", email);
            window.location.href="/";
        }
    }
}


</script>