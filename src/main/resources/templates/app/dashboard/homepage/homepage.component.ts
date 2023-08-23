import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import * as FileSaver from 'file-saver';
import {MessageService} from 'primeng/api';


@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
  providers: [MessageService],

})
export class HomepageComponent implements OnInit {
  [x:string]:any
  cars = [
    { vin: '01011155', brand: 'Vin' },
    { vin: '0111', brand: '404s' },

];

  constructor() {}
  ngOnInit() {

    
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
  
 
  

}
