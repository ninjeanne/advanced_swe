<template>
  <div>
    <div
      id="main-content"
      class="container"
    >
      <div class="row">
        <div class="col-md-6">
          <form class="form-inline">
            <div class="form-group">
              <label for="player">What is your name?</label>
              <input
                type="text"
                id="player"
                class="form-control"
                v-model="player_name"
                placeholder="Your name here..."
              >
            </div>
            <button
              id="send"
              class="btn btn-default"
              type="submit"
              @click.prevent="send"
            >start game
            </button>
          </form>
        </div>
      </div>
    </div>
    <div>
      <canvas id="c"></canvas>
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "websocketdemo",
  data() {
    return {
      received_messages: [],
      player_name: null,
      vueCanvas: null,
      connected: false,
      plan: null,
      colleagues: null,
      obstacles: null,
      vaccination: null
    };
  },
  methods: {
    send() {
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send("/frontend/initialize", this.player_name, {});
      }
    },
    connect() {
      this.socket = new SockJS("http://localhost:8080/socket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect({}, frame => {
        this.connected = true;
        //console.log(frame);
        this.stompClient.subscribe("/backend/start", tick => {
          var response = JSON.parse(tick.body);
          console.log(response);
          this.received_messages.push(response);
          if (this.plan == null) {
            this.plan = response.plan;
          }
          if (this.colleagues == null) {
            this.colleagues = response.colleagues; //todo colleagues bewegen sich
          }
          if (this.obstacles == null) {
            this.obstacles = response.obstacles;
          }
          if (this.vaccination == null) {
            this.vaccination = response.vaccination;
          }
          this.drawMap();
          this.drawObstacles();
        });
      }, error => {
        //console.log(error);
        this.connected = false;
      });
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
    addCanvas() {
      var c = document.getElementById("c");
      c.width = 5000;
      c.height = 5000;
      this.vueCanvas = c.getContext("2d");
    },
    drawMap() {
      this.vueCanvas.clearRect(0, 0, 5000, 5000);
      this.vueCanvas.beginPath();
      this.vueCanvas.rect(0, 0, this.plan.width, this.plan.height);
      this.vueCanvas.fillStyle = "grey";
      this.vueCanvas.fill();
      this.vueCanvas.stroke();
    },
    drawObstacles() {
      console.log(this.obstacles);
      for (var i = 0; i < this.obstacles.length; i++) {
        this.vueCanvas.beginPath();
        this.vueCanvas.rect(this.obstacles[i].x, this.obstacles[i].y, 2, 2);
        this.vueCanvas.fillStyle = "black";
        this.vueCanvas.fill();
      }
      this.vueCanvas.stroke();
    }
  },
  mounted() {
    this.connect();
    this.addCanvas();
  }
};
</script>

<style scoped>
</style>
