import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {ButtonModule} from 'primeng/button';
import {ListboxModule} from 'primeng/listbox';
import {InputTextModule} from 'primeng/inputtext';
import {BreadcrumbModule} from 'primeng/breadcrumb';
import {SplitButtonModule} from 'primeng/splitbutton';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {InputMaskModule} from 'primeng/inputmask';
import {PasswordModule} from 'primeng/password';
import {TableModule} from 'primeng/table';
import {ToastModule} from 'primeng/toast';
import {CalendarModule} from 'primeng/calendar';
import {SliderModule} from 'primeng/slider';
import {MultiSelectModule} from 'primeng/multiselect';
import {ContextMenuModule} from 'primeng/contextmenu';
import {DialogModule} from 'primeng/dialog';
import {DropdownModule} from 'primeng/dropdown';
import {ProgressBarModule} from 'primeng/progressbar';
import {MenubarModule} from 'primeng/menubar';
import {ToolbarModule} from 'primeng/toolbar';
import {PanelMenuModule} from 'primeng/panelmenu';

import {TreeModule} from 'primeng/tree';
import {SplitterModule} from 'primeng/splitter';
import {RippleModule } from 'primeng/ripple';
import {RadioButtonModule} from 'primeng/radiobutton';
import {CheckboxModule} from 'primeng/checkbox';
import {OrganizationChartModule} from 'primeng/organizationchart';
import {PaginatorModule} from 'primeng/paginator';
import {DividerModule} from 'primeng/divider';
import {TabViewModule} from 'primeng/tabview';
import {CarouselModule} from 'primeng/carousel';
import { PanelModule } from "primeng/panel";
import {CardModule} from 'primeng/card';
import {DataViewModule} from 'primeng/dataview';
import {ChipModule } from 'primeng/chip';
import {AccordionModule} from 'primeng/accordion';
import {BadgeModule} from 'primeng/badge';
import {GMapModule} from 'primeng/gmap';
import {TooltipModule} from 'primeng/tooltip';

import {ConfirmPopupModule} from 'primeng/confirmpopup';
import {ChartModule} from 'primeng/chart';
import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import {ScrollTopModule} from 'primeng/scrolltop';
import {ProgressSpinnerModule} from 'primeng/progressspinner';
import {SidebarModule} from 'primeng/sidebar';
import {FieldsetModule} from 'primeng/fieldset';

import {MenuModule} from 'primeng/menu';


const primengModules = [
  SidebarModule,
  ButtonModule,
  SplitButtonModule,
  ListboxModule,
  InputTextModule,
  PasswordModule,
  InputMaskModule,
  InputTextareaModule,
  BreadcrumbModule,
  ToolbarModule,
  PanelMenuModule,
   TableModule,
  MenubarModule,
  CalendarModule,
  SliderModule,
  DialogModule,
  MultiSelectModule,
  ContextMenuModule,
  DropdownModule,
  ButtonModule,
  ToastModule,
  InputTextModule,
  ProgressBarModule,
  TreeModule,
  SplitterModule,
  RippleModule,
  RadioButtonModule,
  CheckboxModule,
  OrganizationChartModule,
  PaginatorModule,
  DividerModule,
  TabViewModule,
  CarouselModule,
  PanelModule,
  CardModule,
  DataViewModule,
  ChipModule,
  AccordionModule,
  BadgeModule,
  GMapModule,
  ConfirmPopupModule,
  TooltipModule,
  MessageModule,
  MessagesModule,
  ScrollTopModule,
  ProgressSpinnerModule,
  FieldsetModule,
  MenuModule,


];

@NgModule({
  imports: [
    CommonModule,
    ...primengModules
  ],
  exports: [
    ...primengModules
  ],
})


export class PrimengModule { }

//npm install @angular/cdk --save
