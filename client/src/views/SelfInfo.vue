<template>
  <ion-page>
    <link href="assets/css/selfInfo.css" rel="stylesheet"  />
    <ion-header>
      <ion-toolbar class="header"> </ion-toolbar>
      <ion-toolbar class="header">
        <ion-title class="title">个人信息</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content class="ion-padding">
      <ion-item href="../../infoChange" class="headImg" detail=false>
        <ion-list>
          <img
            style="
              border-radius: 50%;
              width: 40%;
              height: 40%;
              clear: both;
              display: block;
              margin: auto;
            "
            src="assets/icon/welcome.png"
          />
        </ion-list>
      </ion-item>
        <ion-card>
          <ion-item>
            <ion-icon
              :icon="star"
              src="assets/icon/person-outline.svg"
              slot="start"
            ></ion-icon>
            <ion-label>用户名:{{ name }}</ion-label>
          </ion-item>
        </ion-card>
        <ion-card>
          <ion-item>
            <ion-icon
              :icon="star"
              src="assets/icon/person-outline.svg"
              slot="start"
            ></ion-icon>
            <ion-label>ID:{{ id }}</ion-label>
          </ion-item>
        </ion-card>
        <ion-card>
          <ion-item>
            <ion-icon
              :icon="star"
              slot="start"
              src="assets/icon/call-outline.svg"
            ></ion-icon>
            <ion-label>手机号:{{ phone }}</ion-label>
          </ion-item>
        </ion-card>
        <ion-card>
          <ion-item>
            <ion-icon
              :icon="star"
              slot="start"
              src="assets/icon/mail-outline.svg"
            ></ion-icon>
            <ion-label>邮箱:{{ email }} </ion-label>
          </ion-item>
        </ion-card>
        <ion-button
          expand="block"
          href="../../login"
          class="logout"
          @click="logout()"
        >
          退出登录</ion-button
        >
    </ion-content>
  </ion-page>
</template>

<script>
import { defineComponent } from "vue";
import "@capacitor-community/http";
import { Plugins } from "@capacitor/core";
import {
  IonIcon,
  IonPage,
  IonContent,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonLabel,
  IonItem,
  IonCard,
  IonButton,
} from "@ionic/vue";
import { wallet, book, personCircle } from "ionicons/icons";

export default defineComponent({
  data() {
    return {
      email: "",
      phone: "",
      id: "",
      name: "",
    };
  },
  components: {
    IonIcon,
    IonPage,
    IonContent,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonLabel,
    IonItem,
    IonCard,
    IonButton,
  },

  setup() {
    return {
      wallet,
      book,
      personCircle,
    };
  },
  methods: {
    async logout() {
      const { Http } = Plugins;
      const ret = await Http.request({
        method: "GET",
        url: "http://124.71.224.228:8080/api/user/logout",
      });
      const _data = await ret;
      console.log("logout");
    },
  },
  created: async function () {
    const { Http } = Plugins;

    const ret = await Http.request({
      method: "GET",
      url: "http://124.71.224.228:8080/api/user/getMyInfo",
    });
    const data = await ret.data;
    /*
    const data = {
      phone: "1213243",
      id: "wqdq",
      mail: "test@qq.com",
      name: "rolnan",
    };
    */
    console.log(data);
    const detail=data.data;
    console.log(detail);
    this.phone = detail["phone"];
    this.id = detail["id"];
    this.email = detail["mail"];
    this.name = detail["name"];
  },
});
</script>
