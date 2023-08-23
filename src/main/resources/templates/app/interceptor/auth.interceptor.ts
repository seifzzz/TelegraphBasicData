import { Injectable } from '@angular/core';
import {HttpRequest,HttpHandler, HttpEvent,HttpInterceptor} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';
import { CookieService } from 'ngx-cookie-service';
declare var bootbox:any;
declare var $:any;

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  URL : string = apiURL;

  constructor(private router: Router, private authService: AuthService , private cookie:CookieService) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    //debugger;
    var token = this.authService.getToken();
    var tokenCookie = this.cookie.get('token');
// (token != null && this.authService.isTokenExpired())
    if ( tokenCookie != null && this.authService.isTokenExpiredK()) {
      //debugger;
      const s = this.authService.isTokenExpired();
      //this.authService.logoutUser();
      localStorage.clear();
      this.router.navigate(['/']);
      bootbox.alert({title: "<span style='color:#a33;font-weight: 500; font-size: 16px'>" + "Information message" + "</span>", message: "<span style='color:black;font-weight: 500; font-size: 16px'> Session Timed Out! Please Login </span>",
      callback: () =>{ OK: bootbox.hideAll(); }});
      console.log("Session Timed Out");
      return throwError("Session Timed Out")
    } else{
      
          // URL Address
          // alert(request.url)
          if(request.url.includes('https://maps.googleapis.com/maps/api/geocode/json') == true ){ //|| request.url.includes('http://10.19.35.75:8003/GIS_Security_Auth/rest/v0/CurrentUser') == true
            //alert("jjjj")
            return next.handle(request)
            .pipe(
              tap(event => {
              }, error => {
              })
            )
        }

   
    else { 
      // alert("req")
     // console.log(request.method)

     console.log("cookie-storage", token);
     if(!token){
      this.router.navigateByUrl("");
     }

     var IsCookieExist = this.cookie.check('JSESSIONID');
     console.log("authRquest Cookie Exist JSESSIONID",IsCookieExist);
    //  console.log(this.URL)
    //  console.log(window.location.href)
    //  console.log(window.location.hostname)
      // let str= this.URL.split('/');
      // let port = str[0]+'//'+str[2].split('/')[0] ;
      // console.log(port)
      console.log(this.cookie.get('Host'))
      const authRquest = request.clone({
        setHeaders: {
          "Authorization":'Bearer'+' '+this.cookie.get('token') , // token - this.cookie.get('token')
          "Host":this.cookie.get('Host'),
          "Cookie":'JSESSIONID='+this.cookie.get('JSESSIONID') ,
          "Access-Control-Allow-Origin":"*",
          "Access-Control-Allow-Headers":"*",
          'Content-Type': 'application/json' ,
          'Access-Control-Allow-Credentials': 'true',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, PATCH, POST, DELETE, OPTIONS'
        }
      })
      // console.log(authRquest.headers.append('X-XSRF-TOKEN', this.cookie.get('tokenRandom')))

      return next.handle(authRquest)
        .pipe(
          tap(event => {}, error => {
            console.log("errorInterceptor");
            console.log(error);

            if(error?.url?.includes("Logout") && error?.status == 200){
              localStorage.clear();
              this.cookie.deleteAll();
              this.router.navigateByUrl('/');
              bootbox.hideAll();
            }

            else if(error?.url?.includes("signin")){
              return
            }

            else if(error?.message == 'You Are Logged Out because of Timeout' || error?.error?.message == 'You Are Logged Out because of Timeout'){
              let err = error?.message;
              if(err){
                err = error?.error?.message;
              }
              localStorage.clear();
              this.cookie.deleteAll();
              this.router.navigate(['/']);
              bootbox.alert({title: "<span style='color:#a33;font-weight: 500; font-size: 16px'>" + "Information message" + "</span>", message: `<span style='color:black;font-weight: 500; font-size: 16px'> ${err} </span>`,
              callback: () =>{ OK: bootbox.hideAll(); }});
            }
            // else if(error.status == 401 && (!window.location.href.endsWith('WFMGIS/#/') ||!window.location.href.endsWith('WFMGIS/')) || (error?.error?.status?.includes('UNAUTHORIZED'))){
            // bootbox.alert({title: "<span style='color:#a33;font-weight: 500; font-size: 16px'>" + "Information message" + "</span>", message: `<span style='color:black;font-weight: 500; font-size: 16px'> ${error?.error?.clientMessage} </span>`,
            // callback: () =>{ OK: bootbox.hideAll(); }});
            // // localStorage.clear();
            // // this.cookie.deleteAll();
            // // this.router.navigate(['/']);  
            // }
          
            else if(error?.status == 403 && (!window.location.href.endsWith('WFMGIS/#/') || !window.location.href.endsWith('WFMGIS/'))) { // || error.error.error_message.indexOf('expired')
           localStorage.clear();
            this.cookie.deleteAll();
            this.router.navigate(['/']);
            bootbox.alert({title: "<span style='color:#a33;font-weight: 500; font-size: 16px'>" + "Information message" + "</span>", message: `<span style='color:black;font-weight: 500; font-size: 16px'> Your session has been expired </span>`,
            callback: () =>{ OK: bootbox.hideAll(); }});
          }
        })
      )

    
    }

  }
  }
}





