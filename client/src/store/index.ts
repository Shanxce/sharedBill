import { def } from "@vue/shared";
import { InjectionKey } from "vue";
import { createStore } from "vuex";

export const store = createStore({
  state: {
    count: 0,
    username:"",
    
  },
  mutations: {
    increment (state) {
      state.count++
    },
    loginSuccess(state, user){
      state.username=user;
    }

  }
});