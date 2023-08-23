import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { PrimengModule } from '../primeng/primeng.module';
import { LoginpageComponent } from './loginpage/loginpage.component';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [
    LoginpageComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    SharedModule
   
    ]
})
export class AuthModule { }
