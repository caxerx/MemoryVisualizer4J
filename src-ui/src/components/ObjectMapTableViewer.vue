<template>
  <div fill-height class="fill-height pa-0">
    <v-row no-gutters>
      <v-col style="height: calc(100vh - 64px); overflow: auto;">
        <v-treeview
          :items="entryPoint"
          v-if="entryPoint"
          activatable
          return-object
          :active.sync="selectedItem"
          :load-children="getChildTree"
        ></v-treeview>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col style="height: calc(100vh - 64px); overflow: auto" cols="9">
        <template v-if="selectedItem[0]">
          <template>
            <v-toolbar flat color="primary" dark>
              <span v-if="classTable && selectedItem[0].objectMap">
                Class {{ selectedItem[0].objectMap.classLayout.type }}
              </span>
              <span v-else>
                Object {{ selectedItem[0].name }} (
                {{
                  (selectedItem[0].objectAddress ||
                    selectedItem[0].memoryAddress) | hexMemoryAddress
                }}
                )
              </span>
              <v-spacer></v-spacer>
              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <div class="d-flex">
                    <div class="px-2">Object</div>
                    <v-switch
                      color="white"
                      hide-details
                      v-model="classTable"
                      v-bind="attrs"
                      v-on="on"
                    ></v-switch>
                    <div class="px-2">Class</div>
                  </div>
                </template>
                <span>Switch Layout</span>
              </v-tooltip>
            </v-toolbar>
            <template v-if="selectedClassLayout">
              <template v-if="classTable">
                <ClassLayoutTable
                  :objectMapFields="selectedClassLayout"
                ></ClassLayoutTable>
              </template>
              <template v-else>
                <ObjectMapTable
                  :objectMapFields="selectedObjectLayout"
                ></ObjectMapTable>
              </template>
            </template>
            <template v-else>
              <div class="pa-6">You have selected a primitive type.</div>
            </template>
          </template>
        </template>
        <template v-else>
          <div class="pa-6">No nodes selected.</div>
        </template>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import DisplayUtils from "@/utils/display-utils";
import ObjectMapTable from "@/components/ObjectMapTable";
import ClassLayoutTable from "@/components/ClassLayoutTable";
import { v4 as uuidv4 } from "uuid";

export default {
  name: "ObjectMapTableViewer",
  props: {
    id: {
      type: String
    }
  },
  components: {
    ObjectMapTable,
    ClassLayoutTable
  },
  data: () => ({
    selectedItem: [],
    classTable: false,
    entryPoint: []
  }),
  methods: {
    async getChildTree(item) {
      item.children.push(
        ...this.convertObject(
          this.objectMap.objectLayout.objectLayouts[`${item.objectAddress}`]
        ).children
      );
    },
    convertObject(obj) {
      return {
        ...obj,
        name: this.$options.filters.simpleTypeName(obj.type),
        children: this.convertFields(obj.fields),
        id: uuidv4()
      };
    },
    convertFields(fields) {
      return fields.map(f => ({
        ...f,
        name: `${f.name}: ${this.$options.filters.simpleTypeName(f.type)}`,
        children: f.objectAddress ? [] : undefined,
        id: uuidv4()
      }));
    }
  },
  filters: {
    ...DisplayUtils
  },
  watch: {
    objectMap: {
      immediate: true,
      handler(val) {
        if (!val) return;
        this.entryPoint = [this.convertObject(val.objectLayout.entryPoint)];
      }
    }
  },
  computed: {
    selectedClassLayout() {
      return this.objectMap?.objectLayout?.classLayouts[
        this.selectedItem?.[0]?.objectAddress ||
          this.selectedItem?.[0]?.memoryAddress
      ]?.layout;
    },
    selectedObjectLayout() {
      return this.objectMap?.objectLayout?.objectLayouts[
        this.selectedItem?.[0]?.objectAddress ||
          this.selectedItem?.[0]?.memoryAddress
      ]?.fields;
    },
    objectMap() {
      return this.$store.state.tableModeSelectMap;
    }
  }
};
</script>

<style></style>
