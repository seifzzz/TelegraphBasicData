import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  [x:string]:any;
  constructor(private router: Router , private authService : AuthService , private cookie:CookieService ) { }
  //canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot)
  canActivate():boolean|Observable<boolean>{ 
    // var isAuthenticated = this.authService.getAuthStatus();
    // console.log(isAuthenticated);
    var token = localStorage.getItem('token');
    var tokenCookie = this.cookie.get('token');
    var Checklogout = this.cookie.get('Checklogout');

    console.log("canActivate")
    console.log('token' , token)
    console.log('tokenCookie' , tokenCookie)
    this.openSession = false ;

    var objempty = {
      "username": "",
      "password": ""
  }
    this.loginPage(objempty);

    // this.authService.loginCheckUser().subscribe(
    //   (result: any) => {
    //     console.log('loginCheckUser');
    //     console.log(result);
    //     var objempty = {
    //       "username": result.items[0].UserName,
    //       "password": ""
    //   }
    //   console.log(objempty)
    //   this.authService.login(objempty).subscribe(
    //     (result: any) => {
    //       console.log('result');
    //       console.log(result);
    //       this.openSession = true ;
    //     });
   
    //   },
    //   (error: any) => {
    //    console.log('errorloginCheckUser');
    //    console.log(error);
    //    this.openSession = false ;

    //    this.authService.logoutUser().subscribe((Response: any)=> { 
    //     console.log(Response)
    //     setTimeout( ()=> {
    //       this.router.navigate(["/"]);
    //     }, 0)
    //   });

    //   }
    // );
  

    if(token) // tokenCookie
    {
      return true
    }
    else if(this.openSession == true){
      return true;
    }
    else if(token == null || Checklogout == 'true' || this.openSession == false){
      this.cookie.deleteAll();
      this.router.navigateByUrl("");
      return false;
    }
    this.router.navigateByUrl("");
    // this.cookie.deleteAll();
    // localStorage.clear();
    return false;
   }


   loginPage(_f:any){
    this.authService.login(_f).subscribe(
      (result: any) => {
        console.log('result');
        console.log(result);
          this.openSession = true ;
        this.router.navigate(['/tracking1']);
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Done successfully',
        });
      
      },
      (error: any) => {
        console.log('errorlogincheck');
        console.log(error);

        if (error?.error?.clientMessage?.includes('locked')) {
          this.showErrorMessage = error.error.clientMessage;
        } else {
          this.showErrorMessage = 'Invalid username or password';
        }
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: this.showErrorMessage,
        });

        this.openSession = false ;

       this.authService.logoutUser().subscribe((Response: any)=> { 
        console.log(Response)
        setTimeout( ()=> {
          this.router.navigate(["/"]);
        }, 0)
      });

      }
    );

  }
  
}






