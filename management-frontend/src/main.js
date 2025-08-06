import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap'
import VuexPersistence from 'vuex-persist'
import PrimeVue from 'primevue/config';
import Notifications from '@kyvg/vue3-notification'

import 'primevue/resources/themes/lara-light-teal/theme.css'


createApp(App).use(store).use(router).use(PrimeVue).use(Notifications).mount('#app')
