import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GooglemapService {

  URL : string = apiURL;
  keyMapURL : string = keyMap;
  
  constructor(private http: HttpClient) {
   }

  SearchByWorkerName(EMP_Role_ID:any): Observable<any> {
   // console.log(this.URL+`/data/SearchByWorkerName?EMP_Role_ID=${EMP_Role_ID}`)
    return this.http.get(this.URL+`/data/SearchByWorkerName?EMP_Role_ID=${EMP_Role_ID}`); //H628
  }

  SearchByWO(WORK_ORDER_ID:any): Observable<any> {
   // console.log(this.URL+`/data/SearchByWO?WORK_ORDER_ID=${WORK_ORDER_ID}`)
    return this.http.get(this.URL+`/data/SearchByWO?WORK_ORDER_ID=${WORK_ORDER_ID}`); //27714
  }

  SearchByORGROLE(ORG_ROLE:any , pageNO :any, pageSize :any): Observable<any> {
    // console.log(this.URL+`/data/SearchByORGROLE?ORG_ROLE=${ORG_ROLE}&page=${pageNO}&size=${pageSize}`)
    return this.http.get(this.URL+`/data/SearchByORGROLE?ORG_ROLE=${ORG_ROLE}&page=${pageNO}&size=${pageSize}`); //MA1CA
  }
  
  GetCurrentQUOTA(): Observable<any> {
    return this.http.get(this.URL+`/data/GetCurrentQUOTA`);
  }

  GetMaxQUOTA(): Observable<any> {
    return this.http.get(this.URL+`/data/GetMaxQUOTA`);
  }

  GetAddressLocation(lat:any , lng:any): Observable<any> {
    // console.log(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=${this.keyMapURL}`);
    return this.http.get(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=${this.keyMapURL}`);
  }

  GetAddressLocationAPI(lat:any , lng:any): Observable<any> {
    // console.log(this.URL+`/Geocode/geocodeAddress?lat=${lat}&lng=${lng}&key=${this.keyMapURL}`);
    return this.http.get(this.URL+`/Geocode/geocodeAddress?lat=${lat}&lng=${lng}&key=${this.keyMapURL}`);
  }

  
}
