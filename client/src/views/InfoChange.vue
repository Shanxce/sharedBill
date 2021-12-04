<template>
  <ion-page>
                <link href="assets/css/infoChange.css" rel="stylesheet" />
    <ion-header>
      <ion-toolbar class="header"></ion-toolbar>
      <ion-toolbar class="header">
        <ion-title class="title">个人信息修改</ion-title>
      </ion-toolbar>

      <ion-toolbar class="return">
        <ion-button size="default" @click="back">返回</ion-button>
      </ion-toolbar>
    </ion-header>

    <ion-content>
      <ion-list class="information">
        <ion-item>
          <ion-label>新的用户名</ion-label>
          <ion-input type="text" v-model="username"></ion-input>
        </ion-item>
        <ion-item>
          <ion-label>原密码</ion-label>
          <ion-input type="password" v-model="originPassword"></ion-input>
        </ion-item>
        <ion-item>
          <ion-label>密码</ion-label>
          <ion-input type="password" v-model="password"></ion-input>
        </ion-item>
        <ion-item v-if="ReslutWrong != ''">
          <ion-label color="danger" class="wrong">{{ ReslutWrong }}</ion-label>
        </ion-item>
        <ion-item v-if="ReslutSuccess != ''">
          <ion-label color="success" class="success">{{ ReslutSuccess }}</ion-label>
        </ion-item>
      </ion-list>
      <div>
        <ion-button expand="block" @click="change()">修改 </ion-button>
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
  IonButton,
  IonContent,
  IonList,
  IonInput,
  IonPage,
  IonToolbar,
  IonHeader,
  IonItem,
  IonLabel,
} from "@ionic/vue";
import { defineComponent } from "vue";
import "@capacitor-community/http";
import { Plugins } from "@capacitor/core";
export default defineComponent({
  name: "change",
  data() {
    return {
      username: "",
      originPassword: "",
      password: "",
      ReslutWrong: "",
      ReslutSuccess:"",
    };
  },
  components: {
    IonButton,
    IonContent,
    IonList,
    IonInput,
    IonPage,
    IonToolbar,
    IonHeader,
    IonItem,
    IonLabel,
  },
  methods: {
    async back() {
      this.$router.go(-1);
    },
    
    async change() {
      this.ReslutWrong="";
      this.ReslutSuccess="";
      if (this.username.length < 4 || this.username.length > 10) {
        this.ReslutWrong = "用户名长度错误，应为4~10位";
      } else{
          
        const { Http } = Plugins;

        const ret = await Http.request({
          method: "POST",
          url: "http://124.71.224.228:8080/api/user/modify",
          data: {
            newname: this.username,
            passwd: this.originPassword,
            newpasswd: this.password,
          },
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
        });
        const data = await ret.data;
        console.log(data);
        console.log(data.code);
        if (data.code != 200) this.ReslutWrong = data.msg;
        else
        {
            this.ReslutSuccess="修改成功";
        }
      }
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
     const detail=data.data;
      console.log(detail);
      this.username = detail["name"];
    },
});
</script>
