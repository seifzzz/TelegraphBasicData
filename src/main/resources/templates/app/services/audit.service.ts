import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
declare var apiURL : any;

@Injectable({
  providedIn: 'root'
})
export class AuditService {

  URL : string = apiURL;

  constructor(private http: HttpClient) { }

  getAllSysAudit(): Observable<any> {
   // console.log(this.URL+`/data/getAllAudit`);
    return this.http.get(this.URL+`/data/getAllAudit`); 
  }
  searchAudit(_f:any , pageNO :any, pageSize :any): Observable<any> {
   //console.log(_f);
    return this.http.post(this.URL+`/data/Search$Audit?page=${pageNO}&size=${pageSize}` , _f );
  }


}