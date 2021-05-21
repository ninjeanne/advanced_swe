<template>
  <div>
    <label for="player"><b>Hello &#128512;</b></label>
    <b-form-input size="sm" placeholder="Enter your name" type="text"
      id="player"
      v-model="player.name"
      :disabled="started"
      @change="updatePlayerName"></b-form-input>
    <br>
    <p>Do you dare to go through the contaminated office?</p>
  </div>
</template>

<script>
export default {
  name: "PlayerBoard",
  props: [
    'player',
    'started'
  ],
  methods: {
    updatePlayerName(){
      var name = this.player.name;
      this.$emit('update:player_name', name);
    }
  },
};
</script>

<style scoped>

</style>
