import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    diagramMode: true,
    retrying: true,
    objectMaps: [] as MemoryVisualizer4J.ObjectMapMessage[],
    diagramModeSelectedMap: [] as MemoryVisualizer4J.ObjectMapMessage[],
    tableModeSelectMap: null as MemoryVisualizer4J.ObjectMapMessage | null
  },
  mutations: {
    setRetrying(state, payload) {
      state.retrying = payload;
    },
    setObjectMaps(state, payload) {
      state.objectMaps = payload;
    },
    setDiagramMode(state, payload) {
      state.diagramMode = payload;
    },
    setDiagramModeSelectMap(state, payload) {
      state.diagramModeSelectedMap = payload;
    },
    setTableModeSelectMap(state, payload) {
      state.tableModeSelectMap = payload;
    },
    removeDiagramModeSelectMap(state, payload) {
      state.diagramModeSelectedMap = state.diagramModeSelectedMap.filter(
        (_, i) => i !== payload
      );
    },
    addDiagramModeSelectMap(state, payload) {
      state.diagramModeSelectedMap = [...state.diagramModeSelectedMap, payload];
    }
  },
  actions: {
    selectObjectMap(context, payload) {
      if (context.state.diagramMode)
        return context.dispatch("selectObjectMapDiagram", payload);
      context.dispatch("selectObjectMapTable", payload);
    },
    selectObjectMapTable(context, payload) {
      context.commit("setTableModeSelectMap", payload);
    },
    selectObjectMapDiagram(
      context,
      payload: MemoryVisualizer4J.ObjectMapMessage
    ) {
      const index = context.state.diagramModeSelectedMap.indexOf(payload);
      if (index > -1) {
        return context.commit("removeDiagramModeSelectMap", index);
      }
      context.commit("addDiagramModeSelectMap", payload);
    },
    setDiagramModeOff(context) {
      context.commit("setDiagramMode", false);
    },
    setDiagramModeOn(context) {
      context.commit("setDiagramMode", true);
    },
    toggleDiagramMode(context) {
      if (context.state.diagramMode)
        return context.dispatch("setDiagramModeOff");
      context.dispatch("setDiagramModeOn");
    },
    connectionStart(context) {
      context.commit("setRetrying", false);
      context.commit("setObjectMaps", []);
      context.commit("setDiagramModeSelectMap", []);
      context.commit("setTableModeSelectMap", null);
    },
    addObjectMap(context, payload: MemoryVisualizer4J.ObjectMapMessage) {
      context.commit("setObjectMaps", [...context.state.objectMaps, payload]);
    }
  },
  modules: {}
});
