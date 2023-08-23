import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TestRoutingModule } from './test-routing.module';
import { MapComponent } from './map/map.component';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [
    MapComponent
  ],
  imports: [
    CommonModule,
    TestRoutingModule,
    SharedModule
  ]
})
export class TestModule { }
