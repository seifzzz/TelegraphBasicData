import { DatePipe } from '@angular/common';
import { Component, ElementRef, EventEmitter, OnChanges, OnInit, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { info } from 'console';
import { json } from 'express';
import { MessageService } from 'primeng/api';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { GooglemapService } from 'src/app/services/googlemap.service';
import { LookupsService } from 'src/app/services/lookups.service';
declare var jquery:any;
declare var $:any;

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit , OnChanges {
  [x:string]:any;

  myForm!:FormGroup;
  @Output() getResponse = new EventEmitter;  
  checkType = {"label": "" , "value": ""};
  doneWork = false;
  disablebutton = true;
  showSecondButton = false;

  constructor( private dataApi:GooglemapService ,private lookupApi :LookupsService , 
    private auth :AuthService, private messageService : MessageService ) { 
    this.permSearch = this.auth.checkAuthPermisson("WorkerTrackingSearch.view");
    this.GetCurrentQUOTA();
    this.GetMaxQUOTA();
  }

  ngOnChanges(changes : any){
    //alert(changes)
  }
  
  ngOnInit(): void {
    this.Workers =[{"valuear": undefined,"dependcode": "-1","value": "-1","id": "-1"}];
    this.OrgRole =[{ 'value':undefined,'valuear':'-1'}];
  //this.getWorkersByOrgRole();

    this.WORK_ORDER_ID = null;
    this.items = [{label: "Worker Name" , value:  "1" } , {label: "Worker Order ID" , value:  "2" } , {label: "ORG ROLE" , value:  "3" }];

    this.myForm = new FormGroup({
      'searchType':new FormControl('' , [Validators.required]),
      // 'EMP_Role_ID':new FormControl(''),
      // 'WORK_ORDER_ID':new FormControl(''), // [Validators.pattern(/\-?\d*\.?\d{1,2}/)]
      'ORG_ROLE':new FormControl(''), 
      'all':new FormControl('' , [Validators.required]),
    })
    
  }

  OrgRole:any;
  searchObj:any;
  Workers:any; 
  getOUByOrgRole(){
   
    // console.log(this.OrgRole);
    // console.log("============")
    this.lookupApi.getOUByOrgRole().subscribe(Response=> {
     // console.log("getOUByOrgRole")
    //  console.log(Response)
      if(Response.statusCode == 200){ 
        this.OrgRole = Response.body;
      }else{
        this.OrgRole = []
      }
      this.loado = true;
    }),(error:any) =>{
      this.OrgRole = [];
      if(error?.error?.clientMessage){
          this.messageService.add({severity:'error', summary: 'Error', detail: `${error?.error?.clientMessage}`});
      }       
      else{
        this.messageService.add({severity:'error', summary: 'Error', detail: `Unknown Error`});
      }
    };
    // return this.OrgRole;
  }
  
  getWorkersByOrgRole() {
    this.lookupApi.getWorkersByOrgRole().subscribe(Response=> {
     // console.log("getWorkersByOrgRole");
    //  console.log(Response);
      if(Response.statusCode == 200){ 
        this.Workers = Response.body;
        this.WorkersNoFilter = Response.body;
        this.doneWork = true;
        this.onChangeORGROLE();
      }else{
        this.Workers = []
       }
    }),(error:any)=>{
      this.Workers = [];
      if(error?.error?.clientMessage){
          this.messageService.add({severity:'error', summary: 'Error', detail: `${error?.error?.clientMessage}`});
      }       
      else{
        this.messageService.add({severity:'error', summary: 'Error', detail: `Unknown Error`});
      }
    };

  }
  
  onChangeORGROLE=(e:any = this.searchObj) =>{
    //  console.log(e);
    this.searchObj = e;
    if(e == null){
      this.Workers =  this.WorkersNoFilter;
    }
    if(this.Workers[0]?.id == '-1' || e == undefined){
      return
    }
    this.Workers =  this.WorkersNoFilter;
    // console.log(this.WorkersNoFilter)
    if(e !== null &&  this.doneWork == true){
    let newArr = this.WorkersNoFilter.filter((obj: any ) => obj.dependcode == e.value);
   // console.log("filter");
   // console.log(newArr);
    this.Workers = newArr;
  }else{
    this.Workers =  this.WorkersNoFilter;
  }

  }
  
  onChangesearchType =(type:any) =>{
  //  alert(type);
    this.checkType = type;      
   // console.log(this.myForm)
    // this.myForm.reset();
    this.WORK_ORDER_ID = null
    this.myForm.get('all')?.reset(); 
   if(type !== null){
  
    if(type.value == 1){
      this.getOUByOrgRole();
      this.getWorkersByOrgRole();
     this.checkType = {"label": "" , "value": "1"};
     this.showSecondButton = false;
    }

    if(type.value == 2){
      this.checkType = {"label": "" , "value": "2"};
      this.showSecondButton = true;
    }

    if(type.value == 3){
      this.getOUByOrgRole();
      this.checkType = {"label": "" , "value": "3"};
      this.showSecondButton = false;

    }
  }else{
    this.checkType = {"label": "" , "value": ""};

  }


  }


  onKeyUpWorkOrder(event:any){
   // console.log(event.target.value);
   if(event.target.value !== "" && this.checkType.value == "2"){
    this.disablebutton = false
   }else{
    this.disablebutton = true
   }

 
 
  };

  canSearch(){
    if( 
    //   this.myForm.controls['ORG_ROLE'].value == undefined
    //  || this.myForm.controls['ORG_ROLE'].value == null ||
      this.myForm.controls['all'].value.valuear == undefined 
     || this.myForm.controls['all'].value.value == undefined){
     return false
    }
    return true
   }
  


  onSubmit(){
    localStorage.setItem("searchTypes" , JSON.stringify(""))
    localStorage.setItem("searchTypesValue" , JSON.stringify(""))
    this.getResponse.emit([]);
    this.loadingpage = true;
  //  console.log(this.myForm.value);
    this.searchTypes= this.myForm.value.searchType;
   // console.log(this.searchTypes)
   localStorage.setItem("searchTypes" , JSON.stringify(this.searchTypes))
   localStorage.setItem("searchTypesValue" , JSON.stringify(this.myForm.value.all))
   var type = this.myForm.value.searchType.value ;
    var dataValue = this.myForm.value.all ;
  //  console.log(dataValue);
    // Search By Worker Name
    if(type == 1){ 
      this.getWorkersByOrgRole();
      this.getOUByOrgRole();
      this.locations = [];
      this.dataApi.SearchByWorkerName(dataValue.id).subscribe(Response=> { //id = H628
     // console.log(Response)
        if(Response.statusCode == 200){ 
          this.loadingpage = false;
          this.locations = Response.body;
          this.getResponse.emit(this.locations);
          this.GetCurrentQUOTA();
          }
          else{
            this.locations = [];
            this.getResponse.emit(this.locations);
            this.GetCurrentQUOTA();
           }
        },
        (error:any) => {
          this.locations = [];
          this.getResponse.emit(this.locations);
          this.GetCurrentQUOTA();
          if(error?.error?.clientMessage){
            this.messageService.add({severity:'error', summary: 'Error', detail: `${error?.error?.clientMessage}`});
          }       
          // else if (this.MaxQUOTA?.split('=')[this.MaxQUOTA?.split('=')['length']-1] < this.CurrentQUOTA?.split('=')[this.CurrentQUOTA?.split('=')['length']-1] ){
          //   this.messageService.add({severity:'error', summary: 'Error', detail: `ORG ROLE reached Max QUOTA`});
          // }
          else{
            this.messageService.add({severity:'error', summary: 'Error', detail: `Unknown Error`});
          }

        });
        }
        // Search By Worker Order ID
        if(type == 2){
          this.locations = [];
          this.dataApi.SearchByWO(dataValue).subscribe(Response=> { //27714
         // console.log(Response)
          if(Response.statusCode == 200){ 
            this.locations = Response.body;
            this.getResponse.emit(this.locations);
            this.GetCurrentQUOTA();
          }else{
            this.locations = [];
            this.getResponse.emit(this.locations);
            this.GetCurrentQUOTA();
           }
        },
        (error:any) => {
          if(error?.error?.clientMessage){
          this.messageService.add({severity:'error', summary: 'Error', detail: `${error?.error?.clientMessage}`});
        }       
        else{
          this.messageService.add({severity:'error', summary: 'Error', detail: `Unknown Error`});
        }
          this.locations = [];
          this.getResponse.emit(this.locations);
          this.GetCurrentQUOTA();
        });
    }

    // Search By ORG ROLE
    if(type == 3){
      this.locations = [];
      this.dataApi.SearchByORGROLE(dataValue.value , 0 , 100).subscribe(Response=> { //MA1CA
     console.log(Response)
      if(Response.statusCode == 200 ){ 
        this.locations = Response.body;
        localStorage.setItem("searchTotalItems" ,  Response.developerMessage)
        this.getResponse.emit(this.locations);
        this.GetCurrentQUOTA();
      }else{
        this.locations = [];
        this.getResponse.emit(this.locations);
        this.GetCurrentQUOTA();
       }
    },
    (error:any) => {
      if(error?.error?.clientMessage){
          this.messageService.add({severity:'error', summary: 'Error', detail: `${error?.error?.clientMessage}`});
        }       
        else{
          this.messageService.add({severity:'error', summary: 'Error', detail: `Unknown Error`});
        }
      this.locations = [];
      this.getResponse.emit(this.locations);
      this.GetCurrentQUOTA();

    });
    }
   
  }

  reset(){
    this.myForm.reset();
    this.getResponse.emit("reset");
    this.checkType.value = ""
  }

  ngOnDestroy(){
   this.sub1?.unsubscribe(); 
   this.sub2?.unsubscribe(); 
  }
  
 sub1!:Subscription;
 sub2!:Subscription;
  GetCurrentQUOTA() {
    // alert("childd")
    this.sub2 = this.dataApi.GetCurrentQUOTA().subscribe((Response:any)=> {
    //  console.log("GetCurrentQUOTA")
    //  console.log(Response)
      if(Response.statusCode == 200){ 
        this.CurrentQUOTA = Response.body;
      }
    }
    ,(error:any) => {
      if(error?.error?.clientMessage){
          this.messageService.add({severity:'error', summary: 'Error', detail: `${error?.error?.clientMessage}`});
      }       
      else{
        this.messageService.add({severity:'error', summary: 'Error', detail: `Unknown Error`});
      }
    }
    );
  }

  GetMaxQUOTA() {
    this.sub1 = this.dataApi.GetMaxQUOTA().subscribe((Response:any)=> {
    //  console.log("GetMaxQUOTA")
    //  console.log(Response)
      if(Response.statusCode == 200){ 
        this.MaxQUOTA = Response.body;
      }
    },(error:any) => {
      if(error?.error?.clientMessage){
          this.messageService.add({severity:'error', summary: 'Error', detail: `${error?.error?.clientMessage}`});
      }       
      else{
        this.messageService.add({severity:'error', summary: 'Error', detail: `Unknown Error`});
      }
      });

  }
}
