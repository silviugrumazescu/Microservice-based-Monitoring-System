import { createStore } from 'vuex'

import {auth} from './modules/auth'
import VuexPersistence from 'vuex-persist'

export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    auth
  },
  plugins: 
    [new VuexPersistence().plugin]
})
