<template>
  <div class="row col-12 vh-100 whole-page p-0 m-0">
    <form action="https://www.paypal.com/donate" method="post" target="_top">
      <input type="hidden" name="hosted_button_id" value="TXALJ7ANRPCL8" />
      <input
        type="image"
        src="https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif"
        border="0"
        name="submit"
        title="PayPal - The safer, easier way to pay online!"
        alt="Donate with PayPal button"
      />
      <img
        alt=""
        border="0"
        src="https://www.paypal.com/en_CO/i/scr/pixel.gif"
        width="1"
        height="1"
      />
    </form>
    <div class="position-absolute top-100 start-100 translate-middle btn btn-lg w-100 h-100">
      <form action="https://www.paypal.com/donate" method="post" target="_top" class="w-100 h-100">
        <input type="hidden" name="hosted_button_id" value="TXALJ7ANRPCL8" />
        <input type="image" src="https://img.icons8.com/material/32/000000/delete--v1.png" border="0" name="submit" title="PayPal - The safer, easier way to pay online!" alt="Donate with PayPal button" />
      </form>
    </div>
  </div>
  
</template>

<script>
export default {
  name: "Donation",
  components: {},
  data() {
    return {};
  },
  methods: {},
};
</script>

<style scoped>
.whole-page {
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
    "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
}
</style>
