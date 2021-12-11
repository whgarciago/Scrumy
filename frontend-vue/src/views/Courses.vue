<template>
<div class="container-fluid">
  <div class="row">
    <div class="col-12 col-sm-10 col-md-8 offset-sm-1 offset-md-2">
      <div class="mt-5">
        <h1> Cursos Inscritos</h1>
        <div class="border border-primary rounded form-inline d-flex justify-content-center">
          <Course v-for="enrolled in enrollments"
            v-bind:key="enrolled.courseId + '-' + enrolled.role.id"
            v-bind:enrolled="enrolled"
            ></Course>
        </div>
        <div class="row align-items-center">
            <button v-on:click= "regresar" class="col-3 btn btn-primary mt-5 ">
                    Regresar
            </button>
        </div>
        
      </div>
    </div>
  </div>

</div>
  
</template>

<script>
import axios from 'axios';
import {getAuthenticationToken} from '@/dataStorage';
import Course from "@/components/Course";

const requestPath = '/mis-cursos';

export default {
  name: "Courses",
  components: { Course },
  data(){
    return {
      enrollments: []
    }
  },
  beforeCreate(){
    axios.get( this.$store.state.backURL + requestPath, { params: { access_token: getAuthenticationToken() } } )
      .then( response => {
        if( response.status !== 200 ){
          alert( 'Error en la respuesta del servidor' );
        }else{
          this.enrollments = response.data;
          console.log(response.data)
          
        }
      } ).catch( error => {
        alert( 'Error con la conexión al servidor' );
      } );
  },
  methods:{
    regresar(){
        this.$router.push( {name: 'home'} )
      },
  }
}
</script>

<style scoped>

</style>
