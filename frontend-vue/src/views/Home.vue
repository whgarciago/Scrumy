<template>
  <div class="container-fluid mt-5 text-center ">
    <h1 class="display-1">
      <strong>
        Bienvenido
      </strong>
      </h1>
    <div class="row">
        <div class="container-fluid row justify-content-around col-12 mt-5 mb-5 ">
            <button v-on:click= "mostrarCursos" class=" btn btn-primary">
                    Mostrar cursos
            </button>
          
            <button v-on:click= "mostrarRoles" class=" btn btn-primary">
                        Mostrar Roles
            </button>
            <button v-on:click= "agregarRol" class=" btn btn-primary">
                    Agregar Rol
            </button>
            <button  v-if="rolesP==2 || rolesE==2" v-on:click= "crearCurso" class=" btn btn-primary">
                    Crear Curso
            </button>
        </div>
        <router-view></router-view>       
    </div> 
  </div>
</template>

<script>
import axios from 'axios';
  import {getAuthenticationToken} from '@/dataStorage';

  export default{

    name: "Home",

    data( ){
      return{
        maxVal: 0,
        names: '',
        surnames: '',
        username: '',
        password: '',
        cPassword: '',
        role: '',
        roles: [{id: 1, roleName: 'My Role'}],
        rolesE:0,
        rolesP:0,
        response: null
      }
    },

  beforeCreate(){
      if( !getAuthenticationToken( ) ){
        this.$router.push( {name: 'login'} )
      }
      const requestPath = '/mis-roles';

    axios.get( this.$store.state.backURL + requestPath, { params: { access_token: getAuthenticationToken( ) } } )
        .then( response => {
          if( response.status !== 200 ){
            alert( 'Error Obteniendo sus roles' );
          }else{
            this.roles = response.data;
            this.rolesE = this.roles[0].id;
            if(response.data.length>1){
              
            this.rolesP = this.roles[1].id;

            }
            console.log(this.roles[0].id)
          }
        } ).catch( error => {
          alert( 'Error en la petición' );
          console.log( error );
    } );



  },


    methods:{
      mostrarCursos(){
        this.$router.push( {name: 'courses'} )
      },
      mostrarRoles(){
        this.$router.push({ name: 'roles'} )
      },
      agregarRol(){
        this.$router.push({ name: 'add-role'} )
      },
      crearCurso(){
        this.$router.push({ name: 'crear-curso'} )
      },
      maxValFunction () {		
        this.maxVal = Math.max.apply(Math, this.roles.id);
      } 
    }
  }
</script>

<style>

</style>
