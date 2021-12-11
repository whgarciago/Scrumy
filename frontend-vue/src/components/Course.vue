<template>
  <div class="col-12 col-md-6 col-lg-4 text-center">
    <h1><b>Nombre Curso: </b>{{ enrolled.courseName }}</h1>
    <h3><b>Horas Curso: </b>{{ enrolled.courseDurationHours }}</h3>
    <h6><b>Rol en el Curso: </b>{{ enrolled.role.roleName }}</h6>
  </div>
</template>

<script>
export default {
  name: "Course",
  props: {
    enrolled: Object
  }
}
</script>

<style scoped>

</style>
