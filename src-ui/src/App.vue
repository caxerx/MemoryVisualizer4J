<template>
  <v-app>
    <v-navigation-drawer app clipped>
      <ConnectionState />
      <v-divider></v-divider>
      <ObjectLayoutList />
    </v-navigation-drawer>

    <v-app-bar app color="primary" dark clipped-left>
      <v-toolbar-title>MemoryVisualizer4J</v-toolbar-title>
      <v-spacer></v-spacer>
      <div class="d-flex">
        <div class="px-2">Table</div>
        <v-switch
          color="white"
          hide-details
          :input-value="diagramMode"
          @change="switchMode"
        ></v-switch>
        <div class="px-2">Diagram</div>
      </div>
    </v-app-bar>

    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script>
import ConnectionState from "@/components/ConnectionState.vue";
import ObjectLayoutList from "@/components/ObjectLayoutList.vue";

export default {
  name: "App",
  components: {
    ConnectionState,
    ObjectLayoutList
  },
  created() {
    this.connectToWebsocket();
  },
  beforeDestroy() {
    this.connection.close();
  },

  methods: {
    switchMode(payload) {
      this.$store.commit("setDiagramMode", payload ?? false);
    },
    connectToWebsocket() {
      if (this.connection?.close) {
        this.connection.close();
      }

      this.connection = new WebSocket(
        `ws://${
          process.env.NODE_ENV === "production"
            ? location.host
            : "localhost:20000"
        }/websocket`
      );

      this.connection.onopen = () => {
        this.$store.dispatch("connectionStart", true);
      };

      this.connection.onmessage = e => {
        this.$store.dispatch("addObjectMap", JSON.parse(e.data));
      };

      this.connection.onclose = () => {
        setTimeout(() => {
          this.$store.commit("setRetrying", true);
          this.connectToWebsocket();
        }, 3000);
      };
    }
  },
  computed: {
    diagramMode() {
      return this.$store.state.diagramMode;
    }
  },
  data: () => ({
    retrying: false,
    connection: {}
  })
};
</script>
