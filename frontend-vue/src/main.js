import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import DatetimePicker from 'vuetify-datetime-picker'

Vue.config.productionTip = false;
Vue.use(DatetimePicker);
new Vue({
  router,
  store,
  render: function (h) { return h(App) }
}).$mount('#app');
