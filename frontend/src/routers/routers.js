import { createRouter, createWebHistory } from "vue-router";
import home from "../components/Home.vue";
import register from "../components/RegisterUser.vue";
import login from "../components/LoginUser.vue";
import order from "../components/Order.vue";
import addProduct from "../components/AddProduct.vue";
import logs from "../components/Logs.vue";
import confirmOrder from "../components/ConfirmOrder.vue";
import pruebasMapa from "../components/pruebasMapa.vue";
import querry1 from "../components/Querry1.vue";
import querry2 from "../components/Querry2.vue";
import querry3 from "../components/Querry3.vue";

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
  },
  {
    path: "/pruebasMapa",
    name: "pruebasMapa",
    component: pruebasMapa,
  },
  {
    path: "/querry1",
    name: "querry1",
    component: querry1,
  },
  {
    path: "/querry2",
    name: "querry2",
    component: querry2,
  },
  {
    path: "/querry3",
    name: "querry3",
    component: querry3,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  linkActiveClass: "active-link",
});

export default router;

