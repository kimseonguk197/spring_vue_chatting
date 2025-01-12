<template>
  <!-- <HelloWorld /> -->
   <!-- router-view를 통해 app.vue에서 화면 라우팅 -->
  <v-app>
    <HeaderComponent />
    <v-main>
      <router-view />
    </v-main>
    <FooterComponent />
  </v-app>
</template>
<!-- template에는 html코드가 들어가고, script에는 js코드가 들어감. 디자인은 style태그 안에 들어감 -->
 <!-- vue파일안에는 head태그가 없고, head의 공통 import사항은 main.js에 구성 -->
<script>
// App.vue는 Vue애플리케이션의 루트 컴포넌트(재활용이 가능한 화면)
// import HelloWorld from './components/HelloWorld.vue'
import HeaderComponent from './components/HeaderComponent.vue'
import FooterComponent from './components/FooterComponent.vue'
export default {
  name: 'App',
  components: {
    // HelloWorld
    HeaderComponent
    , FooterComponent
  }
}
</script>

<style>
</style>
