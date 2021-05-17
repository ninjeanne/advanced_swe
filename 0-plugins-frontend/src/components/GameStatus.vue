<template>
  <div class="game_status">
    <div v-if="started">
      <b>Office:</b> {{ boardName }} <br>
      <b>{{ rankingPoints }}</b> (with <img alt="img of vaccination" src="static/work_item.png" width="10" height="10" />: +50)<br>
      {{ player.statistics.WorkItem }} <img alt="img of vaccination" src="static/work_item.png" width="10" height="10" /> (work items)<br>
      {{ isAlive }} <img alt="img of vaccination" src="static/vaccination.png" width="10" height="10" /> (vaccination)<br>
    </div>
  </div>
</template>

<script>
export default {
  name: "GameStatus",
  props: [
    "started", "rankingPoints", "player", "boardName"
  ],
  computed: {
    isAlive: function() {
      return (this.player.statistics.Vaccination - this.player.statistics.Infection);
    }
  }
};
</script>

<style scoped>
.game_status {
  height: 70px;
}

</style>
