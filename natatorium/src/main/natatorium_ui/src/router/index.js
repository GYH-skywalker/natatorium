import Vue from "vue";
import VueRouter from "vue-router";
import routes from "./ConstRoutes";
import store from 'store/index'

Vue.use(VueRouter);

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});
export const setDynamicRoutes = () =>{
  const currentRouteName = router.getRoutes().map(r => r.name)
  if(!currentRouteName.includes("Home")){
    router.addRoute(store.getters.getRoutes)
  }
}
// const routerPush = VueRouter.prototype.push
// VueRouter.prototype.push = function push (location) {
//   return routerPush.call(this, location).catch(error => error)
// }
router.beforeEach((to,from,next)=>{
  const token = localStorage.getItem("token")

    if(to.path === "/"){
      localStorage.removeItem("menus")
      localStorage.removeItem("token")
      next()
    }else{
      if(token !== null){
        const menus =JSON.parse(localStorage.getItem("menus"))
        store.dispatch("generateRoutes",menus).then(() => {
          setDynamicRoutes()
          store.commit("setMenus",menus)
          // console.log("to");
          // console.log(store.getters.getRoutes);
          // console.log(to);
        })
        next();
      }else{
        next("/")
      }
      next()

    // console.log(router.getRoutes());
    // const currentRouteName = router.getRoutes().map(r => r.name)
    // if(!currentRouteName.includes("Home")){
    //   setDynamicRoutes()
    //   next({...to,replace:true})
    // }else{
    //   next()
    // }
  }

})


export default router;
