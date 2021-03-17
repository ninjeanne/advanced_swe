<template>
  <div>
    <logo />
    <div class="content">
      <ranking-board v-bind:top-ranking="top_ranking" />
    </div>
    <router-link to="game" pill size="lg" variant="dark">GAME</router-link>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import {api} from "../services/api";
import RankingBoard from "../components/RankingBoard";
import Logo from "../components/Logo";
import Vue from "vue";
import moment from "moment";

export default {
  name: "TopRanking",
  components: {
    RankingBoard,
    Logo
  },
  data() {
    return {
      top_ranking: [],
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
          this.top_ranking = JSON.parse(tick.body);
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
      api("/ranking?boardName=default", {
        method: "GET"
      }).then(value => this.top_ranking = value);
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
