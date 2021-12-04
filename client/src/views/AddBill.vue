<template>
  <ion-content class="bill">
    <ion-list-header>
      <ion-Label>新建账本名称</ion-Label>
    </ion-list-header>

    <ion-item class="bill">
      <ion-input placeholder="请输入账本名称" v-model="name"></ion-input>
    </ion-item>

    <ion-radio-group v-model="share">
      <ion-list-header>
        <ion-label>账本类型</ion-label>
      </ion-list-header>

      <ion-item>
        <ion-label>共享账本</ion-label>
        <ion-radio slot="start" value="share"></ion-radio>
      </ion-item>

      <ion-item>
        <ion-label>本地账本</ion-label>
        <ion-radio slot="start" value="noshare"></ion-radio>
      </ion-item>
    </ion-radio-group>

    <ion-row>
      <ion-col align="center">
        <ion-button class="control" @click="addbill()">
          <ion-label class="addbill" color="light">确定</ion-label>
        </ion-button>
      </ion-col>
      <ion-col align="center">
        <ion-button class="control" @click="close()">
          <ion-label class="addbill" color="light">取消</ion-label>
        </ion-button>
      </ion-col>
    </ion-row>

    <ion-list-header>
      <ion-Label>加入共享账本</ion-Label>
    </ion-list-header>

    <ion-item class="bill">
      <ion-input placeholder="请输入邀请码" v-model="shareCode"></ion-input>
    </ion-item>

    <ion-row>
      <ion-col align="center">
        <ion-button class="control" @click="joinbill()">
          <ion-label class="addbill" color="light">确定</ion-label>
        </ion-button>
      </ion-col>
      <ion-col align="center">
        <ion-button class="control" @click="close()">
          <ion-label class="addbill" color="light">取消</ion-label>
        </ion-button>
      </ion-col>
    </ion-row>
  </ion-content>
</template>

<script>
import {
  IonContent,
  IonRadio,
  IonRadioGroup,
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
  name: "AddBill",
  data() {
    return {
      name: "",
      share: "noshare",
      shareCode: "",
    };
  },
  components: {
    IonContent,
    IonRadio,
    IonRadioGroup,
    IonButton,
    IonInput,
    IonItem,
    IonLabel,
  },
  methods: {
    async addbill() {
      const { Http } = Plugins;
      const ret = await Http.request({
        method: "POST",
        url: "http://124.71.224.228:8080/api/bill/create",
        data: {
          name: this.name,
          share: this.share == "share" ? 1 : 0,
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
    async close() {
      await popoverController.dismiss("nochange");
    },
    async joinbill() {
      const { Http } = Plugins;

      const ret = await Http.request({
        method: "POST",
        url: "http://124.71.224.228:8080/api/bill/join",
        data: {
          code: this.shareCode,
        },
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
      });
      const data = await ret.data;
      console.log(data);
      if (data.code == 200) {
        alert("加入成功");
        await popoverController.dismiss("changed");
      } else {
        alert("加入失败");
        await popoverController.dismiss("nochange");
      }
    },
  },
});
</script>

<style scoped>
ion-item.bill {
  --background: white;
}

ion-button.control {
  font-size: 15px;
  --background: rgb(245, 150, 100);
}
ion-content.bill {
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