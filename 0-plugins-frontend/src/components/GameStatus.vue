<template>
  <div>
    <b>{{ rankingPoints }}</b> (with <img alt="img of vaccination" src="static/work_item.png" width="10" height="10" />: +50)<br>
    {{ workItems }} <img alt="img of vaccination" src="static/work_item.png" width="10" height="10" /> (work items)<br>
    {{ lifePoints }} <img alt="img of vaccination" src="static/vaccination.png" width="10" height="10" /> (life points)<br>
  </div>
</template>

<script>
export default {
  name: "GameStatus",
  props: [
    'started',
    'rankingPoints',
    'workItems',
    'lifePoints'
  ]
};
</script>

<style scoped>

</style>
