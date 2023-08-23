import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuditRoutingModule } from './audit-routing.module';
import { AllauditComponent } from './allaudit/allaudit.component';
import { SharedModule } from '../shared/shared.module';
import { SearchauditComponent } from './searchaudit/searchaudit.component';


@NgModule({
  declarations: [
    AllauditComponent,
    SearchauditComponent
  ],
  imports: [
    CommonModule,
    AuditRoutingModule,
    SharedModule
  ]
})
export class AuditModule { }
