import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
[x:string]:any;
  constructor(private router: Router , private auth:AuthService, private cookie:CookieService) {
    this.userName = localStorage.getItem('userName')
    this.permSearch = this.auth.checkAuthPermisson("WorkerTrackingSearch.view")
    this.permAudit = this.auth.checkAuthPermisson("WorkerTrackingAudit.view")

   }

  ngOnInit(): void {
  }
  
  logout(){
    // this.auth.logoutUser();
    this.auth.logoutUser().subscribe(Response=> { 
      console.log(Response)
      setTimeout( ()=> {
        this.router.navigate(["/"]);
        // window.history.go();
      }, 0)
    });
  }

  // logoutHelper(){
  //   localStorage.clear();
  //   this.cookie.delete('token');
  //   this.cookie.deleteAll();
  //   this.router.navigate(["/"]);
  // }
}

