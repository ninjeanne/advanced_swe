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
      player_name: null,
      vueCanvas: null,
      connected: false,
      plan: null,
      colleagues: null,
      obstacles: null,
      vaccination: null,
      multiplier: 10
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
          console.log(response.colleagues);
          this.colleagues = response.colleagues;
          this.vaccination = response.vaccination;
          if (this.plan == null) {
            this.plan = response.plan;
          }
          if (this.obstacles == null) {
            this.obstacles = response.obstacles;
          }
          this.drawMap();
          this.drawObstacles();
          this.drawVaccination();
          this.drawColleagues();
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
      c.width = 2000;
      c.height = 2000;
      this.vueCanvas = c.getContext("2d");
    },
    drawMap() {
      this.vueCanvas.clearRect(0, 0, 2000, 2000);
      this.vueCanvas.beginPath();
      this.vueCanvas.rect(0, 0, this.plan.width * this.multiplier, this.plan.height * this.multiplier);
      this.vueCanvas.fillStyle = "white";
      this.vueCanvas.fill();
      this.vueCanvas.stroke();
    },
    drawColleagues() {
      for (var i = 0; i < this.colleagues.length; i++) {
        this.vueCanvas.beginPath();
        this.vueCanvas.rect(this.colleagues[i].position.x * this.multiplier, this.colleagues[i].position.y * this.multiplier, this.multiplier, this.multiplier);
        this.vueCanvas.fillStyle = "red";
        this.vueCanvas.fill();
        this.vueCanvas.stroke();
      }
    },
    drawObstacles() {
      for (var i = 0; i < this.obstacles.length; i++) {
        this.vueCanvas.beginPath();
        this.vueCanvas.rect(this.obstacles[i].x * this.multiplier, this.obstacles[i].y * this.multiplier, this.multiplier, this.multiplier);
        this.vueCanvas.fillStyle = "black";
        this.vueCanvas.fill();
        this.vueCanvas.stroke();
      }
    },
    drawVaccination() {
      if (this.vaccination != null) {
        this.vueCanvas.beginPath();
        this.vueCanvas.rect(this.vaccination.x * this.multiplier, this.vaccination.y * this.multiplier, this.multiplier, this.multiplier);
        this.vueCanvas.fillStyle = "green";
        this.vueCanvas.fill();
        this.vueCanvas.stroke();
      }
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
