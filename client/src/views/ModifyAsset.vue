<template>
  <ion-content class="asset">
    <ion-list-header>
      <ion-Label>修改资产名称</ion-Label>
    </ion-list-header>

    <ion-item class="asset">
      <ion-input placeholder="请输入资产名称" v-model="name"></ion-input>
    </ion-item>

    <ion-list-header>
      <ion-Label>修改资产金额</ion-Label>
    </ion-list-header>

    <ion-item class="asset">
      <ion-input placeholder="请输入资产金额" v-model="account"></ion-input>
    </ion-item>

    <ion-toolbar class="blank"></ion-toolbar>
    <ion-row>
      <ion-col align="center">
        <ion-button class="control" @click="addasset()">
          <ion-label class="addAsset" color="light">确定</ion-label>
        </ion-button>
      </ion-col>
      <ion-col align="center">
        <ion-button class="control" @click="close()">
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
  name: "ModifyAsset",
  props: {
    id: { type: Number },
  },
  data() {
    return {
      name: "",
      account: "",
    };
  },
  components: {
    IonContent,
    IonButton,
    IonInput,
    IonItem,
    IonLabel,
  },
  methods: {
    async close() {
      await popoverController.dismiss("nochange");
    },
    async addasset() {
      const { Http } = Plugins;

      const ret = await Http.request({
        method: "POST",
        url: "http://124.71.224.228:8080/api/assets/modify",
        data: {
          id: this.id,
          name: this.name,
          account: this.account,
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      });
      const data = await ret.data;
      console.log(data);
      if (data.code == 200) {
        await popoverController.dismiss({id:this.id, msg:"changed"});
        alert("修改成功");
      } else {
        await popoverController.dismiss({id:this.id, msg:"nochange"});
        alert("修改失败");
      }
    },
  },
});
</script>

<style scoped>
ion-item.asset {
  --background: white;
}

ion-button.control {
  font-size: 15px;
  --background: rgb(245, 150, 100);
}
ion-content.asset {
  --background: white;
}

ion-toolbar.blank {
  max-height: 25px;
  --background: white;
}

ion-input.input {
  font-size: 20px;
}
</style>


