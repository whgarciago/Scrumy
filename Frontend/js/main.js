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
    manejarEnvioLoginUsuario() {
      console.log(this.valoresInicioDeSesionUsuario.correo);
      console.log(this.valoresInicioDeSesionUsuario.password);
      console.log("login");

      let error = this.verificarLogin();

      if (!error) {
      }

      console.log(error);
    },

    verificarLogin() {
      if (
        this.valoresRegistroUsuario.correo == "" ||
        this.valoresRegistroUsuario.password == ""
      ) {
        alert("Datos incompletos");
        return true;
      } else {
        return false;
      }
    },

    verificarUsuarioExiste() {
      let usuarios = this.obtenerTodosLosUsuarios();
      for (usuario in usuarios) {
        if (
          this.valoresRegistroUsuario.nombre == usuario.nombre ||
          this.valoresRegistroUsuario.correo == usuario.correo
        ) {
          console.log(usuario);
          return true;
        }
      }

      return false;
    },
    verificarRegistro() {
      if (
        this.valoresRegistroUsuario.nombre == "" ||
        this.valoresRegistroUsuario.correo == "" ||
        this.valoresRegistroUsuario.password == "" ||
        this.valoresRegistroUsuario.confirmarPassword == ""
      ) {
        alert("Datos incompletos");
        return true;
      } else if (
        this.valoresRegistroUsuario.password !==
        this.valoresRegistroUsuario.confirmarPassword
      ) {
        alert("Las contraseñas no coinciden");
        return true;
      } else if (this.verificarUsuarioExiste() == true) {
        alert("Usuario o correo ya en uso");
        return true;
      } else {
        return false;
      }
    },
    manejarEnvioRegistroUsuario() {
      console.log("registro");
      let error = this.verificarRegistro();
      if (error == false) {
        this.registrarUsuario();
        console.log("Registro Exitoso");
        alert("Registro exitoso");
        location.reload();
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
          },
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
          "http://localhost:8081/api/usuarios/all",
        );
        console.log(response['data']);
        return response;
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
