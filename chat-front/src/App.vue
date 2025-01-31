<template>
  <v-app>
    <HeaderComponent/>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import HeaderComponent from './components/HeaderComponent.vue'
export default {
  name: 'App',
  components: {
    HeaderComponent
  }
}
</script>

<style>
</style>
