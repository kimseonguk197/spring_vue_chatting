<template>
<v-dialog max-width="500px">
    <v-card>
        <v-card-title class="text-h5 text-center">
            비밀번호 변경하기
        </v-card-title>
        <v-card-text>
            <v-form @submit.prevent="resetPassword">
                <v-text-field
                  label="email"
                  v-model="email"
                  prepend-icon="mdi-email"
                  type="email"
                  required                            
                ></v-text-field>
                <v-text-field
                 label="기존비밀번호"
                 v-model="asIsPassword"
                 prepend-icon="mdi-lock"
                 type="password"
                 required
                ></v-text-field>
                <v-text-field
                 label="신규비밀번"
                 v-model="toBePassword"
                 prepend-icon="mdi-lock"
                 type="password"
                 required
                ></v-text-field>
                <v-btn type="submit" color="primary" block>비밀번호 재설정요청</v-btn>
                <v-btn color="red" @click="closeModal" block>닫기</v-btn>
            </v-form>
        </v-card-text>
    </v-card>
</v-dialog>

</template>

<script>
import axios from 'axios';

export default{
    data(){
        return{
            email:"",
            asIsPassword:"",
            toBePassword:"",
        }
    },
    methods:{
        async resetPassword(){
            const body = {
                email:this.email,
                asIsPassword:this.asIsPassword,
                toBePassword:this.toBePassword
            }
            // {body} => body : {email:xxx, password:aaa}
            // body => {email:xxx, password:yyy}
            // member/reset-password
            try{
                const response =  await axios.patch(`${process.env.VUE_APP_API_BASE_URL}/member/reset-password`, body);
                console.log(response.data);
                alert("정상 변경 처리 되었습니다.")
            }catch(e){
                console.log(e);
                alert(e.response?.data?.error_message || "입력값을 확인해주세요");
            }


        },
        closeModal(){
            // this.emit은 vue에서 컴포넌트간의 이벤트를 전달하는 매커니즘
            // 자식컴포넌트에서 this.$emit을 호출하면 update:dialog라는 이벤트가 실행되고, false 부모 컴포넌트로 전달
            this.$emit('update:dialog', false);
        }
    }
}

</script>