<template>
  <ObjectGraph v-if="diagramMode"></ObjectGraph>
  <ObjectMapTableViewer v-else></ObjectMapTableViewer>
</template>
<script>
import ObjectGraph from "@/components/ObjectGraph";
import ObjectMapTableViewer from "@/components/ObjectMapTableViewer";
export default {
  components: {
    ObjectMapTableViewer,
    ObjectGraph
  },
  computed: {
    diagramMode() {
      return this.$store.state.diagramMode;
    }
  }
};
</script>
