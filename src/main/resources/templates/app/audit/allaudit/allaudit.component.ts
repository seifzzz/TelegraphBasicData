import { formatDate } from '@angular/common';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import * as FileSaver from 'file-saver';
import { MessageService, PrimeNGConfig } from 'primeng/api';
import { Paginator } from 'primeng/paginator';
import { Subscription } from 'rxjs';
import { AuditService } from 'src/app/services/audit.service';
import { AuthService } from 'src/app/services/auth.service';
declare var $: any;

@Component({
  selector: 'app-allaudit',
  templateUrl: './allaudit.component.html',
  styleUrls: ['./allaudit.component.css']
})
export class AllauditComponent implements OnInit {

  [x:string]:any;
  first:any = 0;
  last:any = 0;
  totalRecords:any = 0;
  totalRow:any = 0;
  
  getdatabysearch:boolean = false;
  sub!:Subscription;
  
  @ViewChild('paginator', { static: true }) paginator!: Paginator
  @ViewChild('dt1') myTable:any; 
  
  constructor(private dataApi:AuditService , private auth:AuthService, private messageService:MessageService) {
    this.permAudit = this.auth.checkAuthPermisson("WorkerTrackingAudit.view")
  }

  ngOnInit(): void {
   // alert("ngOnInit")
   if(this.permAudit == true){
  //  this.getAllaudit();
   }

    this.cols = [
      { field: 'username', header: 'User Name' , display:1},
      { field: 'action_NAME', header: 'Action Name' , display:1},
      { field: 'action_DATE', header: 'Action Date',display:1 },
      { field: 'action_DESCRIPTION', header: 'Action Description', display:1},
    //{ field: 'emp_ROLE_ID', header: 'Emp Role ID', display:0},
      { field: 'org_ROLE', header: 'Org Role', display:1}
  ];

  this.selectedcols = this.cols.filter((col: any) =>  col.display == 1);
  this._selectedColumns =  this.selectedcols ;
  }


   
  @Input() get selectedColumns(): any[] {
    return this._selectedColumns;
  }
  set selectedColumns(val: any[]) {
    //restore original order
    this.selectedcols = val;
    this._selectedColumns = this.cols.filter((col: any) => val.includes(col));
    this._selectedColumns =  this.selectedcols
   // console.log(this.selectedcols)
  }

  resetOnlyTable():void{
    $('#inputID').val('');
    this.myTable.reset();
  }

  getAllaudit(){
    this.loading = true;
    this.sub = this.dataApi.getAllSysAudit().subscribe(
      Response=> {
        //console.log(Response)
        this.AllSysAudit = Response.body;
        this.loading = false;
      },
      (error) => {
        if(error?.error?.clientMessage){
          this.messageService.add({severity:'error', summary: 'Error', detail: `${error?.error?.clientMessage}`});
        }       
        else{
          this.messageService.add({severity:'error', summary: 'Error', detail: `Unknown Error`});
        }
       // console.log(error);
        this.AllSysAudit = [];
        this.loading = false;
      // bootbox.alert({title: "<span style='color:#a33;font-weight: 500; font-size: 16px'>" + "Warning message" + "</span>", message: "<span style='color:Black;font-weight: 500; font-size: 16px'>" + "No data found" + "</span>"});
      }
     );
}

ngOnDestroy(){
  this.sub?.unsubscribe();
}

getsearchAudit(_pageNo:any , _totalSize:any , _first:any ,_last:any , obj:any){
  if(this.sub?.closed == false ){
    this.sub?.unsubscribe();
  }
  if(obj==-1 && this.permAudit == true){
    this.resetOnlyTable();
    this.AllSysAudit = [];
    // this.getAllaudit();
    this.first = 0;
    this.last = 0;
    this.totalRecords = 0;
    this.totalRow = 0;

    return
  }
  this.loading = true;
  this.dataApi.searchAudit(obj , _pageNo , _totalSize).subscribe(
    Response=> {
     console.log(Response)
      this.AllSysAudit = Response.body;
      // this.totalItems = Response.totalItems;
      // this.totalRecords = Response.totalItems;
      // this.totalPages = Response.totalPages;
      // this.currentPage = Response.currentPage;
      // this.totalItems = 10;
      this.totalRecords = parseInt(Response.developerMessage);
      this.first= _first;
      this.last = _last;
      this.loading = false;
    },
    (error) => {                              
     // console.log(error);
      this.AllSysAudit = [];
      this.loading = false;
      if(error?.error?.clientMessage){
        this.messageService.add({severity:'error', summary: 'Error', detail: `${error?.error?.clientMessage}`});
      }       
      else{
        this.messageService.add({severity:'error', summary: 'Error', detail: `Unknown Error`});
      }
    // bootbox.alert({title: "<span style='color:#a33;font-weight: 500; font-size: 16px'>" + "Warning message" + "</span>", message: "<span style='color:Black;font-weight: 500; font-size: 16px'>" + "No data found" + "</span>"});
    }
   );

}



exportExcel() {
  //npm install xlsx
  import('xlsx').then((xlsx): void => {
  // console.log( document.getElementById('dt1'));
  this.dataID = document.getElementById('dt1');
  const worksheet = xlsx.utils.table_to_sheet(this.dataID);
  //  const worksheet = xlsx.utils.json_to_sheet(this.AllSysAudit);
    const workbook = { Sheets: { 'data': worksheet }, SheetNames: ['data'] };
    const excelBuffer: any = xlsx.write(workbook, { bookType: 'xlsx', type: 'array' });
    this.saveAsExcelFile(excelBuffer, "SysAudit");
  });
}

saveAsExcelFile(buffer: any, fileName: string): void {
  let EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
  let EXCEL_EXTENSION = '.xlsx';
  const data: Blob = new Blob([buffer], {
    type: EXCEL_TYPE
  });
  FileSaver.saveAs(data, fileName + '_' + formatDate(new Date(), "dd-MMM-YYYY hh:mm", 'en-US') + EXCEL_EXTENSION);
}



// data Search 
getDoneSearch($event: any) {
  this.loading = true;
 // console.log($event);
  this.objectbySearch = $event;
  this.loading = false;
  this.getdatabysearch = true;
  //this.getAllaudit();
  this.totalRecords = 50;
  this.totalRow = 50;
  // page , row , first , last , objsearch
  this.getsearchAudit( 0 , this.totalRow , 1 , this.totalRecords , this.objectbySearch);

}

changeFilter(){
 // alert("jj")
}




paginate(event:any) {
  //  console.log(event);
  //  console.log("page" +  event.page);
  //  console.log("first" + event.first);
  // console.log("rows" + event.rows);
  // console.log("pageCount" + event.pageCount);

  this.first= event.first + 1;
  this.last = (event.page +1) *  event.rows;
  if(event.page ==  event.pageCount -1){
    this.last = this.totalRecords ;
  }

  if(this.getdatabysearch == false){
    this.getsearchAudit(event.page,event.rows , this.first , this.last , {})
  }

  if(this.getdatabysearch == true){
    this.getsearchAudit(event.page, event.rows , this.first , this.last , this.objectbySearch)
  }

}


}
