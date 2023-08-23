import { DatePipe } from '@angular/common';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { AuditService } from 'src/app/services/audit.service';
import { LookupsService } from 'src/app/services/lookups.service';

@Component({
  selector: 'app-searchaudit',
  templateUrl: './searchaudit.component.html',
  styleUrls: ['./searchaudit.component.css'],
  providers: [ DatePipe ]
})
export class SearchauditComponent implements OnInit {

  [x:string]:any;
  myForm!:FormGroup;
  @Output() getResponse = new EventEmitter;  
  fromDate :any ='';
  ACTION_DATE :any ='';
  EMP_ROLE_ID :any = {"valuear": "","value": ""};
  ORG_ROLE :any = {"valuear": "","value": ""};
  OrgRole :any = [{"valuear": "-1","value": "-1"}];
  USERNAME :any ="";
  currentDate: any = new Date();



  constructor(private dataApi:AuditService , private messageService : MessageService , private datePipe: DatePipe , private lookupApi :LookupsService) { }

  ngOnChanges(changes:any){
  //alert("h")
  }
  
  ngAfterViewInit() {
    //alert("h")

  }

  ngOnInit(): void {

    this.getOUByOrgRole();
    // this.getWorkersByOrgRole();

    this.myForm = new FormGroup({
      'ACTION_DATE':new FormControl(''), // {value: 'Rahul', disabled: true} ,[Validators.required] Validators.pattern("[0-9]{11}")
      'ORG_ROLE':new FormControl(''),
      // 'EMP_ROLE_ID':new FormControl(''),
      'USERNAME':new FormControl(''),
    })
    this.OrgRole = [{"valuear": "-1","value": "-1"}];
    
    this.myForm.controls['ORG_ROLE'].valueChanges.subscribe(
      (res:any) =>{
        if(res?.value=="-1"){
          this.myForm.controls['ORG_ROLE'].patchValue(undefined);
        }
  })
  }

  getOUByOrgRole(){
    this.lookupApi.getOUByOrgRole().subscribe((Response:any)=> {
      // console.log("getOUByOrgRole")
      // console.log(Response)
      if(Response.statusCode == 200){ 
        this.OrgRole = Response.body;
      }else{
        this.OrgRole = []
       }
    });
  }

  getWorkersByOrgRole() {
    this.lookupApi.getWorkersByOrgRole().subscribe((Response:any)=> {
      // console.log("getWorkersByOrgRole")
      // console.log(Response)
      if(Response.statusCode == 200){ 
        this.Workers = Response.body;
        this.WorkersNoFilter = Response.body;
        this.doneWork = true;
      }else{
        this.Workers = []
       }
    },(error:any)=>{
      this.Workers = []
      if(error?.error?.clientMessage){
        this.messageService.add({severity:'error', summary: 'Error', detail: `${error?.error?.clientMessage}`});
      }       
      else{
        this.messageService.add({severity:'error', summary: 'Error', detail: `Unknown Error`});
      }
    });

  }

  onChangeORGROLE=(e:any) =>{
    console.log(e);
    this.Workers =  this.WorkersNoFilter;
    // console.log(this.WorkersNoFilter)
    if(e !== null &&  this.doneWork == true){
    let newArr = this.WorkersNoFilter.filter((obj: any )  =>obj.dependcode == e.value);
    // console.log("filter");
    // console.log(newArr);
    this.Workers = newArr;
  }else{
    this.Workers =  this.WorkersNoFilter;
  }

  }

  onSubmit(){
    // console.log(this.myForm.value);
    var obj:any = {
      "ACTION_DATE": this.datePipe.transform(this.myForm.value?.ACTION_DATE , 'dd-MMM-yy'),
      "USERNAME": this.myForm.value?.USERNAME,
      "ORG_ROLE": this.myForm.value?.ORG_ROLE?.value,
      // "EMP_ROLE_ID": this.myForm.value?.EMP_ROLE_ID?.value,
    }
    
    if(obj['ACTION_DATE']==null){
      obj['ACTION_DATE']=undefined
    }
    
    // console.log(obj)
    this.getResponse.emit(obj);
  }

  reset(){
    this.myForm.reset();
    this.getResponse.emit(-1);
  }

  clearSelection(e:any){
    this.myForm.controls['ORG_ROLE'].patchValue(undefined);
  }

  valid():boolean{
    let c = 0;
    let f = this.myForm.controls;
    for(let a in f){
      if(f[a].value == undefined){
        c++;
      }
      if(String(f[a].value).trim() == ''){
        f[a].patchValue(undefined);
      }
    }
    if(c==3){
      return true;
    }
    return false
  }    



}
