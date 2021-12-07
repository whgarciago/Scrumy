const app = Vue.createApp({
  data: () => ({
    formClassValueIniciarSesion: "formulario-inicio-sesion ",
    formClassValueRegistro: ".formulario-registro inactive",
    logo: "./images/logo_scrumy.png",


    valoresInicioDeSesionUsuario: {
      correo: "",
      password: "",
    },

    valoresRegistroUsuario: {
      nombre: "",
      correo: "",
      password: "",
      confirmarPassword: "",
    },
  }),
  methods: {
    mostrarFormularioIniciarSesion() {
      this.formClassValueIniciarSesion = "formulario-inicio-sesion";
      this.formClassValueRegistro = "formulario-registro inactive";
    },

    mostrarFormularioIniciarRegistro() {
      this.formClassValueIniciarSesion = "formulario-inicio-sesion inactive";
      this.formClassValueRegistro = "formulario-registro";
    },
    async manejarEnvioLoginUsuario() {
      console.log(this.valoresInicioDeSesionUsuario.correo);
      console.log(this.valoresInicioDeSesionUsuario.password);
      console.log("login");

      let error = await this.verificarLogin();

      if (error == false) {
        window.location.href = "index.html";
      }

      console.log(error);
    },

    async verificarLogin() {
      if (
        this.valoresInicioDeSesionUsuario.correo == "" ||
        this.valoresInicioDeSesionUsuario.password == ""
      ) {
        alert("Datos incompletos");
        return true;
      }
      let usuarios = await this.obtenerTodosLosUsuarios();

      for (let i = 0; i < usuarios.length; i++) {
        if (usuarios[i].correo == this.valoresInicioDeSesionUsuario.correo) {
          console.log("hallado");
          console.log( usuarios[i].usuarioID);

          localStorage.setItem("usuarioID", usuarios[i].usuarioID);

          
          return false;
        }
      }

      alert("Usuario no hallado");
      return true;
    },

    verificarRegistro() {
      let error = false;

      //TODO: Hallar la funcion async await para comprobar correo
      /*let usuarios = this.obtenerTodosLosUsuarios();
      console.log(usuarios["data"]);

      for (let i = 0; i < usuarios.length; i++) {
        if (usuarios[i].correo == this.valoresRegistroUsuario.correo) {
          console.log("Hallado");
          alert("El correo ya está en uso");
          error = true;
          return error;
        }
      }*/
      if (
        this.valoresRegistroUsuario.nombre == "" ||
        this.valoresRegistroUsuario.correo == "" ||
        this.valoresRegistroUsuario.password == "" ||
        this.valoresRegistroUsuario.confirmarPassword == ""
      ) {
        alert("Datos incompletos");
        error = true;
        return error;
      }
      if (
        this.valoresRegistroUsuario.password !==
        this.valoresRegistroUsuario.confirmarPassword
      ) {
        alert("Las contraseñas no coinciden");
        error = true;
        return error;
      } else {
        return false;
      }
    },
    manejarEnvioRegistroUsuario() {
      console.log("registro");
      let error = this.verificarRegistro();
      console.log("error" + error);
      if (error == false) {
        this.registrarUsuario();
        console.log("Registro Exitoso");
        alert("Registro exitoso");
        //location.reload();
      } else {
        console.log("Hubo un error");
      }
    },
    async registrarUsuario() {
      try {
        const response = await axios.post(
          "http://localhost:8081/api/usuarios/create",
          {
            contraseña: this.valoresRegistroUsuario.password,
            correo: this.valoresRegistroUsuario.correo,
            nombre: this.valoresRegistroUsuario.nombre,
            idProyectos: 23,
          }
        );
        console.log(response);
      } catch (error) {
        console.log(error);
        if (error.response) {
          // get response with a status code not in range 2xx
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        } else if (error.request) {
          // no response
          console.log(error.request);
        } else {
          // Something wrong in setting up the request
          console.log("Error", error.message);
        }
        console.log(error.config);
      }
    },
    async obtenerTodosLosUsuarios() {
      console.log("Buscar Usuarios");
      try {
        const response = await axios.get(
          "http://localhost:8081/api/usuarios/all"
        );
        console.log(response);
        return response["data"];
      } catch (error) {
        console.log(error);
        if (error.response) {
          // get response with a status code not in range 2xx
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        } else if (error.request) {
          // no response
          console.log(error.request);
        } else {
          // Something wrong in setting up the request
          console.log("Error", error.message);
        }
        console.log(error.config);
      }
    },
  },
});
/*const mountAppProyectos =Vue.createApp({
  data(){
    return{
      
    proyectos:[],

    }
  },
  methods:{
    created(){
      proyectos=obtenerProyectosDeBackend()
    },
    async obtenerProyectosDeBackend() {
      try {
        const response = await axios.get(
          "http://localhost:8081/api/proyectos/all",
          {
            params: {
              id: usuarioID,
            },
          }
        );
        proyectos = response["data"];
        console.log(proyectos);
      } catch (error) {
        console.log(error);
        if (error.response) {
          // get response with a status code not in range 2xx
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        } else if (error.request) {
          // no response
          console.log(error.request);
        } else {
          // Something wrong in setting up the request
          console.log("Error", error.message);
        }
        console.log(error.config);
      }
    }
  }

})*/
