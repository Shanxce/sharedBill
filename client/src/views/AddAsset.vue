<template>
    <link href="assets/css/addAsset.css" rel="stylesheet" />
  <ion-content class="addasset">
    <ion-list-header>
      <ion-Label>新建资产名称</ion-Label>
    </ion-list-header>

    <ion-item class="asset">
      <ion-input placeholder="请输入资产名称" v-model="name"></ion-input>
    </ion-item>

    <ion-list-header>
      <ion-Label>新建资产金额</ion-Label>
    </ion-list-header>

    <ion-item class="asset">
      <ion-input placeholder="请输入资产金额" v-model="account"></ion-input>
    </ion-item>

    <ion-toolbar class="blank"></ion-toolbar>
    <ion-row>
      <ion-col align="center">
        <ion-button class="control_add" @click="addasset()">
          <ion-label class="addAsset" color="light">确定</ion-label>
        </ion-button>
      </ion-col>
      <ion-col align="center">
        <ion-button class="control_add" @click="close()">
          <ion-label class="addAsset" color="light">取消</ion-label>
        </ion-button>
      </ion-col>
    </ion-row>
  </ion-content>
</template>

<script>
import {
  IonContent,
  IonButton,
  IonItem,
  IonInput,
  IonLabel,
  popoverController,
} from "@ionic/vue";
import { defineComponent } from "vue";
import { Plugins } from "@capacitor/core";
import "@capacitor-community/http";

export default defineComponent({
  name: "AddAsset",
  data() {
    return {
      name: "",
      account: 0,
    };
  },
  components: { IonContent, IonButton, IonInput, IonItem, IonLabel },
  methods: {
    async close() {
      await popoverController.dismiss("nochange");
    },
    async addasset() {
      const { Http } = Plugins;

      const ret = await Http.request({
        method: "POST",
        url: "http://124.71.224.228:8080/api/assets/create",
        data: {
          name: this.name,
          amount: this.account,
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      });
      const data = await ret.data;
      console.log(data);
      if (data.code == 200) {
        alert("添加成功");
        await popoverController.dismiss("changed");
      } else {
        alert("添加失败");
        await popoverController.dismiss("nochange");
      }
    },
  },
});
</script>



