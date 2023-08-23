import { Component, OnChanges, OnInit, AfterViewInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { url } from 'inspector';
import { CookieService } from 'ngx-cookie-service';
import { MenuItem, MessageService } from 'primeng/api';
import { AuthService } from 'src/app/services/auth.service';
import { GooglemapService } from 'src/app/services/googlemap.service';

@Component({
  selector: 'app-loginpage',
  templateUrl: './loginpage.component.html',
  styleUrls: ['./loginpage.component.css'],
})
export class LoginpageComponent implements OnInit {
  loginForm!: FormGroup;
  [x: string]: any;

  itemsModule!: MenuItem[];
  constructor(
    private authService: AuthService, private router: Router,
    private messageService: MessageService, private dataApi: GooglemapService, private cookie: CookieService) {
    let token = localStorage.getItem('token');
    let tokenCookie = this.cookie.get('token');
    this.Checklogout = this.cookie.get('Checklogout');
    console.log('Checklogout', this.Checklogout)
    console.log('tokenCookie',  tokenCookie);
     
  

    // if (!tokenCookie && token) {
    //   this.cookie.set('token', token);
    //   this.cookie.set('userAgent', (window.navigator.userAgent).toString())
    // }
    // if (token || tokenCookie) {
    //   this.router.navigate(['/tracking']);
    // }
    console.log('Cookie', this.cookie.getAll());
    var IsCookieExist = this.cookie.check('JSESSIONID');
    console.log('Cookie Exist JSESSIONID', IsCookieExist);

    // if(this.cookie.get('JSESSIONID') !== ''){
    //   this.router.navigate(['/tracking']);
    // }
    console.log(this.cookie.get('JSESSIONID'));

  }

  // {
  //    "username": "root",
  //   "password": "84D9EAA2911748A55534B131E6306291"
  // }
  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });

    const userAgent = window.navigator.userAgent;
    console.log(userAgent);
    // alert(userAgent)
    // this.cookie.set('test', "ff jj");
    // alert(decodeURI(this.cookie.get('userAgent1')))
    // this.cookie.set('userAgent2',decodeURI(this.cookie.get('userAgent1')));


    this.itemsModule = [{
      label: 'All Modules',
      items: [{
          label: 'itemsModule1',
          icon: 'pi pi-external-link',
          url: '/'
      },{
        label: 'itemsModule2',
        icon: 'pi pi-external-link',
        url: '/'
    }]
  }];

this.getHref();

this.authService.getCheckLogout.subscribe(flag => {
  if(flag == true){

    var objempty = {
      "username": "",
      "password": ""
  }
  console.log(objempty)
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
//   this.loginPage(objempty);
//   },
//   (error: any) => {
//    console.log('errorloginCheckUser');
//    console.log(error);

//   //  this.authService.logoutUser().subscribe((Response: any)=> { 
//   //   console.log(Response)
//   //   setTimeout( ()=> {
//   //     this.router.navigate(["/"]);
//   //   }, 0)
//   // });

//     this.messageService.add({
//       severity: 'error',
//       summary: 'Error',
//       detail: "Please Enter UserName and Password",
//     });
//   }
// );

}else{
  console.log("username is Logout" , flag)
}
})





}
  

    

  // onSubmit(){
  //   // this.router.navigate(['/dashboard'])
  //   // this.valueRandam = this.createRandomToken()
  //   // console.log( this.valueRandam)
  //   this.authService.login(this.loginForm.value , this.valueRandam).subscribe((result:any)=>{
  //    // console.log(result)
  //       this.router.navigate(['/tracking'])
  //       this.messageService.add({severity:'success', summary: 'Success', detail: 'Done successfully'});

  //   },(error:any)=>{
  //     console.log(error)
  //     this.showErrorMessage = "Invalid username or password";
  //     this.messageService.add({severity:'error', summary: 'Error', detail: this.showErrorMessage});

  //   })
  // }

  onSubmit() {
    localStorage.clear();
    this.cookie.deleteAll();
    // console.log(this.loginForm.value)

    this.loginPage(this.loginForm.value);

    // this.authService.loginbyWeblogic(this.loginForm.value).subscribe(
    //   (result: any) => {},
    //   (error: any) => {
    //     console.log('loginbyWeblogic');
    //     console.log(error);
    //     if (error.status == 200 || error.status == 401) {
    //       console.log(this.cookie);
    //       console.log(this.cookie.get('JSESSIONID'));
    //       this.loginPage(this.loginForm.value);
    //     } else {
    //       this.showErrorMessage = 'Invalid username or password';
    //       this.messageService.add({
    //         severity: 'error',
    //         summary: 'Error',
    //         detail: this.showErrorMessage,
    //       });
    //     }
    //   }
    // );
  }


  loginPage(_f:any){
    this.authService.login(_f).subscribe(
      (result: any) => {
        console.log('resultloginPage');
        console.log(result);
        this.router.navigate(['/tracking1']);
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: 'Done successfully',
        });
      
      },
      (error: any) => {
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
      }
    );

  }
  getAddress() {
    this.dataApi.GetAddressLocation(29.9724739, 31.2361398).subscribe(
      (Response) => {
        // console.log(Response)
        this.addressF = Response.results[0].formatted_address;
      },
      (error: any) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: `Something went wrong  while getting address location`,
        });
      }
    );
  }

  createRandomToken() {
    var random_token = '';
    var characters =
      'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz';
    for (let x = 0; x < characters.length; x++) {
      random_token += characters.charAt(
        Math.floor(Math.random() * characters.length)
      );
    }
    return random_token;
  }


  getHref():string{
  let str= String(window.location.href).split(':');
  let ip = str[0] + ":" +str[1] + ":";
  let port = str[2].split('/')[0] + "/";
  let hrefhost = ip+port;
  // console.log("hrefhost");
  // console.log(hrefhost);
  this.cookie.set('Host', hrefhost);
  return hrefhost
}

}
