<template>
  <ion-page>
            <link href="assets/css/register.css" rel="stylesheet" />
    <ion-header>
      <ion-toolbar class="header"></ion-toolbar>
      <ion-toolbar class="header">
        <ion-title class="title">欢迎注册</ion-title>
      </ion-toolbar>

      <ion-toolbar class="return">
        <ion-button size="default" href="../login">返回</ion-button>
      </ion-toolbar>
    </ion-header>

    <ion-content>
      <ion-list class="information">
        <ion-item>
          <ion-label>用户名</ion-label>
          <ion-input type="text" v-model="username"></ion-input>
        </ion-item>
        <ion-item v-if="usernameWrong != ''">
          <ion-label color="danger" class="wrong">{{
            usernameWrong
          }}</ion-label>
        </ion-item>
        <ion-item>
          <ion-label>手机号码</ion-label>
          <ion-input type="text" v-model="phone"></ion-input>
        </ion-item>
        <ion-item v-if="phoneWrong != ''">
          <ion-label color="danger" class="wrong">{{ phoneWrong }}</ion-label>
        </ion-item>
        <ion-item>
          <ion-label>密码</ion-label>
          <ion-input type="password" v-model="password"></ion-input>
        </ion-item>
        <ion-item>
          <ion-label stacked>确认密码</ion-label>
          <ion-input type="password" v-model="repassword"></ion-input>
        </ion-item>
        <ion-item v-if="passwordWrong != ''">
          <ion-label color="danger" class="wrong">{{
            passwordWrong
          }}</ion-label>
        </ion-item>
        <ion-item>
          <ion-label>邮箱</ion-label>
          <ion-input type="text" v-model="email"></ion-input>
        </ion-item>
        <ion-item v-if="emailWrong != ''">
          <ion-label color="danger" class="wrong">{{ emailWrong }}</ion-label>
        </ion-item>
        <ion-item>
          <ion-label>验证码</ion-label>
          <ion-input type="text" v-model="vcode"></ion-input>
          <ion-button slot="end" expand="block" size="default" @click="check()"
            >发送验证码
          </ion-button>
        </ion-item>
        <ion-item v-if="isSend != ''">
          <ion-label color="success" class="isSend">{{ isSend }}</ion-label>
        </ion-item>
        <ion-item v-if="regReslutWrong != ''">
          <ion-label color="danger" class="wrong">{{
            regReslutWrong
          }}</ion-label>
        </ion-item>
      </ion-list>
      <div>
        <ion-button expand="block" @click="reg()">注册 </ion-button>
        <ion-button expand="block" href="../login">已有账号</ion-button>
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
  name: "register",
  data() {
    return {
      username: "",
      password: "",
      phone: "",
      repassword: "",
      email: "",
      vcode: "",
      usernameWrong: "",
      phoneWrong: "",
      passwordWrong: "",
      emailWrong: "",
      regReslutWrong: "",
      isSend: "",
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
    async check() {
      const username = this.username;
      let flag = 1;
      if (username.length < 4 || username.length > 10) {
        this.usernameWrong = "用户名长度错误，应为4~10位";
        flag = 0;
      } else {
        this.usernameWrong = "";
      }
      let phoneRight = 1;
      for (let i = 0; i < this.phone.length; i++) {
        if (
          this.phone[i] > "9" ||
          this.phone[i] < "0" ||
          this.phone.length != 11
        ) {
          this.phoneWrong = "手机号错误";
          flag = 0;
          phoneRight = 0;
        }
        if (i == this.phone.length - 1 && phoneRight == 1) this.phoneWrong = "";
      }

      if (this.password != this.repassword) {
        flag = 0;
        this.passwordWrong = "两次密码不一致";
      } else {
        this.passwordWrong = "";
      }
      if (this.password == "") {
        flag = 0;
        this.passwordWrong = "密码不能为空";
      } else {
        this.passwordWrong = "";
      }
      const email = this.email;
      const reg = /^\w+((-\w+)|(\.\w+))*@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
      if (!reg.test(email)) {
        this.emailWrong = "邮箱格式错误";
        flag = 0;
      } else {
        this.emailWrong = "";
      }
      if (flag == 1) {
        const { Http } = Plugins;

        const ret = await Http.request({
          method: "POST",
          url: "http://124.71.224.228:8080/api/user/sendMail",
          data: {
            mail: this.email,
          },
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
        });
        this.isSend = "发送成功";
      }
    },

    async reg() {
      const { Http } = Plugins;

      const ret = await Http.request({
        method: "POST",
        url: "http://124.71.224.228:8080/api/user/register",
        data: {
          mail: this.email,
          username: this.username,
          passwd: this.password,
          phone: this.phone,
          captcha: this.vcode,
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      });
      const data = await ret.data;
      console.log(data);
      console.log(data.code);
      console.log(data.code == 310);
      if (data.code == 200) {
        this.$router.replace("/login");
      } else {
        if (data.code == 310) this.regReslutWrong = "验证码错误";
        else {
          if (data.code == 311) this.regReslutWrong = "邮箱已存在";
          else {
            if (data.code == 312) this.regReslutWrong = "手机号已存在";
            else this.regReslutWrong = "未知错误";
          }
        }
      }
    },
  },
});
</script>
