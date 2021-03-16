<template>
  <div>
    <label v-if="!started" for="player"><b>Enter your name</b></label>
    <input
      type="text"
      id="player"
      class="form-control"
      v-model="name"
      :disabled="started"
      v-if="!started"
      placeholder="Your name here..."
      @change="updatePlayerName"
    >
    <b v-else>Hi {{ this.name }}</b>
  </div>
</template>

<script>
export default {
  name: "PlayerBoard",
  props: [
    'playerName',
    'started'
  ],
  data() {
    return {
      name: ""
    }
  },
  methods: {
    updatePlayerName(){
      this.$emit('update:player_name', this.name);
    }
  },
 mounted() {
    this.name = this.playerName;
 }
};
</script>

<style scoped>

</style>
