import { createRouter, createWebHistory } from "vue-router";
import Home from "../components/Home.vue";
import Register from "../components/RegisterUser.vue";
import login from "../components/LoginUser.vue";
import order from "../components/Order.vue";
import AddProduct from "../components/AddProduct.vue";
import logs from "../components/Logs.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/registerUser",
    name: "Register",
    component: Register,
  },
  {
    path: "/login",
    name: "login",
    component: login,
  },
  {
    path: "/order",
    name: "order",
    component: order,
  },
  {
    path: "/addProduct",
    name: "AddProduct",
    component: AddProduct,
  },
  {
    path: "/logs",
    name: "logs",
    component: logs,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  linkActiveClass: "active-link",
});

export default router;