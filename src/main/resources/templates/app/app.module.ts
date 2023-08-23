import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HttpClientXsrfModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { SharedModule } from './shared/shared.module';
import { MessageService } from 'primeng/api';
import { AuthInterceptor } from './interceptor/auth.interceptor';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { CookieService } from 'ngx-cookie-service';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SidebarComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule, 
    SharedModule,
    HttpClientXsrfModule.withOptions({
      cookieName: 'app-Xsrf-Cookie',
      headerName: 'app-Xsrf-Header',
      }),
  ],
  providers: [
    {provide: MessageService },
    {provide: CookieService }, //npm install ngx-cookie-service 
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    {provide : LocationStrategy , useClass : HashLocationStrategy}, // Solved Reload Page on server

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

