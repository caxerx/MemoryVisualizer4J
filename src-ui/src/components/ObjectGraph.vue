<template>
  <div style="height:100%; width:100%">
    <div style="height:64px">
      <v-toolbar color="primary" dark>
        Diagram View
        <v-spacer></v-spacer>
        <div class="d-flex mr-2">
          <div class="px-2">Vertical</div>
          <v-switch
            color="white"
            hide-details
            v-model="isHorizontal"
            v-bind="attrs"
            v-on="on"
          ></v-switch>
          <div class="px-2">Horizontal</div>
        </div>
        <v-btn @click="setGraphOption" outlined color="white" class="mr-2">
          Reset Layout
        </v-btn>
        <v-btn @click="drawSvg" outlined color="white">Export Diagram</v-btn>
      </v-toolbar>
    </div>
    <div style="height:calc(100% - 64px); width:100%" ref="graphContainer">
      <div
        v-if="objectMap == null"
        style="height:100%; width:100%; display:flex;"
        class="align-center justify-center"
      >
        No Object Selected
      </div>
      <Network
        :nodes="graphData.nodes"
        :edges="graphData.edges"
        ref="network"
        :style="`height: ${height}px; width: ${width}px`"
        v-else-if="height > 0"
      ></Network>
    </div>
    <a
      :href="downloadData"
      :download="fileName"
      v-show="false"
      ref="download"
    ></a>
  </div>
</template>
<script>
import DisplayUtils from "@/utils/display-utils";
import { Network } from "vue-visjs";
import C2S from "canvas2svg-visjs";
export default {
  name: "ObjectGraph",
  components: { Network },
  mounted() {
    this.observer = new ResizeObserver(e => {
      const rect = e[0].contentRect;
      this.height = rect.height;
      this.width = rect.width;
    });
    this.observer.observe(this.$refs.graphContainer);
  },
  beforeDestroy() {
    this.observer.disconnect();
  },
  methods: {
    getFileName() {
      return `ObjectDiagram_${+new Date()}`;
    },
    async drawSvg() {
      const network = this.$refs.network.network;
      const networkContainer = network.body.container;
      const ctx = new C2S({
        width: networkContainer.clientWidth,
        height: networkContainer.clientHeight,
        embedImages: true
      });
      const canvasProto = network.canvas.__proto__;
      const originalGetContext = canvasProto.getContext;
      canvasProto.getContext = () => {
        return ctx;
      };
      network.redraw();
      canvasProto.getContext = originalGetContext;
      this.svg = ctx.getSerializedSvg();
      this.download();
    },
    async download() {
      this.fileName = this.getFileName();
      const svgBlob = new Blob([this.svg], {
        type: "image/svg+xml;charset=utf-8"
      });
      this.downloadData = URL.createObjectURL(svgBlob);
      await this.$nextTick();
      this.$refs.download.click();
    },
    setGraphOption() {
      setTimeout(async () => {
        if (this.$refs.network == null) return;
        await this.$nextTick();
        this.$refs.network.setOptions(this.initialOptions);
        await this.$nextTick();
        this.$refs.network.setOptions({
          ...this.initialOptions,
          layout: {
            hierarchical: false
          }
        });
      }, 10);
    }
  },
  computed: {
    objectMap() {
      let objectMap = null;
      for (let i of this.$store.state.diagramModeSelectedMap) {
        objectMap = {
          objectLayouts: {
            ...(objectMap?.objectLayouts ?? {}),
            ...i.objectLayout.objectLayouts
          },
          entryPoints: [
            ...(objectMap?.entryPoints ?? []),
            i.objectLayout.entryPoint.memoryAddress
          ]
        };
      }
      return objectMap;
    },
    initialOptions() {
      return {
        layout: {
          hierarchical: {
            sortMethod: "directed",
            direction: this.isHorizontal ? "LR" : "UD",
            levelSeparation: 200,
            nodeSpacing: 200,
            shakeTowards: "leaves",
            edgeMinimization: false,
            blockShifting: false
          }
        },
        physics: {
          enabled: false
        },
        edges: {
          scaling: { label: { drawThreshold: 0 } },
          arrows: {
            to: {
              enabled: true
            }
          },
          smooth: {
            enabled: false
          }
        },
        nodes: {
          fixed: false,
          scaling: { label: { drawThreshold: 0 } },
          shape: "box",
          font: {
            align: "left",
            multi: "md"
          }
        }
      };
    },
    graphData() {
      if (this.objectMap == null) return { nodes: [], edges: [] };
      return {
        nodes: Object.values(this.objectMap.objectLayouts).map(i => ({
          id: `${i.memoryAddress}`,
          label:
            `*${this.$options.filters.simpleTypeName(i.type)}*\n` +
            `*${this.$options.filters.hexMemoryAddress(i.memoryAddress)}*\n` +
            i.fields
              .map(
                f =>
                  `${f.name}: ${this.$options.filters.simpleTypeName(f.type)}` +
                  (f.value == null ? "" : ` = ${f.value}`) +
                  (f.objectAddress == null
                    ? ""
                    : ` -> ${this.$options.filters.hexMemoryAddress(
                        f.objectAddress
                      )}`)
              )
              .join("\n"),
          color: this.objectMap.entryPoints.includes(i.memoryAddress)
            ? { background: "lime" }
            : null
        })),
        edges: Object.values(this.objectMap.objectLayouts)
          .map(i =>
            i.fields
              .filter(j => j.objectAddress)
              .map(j => ({
                from: `${i.memoryAddress}`,
                to: `${j.objectAddress}`
              }))
          )
          .flat()
          .map((i, index) => ({
            ...i,
            id: index
          }))
      };
    }
  },
  watch: {
    isHorizontal() {
      this.setGraphOption();
    },
    objectMap: {
      immediate: true,
      handler() {
        this.setGraphOption();
      }
    }
  },
  data() {
    return {
      isHorizontal: false,
      fileName: "",
      svg: "",
      downloadData: "",
      observer: null,
      height: 0,
      width: 0
    };
  },
  filters: {
    ...DisplayUtils
  }
};
</script>
