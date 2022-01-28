<template>
  <div class="row col-12 vh-100 whole-page p-0 m-0">
    <div
      class="d-flex flex-column  align-items-center justify-content-center h-100 col-12 col-xl-4 col-sm-12 color rigth-shadow"
    >
        <img :src="logoURL" alt="logo_scrumy " class="img-fluid "  />

      <div >
        <button v-on:click="showLogInForm" class="btn btn-primary m-2 col-12">
          Iniciar Sesión
        </button>
        <button v-on:click="showSignUpForm" class="btn btn-primary m-2 col-12">
          Registrarse
        </button>
      </div>
    </div>
    <div class=" d-flex col-xl-8 col-sm-12 h-100 align-items-center justify-content-center">
      <router-view></router-view>
    </div>
  </div>
</template>
<script>

import logo from "../assets/logo_scrumy.png";

const path = "/oauth/token";

export default {
  name: "Login",
  components: {},
  data() {
    return {
      logoURL: logo,
    };
  },
  methods: {
    showLogInForm() {
      this.$router.push({ name: "loginform" }).catch(err=>{})
    },
    showSignUpForm() {
      this.$router.push({ name: "signupform" }).catch(err=>{});
    },
  },
};
</script>

<style scoped>

.whole-page {
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
    "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
}
.color {
  background: linear-gradient(to bottom, rgb(34, 97, 170), rgb(211, 211, 233));
}
.rigth-shadow {
  box-shadow: 10px 0 5px -5px #888;
}
</style>
