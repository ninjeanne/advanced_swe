<template>
  <div class="game_status">
    <div v-if="started">
      <b>Office:</b> {{ boardName }} <br>
      <b>{{ rankingPoints }}</b> (with <img alt="img of vaccination" src="static/work_item.png" width="10" height="10" />: +50)<br>
      {{ player.workItems }} <img alt="img of vaccination" src="static/work_item.png" width="10" height="10" /> (work items)<br>
      {{ player.lifePoints }} <img alt="img of vaccination" src="static/vaccination.png" width="10" height="10" /> (life points)<br>
    </div>
  </div>
</template>

<script>
export default {
  name: "GameStatus",
  props: [
    "started", "rankingPoints", "player", "boardName"
  ]
};
</script>

<style scoped>
.game_status {
  height: 50px;
}

</style>
