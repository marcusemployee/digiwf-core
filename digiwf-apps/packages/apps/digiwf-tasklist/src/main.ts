import Vue, {VNode} from 'vue';
import vuetify from "./plugins/vuetify";
import store from './store';
import App from './App.vue';
import router from "./router";
import TextHighlight from 'vue-text-highlight';
import {VDivider} from "vuetify/lib/components";
import AppJsonForm from "@/components/schema/AppJsonForm.vue";
import VUserInput from "@/components/schema/VUserInput.vue";
import VMultiUserInput from "@/components/schema/VMultiUserInput.vue";
import {DwfFormRenderer} from "@muenchen/digiwf-form-renderer";
import {DwfMultiFileInput} from "@muenchen/digiwf-multi-file-input";
import Vue2PdfEmbed from 'vue-pdf-embed/dist/vue2-pdf-embed.js';
import './plugins/vjsf';
import './plugins/digiwf-forms';
import VueMarkdown from 'vue-markdown';


Vue.config.productionTip = false;

// @ts-ignore
Vue.use(VueMarkdown);
Vue.component('VLdapInput', BaseLdapInput);
Vue.component('VImageOutput', ImageOutput);
Vue.component('VFileOutput', FileOutput);
Vue.component('VPdfOutput', PdfOutput);
Vue.component('VCsvOutput', CsvOutput);
Vue.component('VAlwDocumentInput', BaseAlwDocumentInput);
Vue.component('VDocumentInput', BaseDocumentInput);
Vue.component('VMarkdownOutput', BaseMarkdownOutput);
Vue.component('TextHighlight', TextHighlight);
Vue.component('VDivider', VDivider);
Vue.component('AppJsonForm', AppJsonForm);
Vue.component('VUserInput', VUserInput);
Vue.component('VMultiUserInput', VMultiUserInput);
Vue.component('DwfFormRenderer', DwfFormRenderer);
Vue.component('DwfMultiFileInput', DwfMultiFileInput);
Vue.component('Vue2PdfEmbed', Vue2PdfEmbed);

import FormBase from "vuetify-form-base";
import BaseMarkdownOutput from "./components/form/BaseMarkdownOutput.vue";
import BaseDocumentInput from "./components/form/BaseDocumentInput.vue";
import BaseAlwDocumentInput from "./components/form/BaseAlwDocumentInput.vue";
import CsvOutput from "./components/form/CsvOutput.vue";
import PdfOutput from "./components/form/PdfOutput.vue";
import FileOutput from "./components/form/FileOutput.vue";
import ImageOutput from "./components/form/ImageOutput.vue";
import BaseLdapInput from "./components/form/BaseLdapInput.vue";
Vue.component('FormBase', FormBase);

// highlight filter
Vue.filter('highlight', function (words: any, query: any) {
  const iQuery = new RegExp(query, "ig");
  return words.toString().replace(iQuery, function (matchedTxt: any, a: any, b: any) {
    return ('<span class=\'highlight\'>' + matchedTxt + '</span>');
  });
});

new Vue({
  router,
  store: store,
  vuetify,
  render: (h): VNode => h(App),
}).$mount('#app');
