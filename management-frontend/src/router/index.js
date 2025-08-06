import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import DevicesView from '../views/DevicesView.vue'
import AdminUsersView from '../views/AdminUsersView.vue'
import AdminDevicesView from '../views/AdminDevicesView.vue'
import UnauthorizedView from '../views/UnauthorizedView.vue'
import UserChatView from '../views/UserChatView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView,
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/devices',
    name: 'devices',
    component: DevicesView,
    meta: {
      requiredRole: 'ROLE_USER'
    }
  },
  {
    path: '/admin/users',
    name: 'admin',
    component: AdminUsersView,
    meta: {
      requiredRole: 'ROLE_ADMIN'
    }
  },
  {
    path: '/admin/devices',
    name: 'adminDevices',
    component: AdminDevicesView,
    meta: {
      requiredRole: 'ROLE_ADMIN'
    }
  },
  {
    path: '/unauthorized',
    name: 'unauthorized',
    component: UnauthorizedView
  },
  {
    path: '/userchat',
    name: 'userchat',
    component: UserChatView
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const user = JSON.parse(localStorage.getItem('user'))
  console.log('entered before each')
  if(to.meta == null) {
    return next();
  }
  if (to.meta.requiredRole == null) {
    return next();
  }

  console.log('role required')
  if(user != null) {
    console.log('user not null')
    if(user.role.localeCompare(to.meta.requiredRole) == 0) {
      console.log('role match')
      return next();
    }
  }
  console.log('else')
  router.push('/unauthorized')
});

export default router
