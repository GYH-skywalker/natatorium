import router from "../router";

export default {
    generateRoutes({ commit },menus){
        return new Promise(resolve => {
            // const DRoutes = []
            const home = {
                path:'/home',
                name:"Home",
                component:() => import("views/Home.vue"),
                children:[]
            }
            // router.addRoute(home)
            menus.forEach(PMenu => {
                if(PMenu.path){
                    console.log("test");
                    const routeMap = {
                        path:PMenu.path,
                        name:PMenu.name,
                        component:() => import("views/"+PMenu.pageUrl+".vue")
                    }
                    home.children.push(routeMap)
                    // router.addRoute("Home",routeMap)
                }
                PMenu.children.forEach(CMenu => {
                    if(CMenu.path){
                        const childrenRoute = {
                            path:CMenu.path,
                            name:CMenu.name,
                            component:() => import("views/"+CMenu.pageUrl+".vue")
                        }
                        home.children.push(childrenRoute)
                        // router.addRoute("Home",childrenRoute)
                    }
                })
            })
            console.log("home");
            console.log(home);
            console.log("home");
            // DRoutes.push(home)
            // console.log("Dr");
            // console.log(DRoutes);
            // console.log("DR");
            commit('setDynamicRoutes',home)
            resolve()
        })
    }
}