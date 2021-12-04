import { createRouter, createWebHistory } from '@ionic/vue-router';
import { RouteRecordRaw } from 'vue-router';

import Tabs from '../components/Tabs.vue'


const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    component: () => import('@/views/Login.vue')
  },
  {
    path:'/register',
    component: () => import('@/views/Register.vue')
  },
  {
  path: '/tabs/',
    component: Tabs,
    children: [
      {
        path: '',
        redirect: '/tabs/bill'
      },
      {
        path: 'bill',
        component: () => import('@/views/Bill.vue')
      },
      {
        path: 'asset',
        component: () => import('@/views/Asset.vue')
      },
      {
        path: 'my',
        component: () => import('@/views/SelfInfo.vue')
      }
    ]
  },
  {
    path:'/assetd',
    name: "AssetDetail",
    component: () => import('@/views/AssetDetail.vue')
  },
  {
    path:'/infoChange',
    name: "infoChange",
    component: () => import('@/views/InfoChange.vue')
  },
  {
    path:'/billd',
    name: "BillDetail",
    component: () => import('@/views/BillDetail.vue')
  },
  {
    path:'/billc',
    name: "BillContent",
    component: () => import('@/views/BillContent.vue')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
