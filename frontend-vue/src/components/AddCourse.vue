<template>
  <div class="col-12 col-sm-10 col-md-8 offset-sm-1 offset-md-2">
    
    <div class="mt-5">
      <form class="border border-primary rounded form-inline" @submit="crearCurso">

        <h2 class="col-12 text-center text-primary mt-3 mb-5">Crear un curso</h2>

        <div class="form-group col-12">
          <label for="nombreCurso" class="custom-label col-md-3">Nombre del curso</label>
          <input id="nombreCurso" class="form-control col-12 col-sm-10 col-md-7 offset-sm-1" type="nombreCurso"
                 placeholder="Nombre del Curso" v-model="nombreCurso" required/>
        </div>
        <div class="form-group col-12">
          <label for="numeroHoras" class="custom-label col-md-3">N&uacute;mero de horas</label>
          <input id="numeroHoras" class="form-control col-12 col-sm-10 col-md-7 offset-sm-1" type="numeroHoras"
                 placeholder="Número de horas" v-model="numeroHoras" required/>
        </div>

        <div class="col-12 mb-3">
          <button class="col-sm-6 col-md-4 offset-sm-5 offset-md-7 btn btn-primary" type="submit">
            Crear curso
          </button>
        </div>

      </form>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  import {getAuthenticationToken} from '@/dataStorage';

  export default {
    name: "AddCourse",

    data( ){
      return {
        nombreCurso: '',
        numeroHoras:'',
      }
    },
    methods: {
      crearCurso(event){
        const coursePath = '/profesor/crear-curso';
        axios.post(this.$store.state.backURL+ coursePath,{
          courseName:this.nombreCurso,
          durationHours:this.numeroHoras
        },
        {
              params: {
                access_token: getAuthenticationToken( )
              }
        }
        ).then( response => {
            if( response.status !== 201 ){
              alert( "Error en la petición. Intente nuevamente" );
            }else{
              alert( "Se ha creado el curso" );
            }
          }).catch( response => {
            if( response.response.status === 401 ){
              alert( "¡Ups! Al parecer tu contraseña es incorrecta o la sesión ha finalizado" );
            }else if ( response.response.status === 400 ){
              alert( "¿Estás seguro de que aún no tienes ese rol asignado?" );
            }else{
              alert( "No es posible conectar con el backend en este momento" );
            }
          });

      event.preventDefault( );
      },
      
    }

  }
</script>

<style scoped>
  .form-inline .form-group{
    margin-bottom: 1rem;
  }
</style>
