
<template>
  <ion-page>
                <link href="assets/css/login.css" rel="stylesheet" />
    <ion-header>
      <div>
        <ion-toolbar class="header"> </ion-toolbar>
        <ion-toolbar class="header">
          <ion-title class="title">Sharing Ledger</ion-title>
        </ion-toolbar>
      </div>
    </ion-header>
    <ion-content>
      <div id="father">
        <div id="son">
          <ion-list>
            <ion-item>
              <img src="assets/icon/welcome.png" />
            </ion-item>
            <ion-item class="input">
              <ion-label>账号</ion-label>

              <ion-input
                placeholder="邮箱或手机号"
                v-model="username"
              ></ion-input>
              <ion-icon
                :icon="star"
                src="assets/icon/person-outline.svg"
                slot="start"
              ></ion-icon>
            </ion-item>

            <ion-item class="input">
              <ion-label>密码</ion-label>
              <ion-input
                placeholder="您的密码"
                type="password"
                v-model="password"
              ></ion-input>
              <ion-icon
                :icon="star"
                src="assets/icon/keypad-outline.svg"
                slot="start"
              ></ion-icon>
            </ion-item>
            <ion-item  v-if="loginWrong != ''">
              <ion-label color="danger"  class="error">{{loginWrong}}</ion-label>
            </ion-item>

            <ion-button expand="block" class="login" @click="login()">
              点击登录</ion-button
            >

            <ion-button expand="block" class="register" href="../register"
              >注册账号</ion-button
            >
          </ion-list>
        </div>
      </div>
    </ion-content>
    <ion-footer>
      <ion-toolbar class="footer">
        <ion-title>Copyright@南鸽难鸽</ion-title>
      </ion-toolbar>
    </ion-footer>
  </ion-page>
</template>
<script lang="ts">
import {
  IonItem,
  IonLabel,
  IonIcon,
  IonInput,
  IonPage,
  IonHeader,
  IonFooter,
  IonToolbar,
  IonButton,
} from "@ionic/vue";
import { defineComponent } from "vue";
import { useStore } from "vuex";
import "@capacitor-community/http";
import { Plugins } from "@capacitor/core";

export default defineComponent({
  name: "login",
  setup() {
    const store = useStore();
    console.log(store);
    return {
      store,
    };
  },
  data() {
    return {
      username: "",
      password: "",
      loginWrong: "",
    };
  },
  components: {
    IonItem,
    IonLabel,
    IonIcon,
    IonInput,
    IonPage,
    IonHeader,
    IonFooter,
    IonToolbar,
    IonButton,
  },
  methods: {
    async login() {
      const { Http } = Plugins;

      const ret = await Http.request({
        method: "POST",
        url: "http://124.71.224.228:8080/api/user/login",
        data: {
          username: this.username,
          passwd: this.password,
        },
        headers: {
          'Content-Type' : "application/x-www-form-urlencoded"
        }
      });
      const data = await ret.data;
      if (data.code == 200) {
        this.$router.replace("/tabs");
        this.store.commit("loginSuccess", this.username);
      } else {
        this.loginWrong = "用户或密码错误";
      }
    },
  },
});
</script>
