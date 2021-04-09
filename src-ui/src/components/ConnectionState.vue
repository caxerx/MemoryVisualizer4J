<template>
  <v-list dense nav :color="color" dark>
    <v-list-item>
      <v-list-item-content>
        <v-list-item-title>
          {{ connectingState }}
        </v-list-item-title>
      </v-list-item-content>
    </v-list-item>
  </v-list>
</template>

<script>
export default {
  name: "ConnectionState",
  computed: {
    color() {
      return this.connecting ? "red" : "green";
    },
    connectingState() {
      return this.connecting ? "Connecting" : "Connected";
    },
    connecting() {
      return this.$store.state.retrying;
    }
  }
};
</script>
