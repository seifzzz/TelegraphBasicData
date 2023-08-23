import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './interceptor/auth.guard';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
//import {AuthGuardService} from './interceptor/auth.guard';
const routes: Routes = [
  // {path:'homepage',loadChildren:()=>import('./dashboard/dashboard.module').then(m=>m.DashboardModule)},
  {path:'dashboard',component:SidebarComponent,children:[
    {path:'',loadChildren:()=>import('./dashboard/dashboard.module').then(module=>module.DashboardModule) , canActivate:[AuthGuard]}  //, canActivate:[AuthGuard]
  ]},
  
 
  {path:'tracking1',component:SidebarComponent,children:[
    {path:'',loadChildren:()=>import('./googlemap/googlemap.module').then(module=>module.GooglemapModule)  , canActivate:[AuthGuard]} 
  ]},

  {path:'test',component:SidebarComponent,children:[
    {path:'',loadChildren:()=>import('./test/test.module').then(module=>module.TestModule) } 
  ]},


  {path:'audit',component:SidebarComponent,children:[
    {path:'',loadChildren:()=>import('./audit/audit.module').then(module=>module.AuditModule) , canActivate:[AuthGuard]} 
  ]},


  {path:'',loadChildren:()=>import('./auth/auth.module').then(m=>m.AuthModule)},
  {path:'**',redirectTo:'',pathMatch:'full'},
  


  
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
 }
