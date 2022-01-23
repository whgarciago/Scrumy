<template>
  <div class="row col-12 vh-100 align-items-center whole-page p-0 m-0">
    <div class="col-5 ">

      <img :src="logoURL" alt="logo_scrumy" class="col-12 ">
      
      <div class="d-flex flex-column justify-content-center my-5 col-12 p-5 ">
        <button v-on:click= "showLogInForm " class="m-2"> Iniciar Sesión</button>
        <button v-on:click= "showSignUpForm" class="m-2"> Registrarse</button>
      </div>

    </div>
    <div class="col-7">
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
    import axios from 'axios';
    import {setAuthenticationToken} from '@/dataStorage';

    import logo from "../assets/logo_scrumy.png"

    const path = "/oauth/token";

    

    export default {
        name: "Login",
        components: {},
        data(){
          return{
            logoURL:logo
          }
        },
        methods: {
          showLogInForm(){
            this.$router.push({ name: 'loginform'} )
          },
          showSignUpForm(){
            this.$router.push({ name: 'signupform'} )
          }
        }
    }
</script>

<style scoped>
  form {
    border-radius: 20px !important;
  }

  .form-inline {
    width: auto;
  }

  .form-inline .form-group {
    margin-bottom: 1rem;
  }

  .custom-label {
    display: inline-block;
    justify-content: right !important;
    text-align: right !important;
  }

  @media (max-width: 767px) {
    .custom-label {
      display: none !important;
    }
  }

  .whole-page{
    background-color: cornflowerblue;
    background-image: url("../assets/background_picture.jpg");
    background-size: cover;
    height:  100%;
    background-repeat:no-repeat;
    background-position:center;
    font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif
}


  
</style>
