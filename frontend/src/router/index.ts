import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import UsersView from '../views/UsersView.vue'
import KeywordsView from '../views/KeywordsView.vue'
import TownsView from '../views/TownsView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/users',
      name: 'users',
      component: UsersView
    },
    {
      path: '/keywords',
      name: 'keywords',
      component: KeywordsView
    },
    {
      path: '/towns',
      name: 'towns',
      component: TownsView
    }
  ]
})

export default router