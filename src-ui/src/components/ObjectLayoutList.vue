<template>
  <v-list nav>
    <v-list-item
      v-for="item in objectMaps"
      :key="item.createTime"
      @click="selectMap(item)"
      :class="isActive(item) ? `v-list-item--active` : ``"
    >
      <v-list-item-content>
        <v-list-item-title>
          {{ item.objectType | simpleTypeName }}
        </v-list-item-title>
        <v-list-item-subtitle>
          {{ item.createTime | formatTime }}
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>
  </v-list>
</template>

<script>
import { DateTime } from "luxon";
import DisplayUtils from "@/utils/display-utils";

export default {
  name: "ObjectLayoutList",
  data: () => ({}),
  methods: {
    selectMap(item) {
      this.$store.dispatch("selectObjectMap", item);
    },
    isActive(item) {
      if (this.diagramMode) {
        return this.diagramSelected.includes(item);
      }
      return this.tableSelected == item;
    }
  },
  filters: {
    formatTime(time) {
      return DateTime.fromMillis(time).toLocaleString(
        DateTime.TIME_24_WITH_SECONDS
      );
    },
    ...DisplayUtils
  },
  computed: {
    diagramSelected() {
      return this.$store.state.diagramModeSelectedMap;
    },
    tableSelected() {
      return this.$store.state.tableModeSelectMap;
    },
    diagramMode() {
      return this.$store.state.diagramMode;
    },
    objectMaps() {
      return this.$store.state.objectMaps;
    }
  }
};
</script>
