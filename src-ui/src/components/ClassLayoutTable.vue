<template>
  <v-data-table
    disable-pagination
    dense
    :headers="layoutTableHeaders"
    :items="objectMapFields"
    item-key="name"
    hide-default-footer
  >
    <template v-slot:[`item.type`]="{ item }">
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <span v-bind="attrs" v-on="on">
            {{ item.type | simpleTypeName }}
          </span>
        </template>
        <span>{{ item.type }}</span>
      </v-tooltip>
    </template>

    <template v-slot:[`item.memoryAddress`]="{ item }">
      {{ item.memoryAddress | hexMemoryAddress }}
    </template>
  </v-data-table>
</template>

<script>
import DisplayUtils from "@/utils/display-utils";
export default {
  name: "ClassLayoutTable",
  props: {
    objectMapFields: {
      type: Array
    }
  },
  data: () => ({
    layoutTableHeaders: [
      {
        text: "Name",
        value: "name"
      },
      {
        text: "Type",
        value: "type"
      },
      {
        text: "Offset",
        value: "offset"
      },
      {
        text: "Size",
        value: "size"
      }
    ]
  }),
  filters: {
    ...DisplayUtils
  }
};
</script>

<style></style>
