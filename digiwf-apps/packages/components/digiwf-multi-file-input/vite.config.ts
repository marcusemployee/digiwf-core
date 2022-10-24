import vue from "@vitejs/plugin-vue2";
import {defineConfig} from 'vite'
//@ts-ignore
import {fileURLToPath, URL} from "url";

export default defineConfig({
  plugins: [
    vue()
  ],
  build: {
    lib: {
      entry: 'src/index.ts',
      name: 'digiwf-multi-file-input',
    },
    rollupOptions: {
      external: [
        'vue',
        'vuex',
        'vue-router',
        /@vuetify\/.*/
      ],
      output: {
        globals: {
          vue: 'Vue'
        }
      }
    },
    minify: 'esbuild'
  },
  resolve: {
    alias: {
      //@ts-ignore
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
})
