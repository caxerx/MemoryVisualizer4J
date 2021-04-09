import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import LayoutViewer from "@/views/LayoutViewer.vue";
import ObjectMapViewer from "@/components/ObjectMapViewer.vue";
Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/",
    name: "Layout Viewer",
    component: LayoutViewer,
    redirect: "viewer",
    children: [
      {
        path: "viewer",
        name: "Object Map Viewer",
        component: ObjectMapViewer
      }
    ]
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {
  window.document.title = `${to.name} - MemoryVisualizer4J`;
  next();
});

export default router;
