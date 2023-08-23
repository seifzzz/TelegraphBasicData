import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { PrimengModule } from '../primeng/primeng.module';
import { LoadingComponent } from './loading/loading.component';



@NgModule({
  declarations: [
  
    LoadingComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule, 
    PrimengModule ,
    
  ],
  exports:[
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule, 
    PrimengModule ,
  ]
})
export class SharedModule { }

