<template>
  <div class="home">
    <p>connection to backend established: {{ isConnected }}</p>
    <p>Sent: {{ sentMessage }}</p>
    <p>Received: {{ receivedMessage }}</p>
    <button v-on:click="sendMessage()">Send Message</button>
  </div>
</template>

<script>
// @ is an alias to /src
export default {
  name: "home",
  components: {},
  data() {
    return {
      socket: null,
      sent: null,
      received: null
    };
  },
  methods: {
    connect() {
      this.socket = new WebSocket("ws://localhost:8080/chat");
      this.socket.onmessage = function(event) {
        console.log(event);
        this.received = event;
      };
      this.socket.onopen = function(event) {
        console.log("Successfully connected to the echo websocket server...");
      };

      this.socket.onmessage = function(event) {
        alert(`[message] Data received from server: ${event.data}`);
        this.received = event.data;
      };

      this.socket.onclose = function(event) {
        if (event.wasClean) {
          alert(`[close] Connection closed cleanly, code=${event.code} reason=${event.reason}`);
        } else {
          // e.g. server process killed or network down
          // event.code is usually 1006 in this case
          alert("[close] Connection died");
        }
      };
      this.socket.onerror = function(error) {
        alert(`[error] ${error.message}`);
      };
    },
    sendMessage: function() {
      var message = {
        from: "Frontend",
        text: "Jeanne"
      };
      this.sent = message;
      this.socket.send(message);
    }
  },
  computed: {
    isConnected() {
      return !!this.socket;
    },
    sentMessage() {
      return this.sent;
    },
    receivedMessage() {
      return this.received;
    }
  },
  mounted() {
    this.connect();
  }
};
</script>

<style scoped>
div.responses {
  width: 700px;
  margin: auto;
}
</style>
