<template>
  <div class="home">
    <layout></layout>
  </div>
</template>

<script>
// @ is an alias to /src
import layout from "@/layout/layout.vue";

export default {
  name: "Home",
  components: {
    layout
  },
};
</script>
