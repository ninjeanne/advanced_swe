<template>
  <div>
    <h3 v-if="headline">Your Highscore</h3>
    <br v-else>
    <table>
      <tr v-for="(ranking, index) in highscore" :key="index">
        <th>{{ (index + 1) }}. <b>{{ ranking.name }}</b></th>
        <th style="color:green;text-align:right;font-weight:bold">{{ ranking.total }}</th>
        <th>[{{ new Date(ranking.date) | formatDate }}]</th>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  name: "HighscoreComponent",
  props: [
    "highscore",
    "headline"
  ]
};
</script>

<style scoped>
th {
  padding: 5px;
}
</style>
