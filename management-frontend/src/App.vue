<template>
  <Navbar />
  <router-view />
  <notifications position="bottom right" pauseOnHover="true" duration="10000" width="500px" max="5"/>
</template>

<script>
import Navbar from './components/Navbar.vue'

export default {
  name: 'App',
  components: {
    Navbar,
  },
  data() {
    return {
      managementSocket: null
    }
  }
}
</script>

<style>
  #app {
    height: 100vh;

  }
</style>
