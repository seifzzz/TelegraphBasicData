import { HttpClient, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import jwt_decode from "jwt-decode";
import { CookieService } from 'ngx-cookie-service';
import jwtDecode from 'jwt-decode';
import { catchError, tap } from "rxjs/operators";
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  URL : string = apiURL;
  private checkLogout = new BehaviorSubject(true);
  constructor(private http: HttpClient, private cookie:CookieService ) {}

  setCheckLogout(val:boolean){
    this.checkLogout.next(val);
  }

  getCheckLogout = this.checkLogout.asObservable();

  loginbyWeblogic(_f:any) { 
    // console.log(_f);
   // console.log(this.URL+"/api/auth/signin")
  //http://10.19.35.75:8003/demo/j_security_check?j_username=100001&j_password=123456789&submit=Login
  //post : http://10.19.35.75:8003/wl-spring-boot-realm-demo-1.0.1/j_security_check?j_username=root&j_password=root1234
  console.log(`http://10.19.35.75:8003/GISModule/j_security_check?j_username=${_f.username}&j_password=${_f.password}`);
     return this.http.post<any>( `http://10.19.35.75:8003/GISModule/j_security_check?j_username=${_f.username}&j_password=${_f.password}`, {} );
  //   .pipe(map(response=> {
  //     console.log("response");
  //     console.log(response);
  //     return response;
  // }));
}



// getHref():string{
//   if(String(window.location.href).includes('wfm.te.eg')){
//     return "http://10.19.35.75:8003/";
//   }
//   let str= String(window.location.href).split(':');
//   let ip = str[0] + ":" +str[1] + ":";
//   let port = str[2].split('/')[0] + "/";
//   let href = ip+port;
//   if (href == 'http://localhost:4200/'){
//     return "http://10.19.35.75:8003/";
//   }
//   return href
// }

// http://localhost:4200/#/
login(_f:any) { 
  // let href = this.getHref();
  let obj={
    "username": _f.username,
    "password": _f.password
    // "httpurl":'http://10.19.135.105:7777/'
  };
  // https://10.19.135.101:8001/
  // http://10.19.135.105:7777/
  return this.http.post<any>(this.URL+'/api/auth/signin' , obj ) 
  .pipe(map(response=> {
  //console.log("response");
  console.log(response);

    localStorage.setItem('token', response.token)
    localStorage.setItem('userName', response.username)
    localStorage.setItem('type', response.type)
    localStorage.setItem('authorities', response.authorities)
    this.cookie.set('token', response.token)
    this.cookie.set('userAgent', (window.navigator.userAgent).toString())
   
    console.log("this.cookie.get('authorities')")
    console.log(this.cookie.get('authorities'))

    console.log("this.cookie.get('userName')")
    console.log(this.cookie.get('userName'))


    // const decoded: any = jwt_decode(response.token);
    // console.log(decoded)
    return response;
}));
}

loginCheckUser() {

  const httpOptions = {
    headers: new HttpHeaders({
      "Cookie":'JSESSIONID='+this.cookie.get('JSESSIONID') ,
      "Host":this.cookie.get('Host'),
      "Postman-Token":"<calculated when request in sent>",
    })};

  console.log(`http://10.19.35.75:8003/GIS_Security_Auth/rest/v0/CurrentUser`)
  return this.http.get(`http://10.19.35.75:8003/GIS_Security_Auth/rest/v0/CurrentUser` , httpOptions);
 }

logoutUser() {
  this.cookie.set('Checklogout' , 'true');
  localStorage.clear();
  this.cookie.delete('token');
  this.cookie.delete('userAgent');
  this.cookie.delete('JSESSIONID'); 
  this.cookie.delete('Host'); 
  this.cookie.deleteAll();
  this.cookie.delete('SESSIONIDDEMO');
  // window.history.go();
  this.setCheckLogout(false);
 return this.http.get(this.URL+`/api/auth/Logout`);
}

getToken(): string {
 // console.log(localStorage.getItem('token'))
return localStorage.getItem('token') || '';
}



getTokenExpirationDate(token: string): any {
  token = this.getToken()
  const decoded: any = jwt_decode(token);

  if (decoded.exp === undefined) return null;

  const date = new Date(0);
  date.setUTCSeconds(decoded.exp);
  return date;
}

isTokenExpired(token?: string): boolean {
 //debugger;
  if (!token) token = this.getToken();
 // console.log(token);
  if (!token || token == "undefined") return false;

  const date = this.getTokenExpirationDate(token);
  if (date === undefined) return false;
  const d = date.valueOf();
  const nd = new Date().valueOf();
  const r = !(date.valueOf() > new Date().valueOf());
  return !(date.valueOf() > new Date().valueOf());
}

isTokenExpiredK(token?: string): boolean {
 //debugger;
  if (!token) token = this.cookie.get("token");
 // console.log(token);
  if (!token || token == "undefined") return false;

  const date = this.getTokenExpirationDate(token);
  if (date === undefined) return false;
  const d = date.valueOf();
  const nd = new Date().valueOf();
  const r = !(date.valueOf() > new Date().valueOf());
  return !(date.valueOf() > new Date().valueOf());
}




getAuthStatus(): boolean {
  if(localStorage.getItem('token')){
    // console.log("token");
    return true ;
  }else{
    return false;
  }
}



    //Check Permisson
public checkAuthPermisson(actionPermission: string){
  // debugger;
  let Allpermissions = localStorage.getItem("authorities");
  let perms = JSON.stringify(Allpermissions? Allpermissions : "");
 // console.log("Allpermissions");
 // console.log(perms);
   return perms.includes(actionPermission) ?  true : false;  
 }


}




    // getUserScopes(){
    //   //const token = this.getToken();
    //   //const decodedToken: string[] = jwt_decode(token?.split(" ")[1] || '');
    //   //let scopesStored = localStorage.getItem("scopes");
    //   //let scopes = JSON.parse(scopesStored? scopesStored : "");
    //   let permissionsStored = localStorage.getItem("permissions");
    //   return JSON.parse(permissionsStored? permissionsStored : "");;
    // }


  
    // public checkAuth(actionPermission: string){
    //   //const token = localStorage.getItem("token");
    //   //const decodedToken: string[] = jwt_decode(token?.split(" ")[1] || '');
    //   //return decodedToken["roles"].includes(actionPermission) ?  true : false;
    //   let permissionsStored = localStorage.getItem("permissions");
    //   let permissions = JSON.parse(permissionsStored? permissionsStored : "");
    //   //console.log("permi ", permissions);
    //   return permissions.includes(actionPermission) ?  true : false;
    // }

