import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GooglemapRoutingModule } from './googlemap-routing.module';
import { MapComponent } from './map/map.component';
import { SharedModule } from '../shared/shared.module';
import { SearchComponent } from './search/search.component';


@NgModule({
  declarations: [
    MapComponent,
    SearchComponent
  ],
  imports: [
    CommonModule,
    GooglemapRoutingModule,
    SharedModule
  ]
})
export class GooglemapModule { }
