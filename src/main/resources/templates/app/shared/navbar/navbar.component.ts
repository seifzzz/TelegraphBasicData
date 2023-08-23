import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
[x:string]:any;
  constructor() { }

  ngOnInit(): void {
    this.gfg = true;
    this.visibleSidebar1 = true;

  }

}
