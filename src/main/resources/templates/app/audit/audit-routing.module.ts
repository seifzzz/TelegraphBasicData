import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllauditComponent } from './allaudit/allaudit.component';

const routes: Routes = [
  {path:'', component: AllauditComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuditRoutingModule { }
