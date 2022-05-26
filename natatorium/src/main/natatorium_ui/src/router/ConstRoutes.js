// import Home from "views/Home";
const login = () => import('views/login')
const NotFound = () => import('views/notFound')
// const basicMemberInfo = () => import("views/basicMemberInfo")
// const businessList = () => import("views/businessList")
// const menus = ()=>import("views/Menus")
// const permission = ()=>import("views/permission/permission")

const ConstRoutes = [
    {
        path:'/',
        name:'login',
        component:login
    },
    // {
    //     path:"*",
    //     name:"404",
    //     component:NotFound
    // }
    // {
    //     path: "/",
    //     name: "Home",
    //     component: Home,
    //     children:[
    //         {
    //             path: "/basicMemberInfo",
    //             component: basicMemberInfo
    //         },
    //         {
    //             path: "/businessList",
    //             component: businessList
    //         },
    //         {
    //             path: "/menus",
    //             component: menus
    //         },
    //         {
    //             path: "/permission",
    //             component: permission
    //         }
    //     ]
    // }
]

export default ConstRoutes