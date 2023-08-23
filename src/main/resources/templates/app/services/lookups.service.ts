import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
declare var apiURL : any;
@Injectable({
  providedIn: 'root'
})
export class LookupsService {

  URL : string = apiURL;
  constructor(private http: HttpClient) { }

  getOUByOrgRole(): Observable<any> {
    return this.http.get(this.URL+"/data/getOUByOrgRole");
  }

  getWorkersByOrgRole(): Observable<any> {
    return this.http.get(this.URL+"/data/getWorkersByOrgRole");
  }



}




