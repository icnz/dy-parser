import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { LayuiVueResolver } from "unplugin-vue-components/resolvers";
import { resolve } from "path";

const excludeComponents = ["LightIcon", "DarkIcon"];

export default defineConfig({
  // 默认为“/”，“./”用于嵌入形式的开发
  base: "./",
  // 解决“vite use `--host` to expose”
  server: {
    // 默认“localhost”，“0.0.0.0”则监控所有地址
    //host: '0.0.0.0',
    // 默认“5173”
    port: 3000,
    // 开发服务器启动时，自动在浏览器中打开应用程序。或可指定URL路径
    // open: true,
  },
  resolve: {
    alias: [
      {
        find: "@",
        replacement: resolve(__dirname, "./src"),
      },
    ],
  },
  plugins: [
    AutoImport({
      resolvers: [LayuiVueResolver()],
    }),
    Components({
      resolvers: [
        LayuiVueResolver({
          resolveIcons: true,
          exclude: excludeComponents,
        }),
      ],
    }),
    vue(),
  ],
});
