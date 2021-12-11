<template>
  <div class="row col-12 vh-100 align-items-center image">
    <div class="col-5 ">

      <img :src="logoURL" alt="logo_scrumy" class="col-12 ">
      
      <div class="d-flex flex-column justify-content-center my-5 col-12 p-5">
        <button v-on:click= "showLogInForm"> Iniciar Sesión</button>
        <button v-on:click= "showSignUpForm"> Registrarse</button>
      </div>

    </div>
    <div class="col-7 ">
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
          },
            login( event ){
                axios
                .post( this.$store.state.backURL + path, // URL
                    { }, // Body
                    {
                        headers: {
                          'Content-Type': 'application/json'
                        },
                        params: {
                            username: this.username,
                            password: this.password,
                            grant_type: 'password'
                        },
                        auth: {
                            username: "soft-eng-ii",
                            password: "secret",
                        }
                    }
                ).then( response => {
                    if( response.status !== 200 ){
                        alert( "Error en la autenticación" );
                    }else{
                        setAuthenticationToken( response.data.access_token );
                        this.$router.push( {name: 'home'} );
                    }
                } ).catch( error => {
                    if( error.response.status === 400 ){
                      alert( "Credenciales incorrectas" );
                    }else{
                      alert( "¡Parece que hubo un error de comunicación con el servidor!" );
                    }
                } );

                event.preventDefault();
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
  .image{
    background-color: whitesmoke
  }
  
</style>
