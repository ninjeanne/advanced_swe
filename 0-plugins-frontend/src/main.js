// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import router from "./router";
import VueSocketIO from "vue-socket.io";

Vue.use(new VueSocketIO({
  debug: true,
  connection: "http://localhost:7000",
  options: {path: "/my-app/"} //Optional options
}));
Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
  el: "#app",
  router,
  components: {App},
  template: "<App/>"
});
