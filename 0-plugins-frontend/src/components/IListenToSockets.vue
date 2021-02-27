<template>
  <div>
    <p>HI</p>
    <p v-if="isConnected">We're connected to the server!</p>
    <p>Message from server: "{{ socketMessage }}"</p>
    <button @click="pingServer()">Ping Server</button>
  </div>
</template>

<script>
export default {
  name: "IListenToSockets",
  data() {
    return {
      isConnected: false,
      socketMessage: ""
    };
  },

  sockets: {
    connect() {
      // Fired when the socket connects.
      this.isConnected = true;
    },

    disconnect() {
      this.isConnected = false;
    },

    // Fired when the server sends something on the "messageChannel" channel.
    messageChannel(data) {
      this.socketMessage = data;
    }
  },

  methods: {
    pingServer() {
      // Send the "pingServer" event to the server.
      this.$socket.emit("stream", "{\\\"origin\\\":\\\"Client\\\",\\\"interaction\\\":\\\"Request\\\"}");
    }
  }
};
</script>
