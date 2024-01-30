import { defineStore } from "pinia";
import { menu, permission } from "../api/module/user";

export const useUserStore = defineStore({
  id: "user",
  state: () => {
    return {
      token: "",
      userInfo: {},
      permissions: [],
      menus: [],
      page: 1,
      limit: 50,
      cookies: "",
    };
  },
  actions: {
    async loadMenus() {
      const { data, code } = await menu();
      if (code == 200) {
        this.menus = data;
      }
    },
    async loadPermissions() {
      const { data, code } = await permission();
      if (code == 200) {
        this.permissions = data;
      }
    },
  },
  persist: {
    storage: localStorage,
    paths: [
      "token",
      "userInfo",
      "permissions",
      "menus",
      "page",
      "limit",
      "cookies",
    ],
  },
});
