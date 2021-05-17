<template>
  <div>
    <logo />
    <div class="content">
      <highscore-component v-bind:highscore="highscore" />
    </div>
    <router-link to="game" pill size="lg" variant="dark">GAME</router-link>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import {api} from "../services/api";
import HighscoreComponent from "../components/HighscoreComponent";
import Logo from "../components/Logo";
import Vue from "vue";
import moment from "moment";

export default {
  name: "Highscore",
  components: {
    HighscoreComponent,
    Logo
  },
  data() {
    return {
      highscore: [],
      connected: false
    };
  },
  methods: {
    async start() {
      if (!this.connected) {
        await this.connect();
      }
      if (this.stompClient != null && this.connected) {
        this.stompClient.subscribe("/backend/topranking", tick => {
          this.highscore = JSON.parse(tick.body);
        });
      }
    },
    async connect() {
      this.socket = new SockJS("http://localhost:8080/socket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.debug = () => {
      };
      await this.stompClient.connect({}, frame => {
        this.connected = true;
      }, error => {
        //console.log(error);
      });
    },
    defaultRankingForBoard() {
      api("/highscore", {
        method: "GET"
      }).then(value => this.highscore = value);
    }
  },
  mounted() {
    this.connect();
    this.defaultRankingForBoard();
    Vue.filter("formatDate", function(date) {
      if (date) {
        return moment(date).format("DD. MMMM YYYY HH:mm");
      }
    });
    this.start();
  }
};
</script>

<style scoped>
.content {
  display: flex;
  justify-content: center;
  height: 400px;
}

</style>
