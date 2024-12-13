import { createRouter, createWebHistory } from "vue-router";
import home from "../components/Home.vue";
import register from "../components/RegisterUser.vue";
import login from "../components/LoginUser.vue";
import order from "../components/Order.vue";
import addProduct from "../components/AddProduct.vue";
import logs from "../components/Logs.vue";
import confirmOrder from "../components/ConfirmOrder.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: home,
  },
  {
    path: "/registerUser",
    name: "register",
    component: register,
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
    component: addProduct,
  },
  {
    path: "/logs",
    name: "logs",
    component: logs,
  },
  {
    path: "/confirmOrder",
    name: "ConfirmOrder",
    component: confirmOrder,
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  linkActiveClass: "active-link",
});

export default router;

