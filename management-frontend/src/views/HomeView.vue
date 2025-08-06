<template>
  <div class="d-flex justify-content-center align-items-center" style="margin-top:200px">
    <div class="v-stack align-items-center text-center">
      <h1 class="">Welcome to the device management system</h1>
      <p class="text">Please login or register</p>
    </div>
  </div>
</template>

<script>

export default {
  name: 'HomeView',
  components: {
  },
  mounted() {
    console.log(process.env.VUE_APP_USER_SERVICE_IP);
    console.log(process.env.VUE_APP_DEVICE_SERVICE_IP);
  }
}
</script>
