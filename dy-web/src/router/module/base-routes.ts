import BaseLayout from "../../layouts/BaseLayout.vue";

export default [
  {
    path: "/",
    redirect: "/keyword",
  },
  {
    path: "/keyword",
    redirect: "/keyword/search",
    component: BaseLayout,
    meta: { title: "视频关键词" },
    children: [
      {
        path: "/keyword/search",
        name: "Workbench",
        component: () => import("../../views/keyword/search/index.vue"),
        meta: {
          title: "关键词查询",
          requireAuth: true,
          affix: true,
          closable: false,
        },
      },
      {
        path: "/keyword/history",
        component: () => import("../../views/keyword/history/index.vue"),
        meta: { title: "历史记录", requireAuth: true },
      },
    ],
  },
];
