import { Component, OnInit, ViewChild } from '@angular/core';
import { LookupsService } from 'src/app/services/lookups.service';
import {MessageService} from 'primeng/api';
import { formatDate } from '@angular/common';
import { Router } from '@angular/router';
import { GooglemapService } from 'src/app/services/googlemap.service';
import { SearchComponent } from '../search/search.component';
//import { MarkerClusterer } from '@googlemaps/markerclusterer';
declare var google: any;
declare var MarkerClusterer: any;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'],
  providers: [MessageService],

})

export class MapComponent implements OnInit {
  @ViewChild('mySearch') mySearch : SearchComponent | undefined  ;
  first:any = 0;
  last:any = 0;
  totalRecords:any = 0;
  totalRow:any = 0;
  
  options: any;
  [x:string]:any
  overlays!: any[];
  dialogVisible!: boolean;
  markerTitle!: string;
  selectedPosition: any;
  infoWindow: any;
  draggable!: boolean;
  hiddenMap :boolean = false;
  hiddentable :boolean = false;
  showMessage :boolean = false;
  counterlocations = 0;
  allFormateAddress! :any[];
  locations = [{  lat: 0 , lng: 0 }]
  isPaginator :boolean = true ;

//  locations = [
//   { lat: 40.813078, lng: -73.046388},
//   { lat: 40.813223, lng: -73.049288},
// ];
  



getLocations($event: any) {
  // alert("locationss")
  this.isPaginator = true ;
  this.counterlocations++ ;
  if(this.counterlocations == 1){
    this.loading = true;
   
  }else if(this.counterlocations == 2){
    this.loading = false;
    this.counterlocations = 0;
  }
  this.showMessageNotActive = ""
  this.showMessage = false;
  let z = localStorage.getItem("searchTypes")
  if(z!== null)
  this.searchTypes = JSON.parse(z);
//  console.log(this.searchTypes)
 if(this.searchTypes?.value == 1){
  this.labelMessage = "FME name"
 }else if(this.searchTypes?.value == 2){
  this.labelMessage = "WO"
 }else if(this.searchTypes?.value == 3){
  this.labelMessage = " "

 }
  this.counterDrawMap = 0
  // console.log($event);
  // this.loading = false;

  this.dataDetails = $event;
//   this.dataDetails = [{
//     "worker_ID": "181199",
//     "last_MODIFIED_DATE": "2022-09-12 12:25:36.0",
//     "emp_ROLE_ID": "181199",
//     "worker_NAME": "181199",
//     "org_ROLE": "Moqattam",
//     "imei": "352948094479015",
//     "worker_STATUS": "InActive Location",
//     "longitude": "31.2361288",
//     "latitude": "29.9725259",
//     "id": "41"
// },
// {
//   "worker_ID": "18119955",
//   "last_MODIFIED_DATE": "2022-09-12 12:25:36.0",
//   "emp_ROLE_ID": "181199",
//   "worker_NAME": "10-20",
//   "org_ROLE": "Moqattam",
//   "imei": "352948094479015",
//   "worker_STATUS": "InActive Location",
//   "longitude": "70",
//   "latitude": "50",
//   "id": "43"
// }]


  if($event == "reset"){
    this.locations = [];
  //  this.drawMaps();
    this.hiddenMap = true;
    window.history.go();
    // this.router.navigate(["/tracking"]);
  }else{
  this.locations = [];
  this.hiddentable = true;

  console.log(this.searchTypes)
  if(this.searchTypes?.value == 3 ){
    // alert("333")
    this.isPaginator = false ;
    this.totalRecords = localStorage.getItem("searchTotalItems");
    this.first= 1;
    this.last = this.dataDetails.length;
    this.totalRow = this.dataDetails.length;
  }
      
  // alert("jj")
  for(let x=0 ; x < this.dataDetails.length ; x++){
    if(this.dataDetails[x].latitude !== null && this.dataDetails[x].longitude !== null  ){
      this.counterDrawMap ++ ;
      // alert(this.counterDrawMap);
    }
     this.locations.push({ lat: parseFloat(this.dataDetails[x].latitude) , lng: parseFloat(this.dataDetails[x].longitude) }) 
  }
  // console.log(this.locations)
  // alert(this.dataDetails.length )
  if(this.dataDetails.length  !== 0){
    if(this.counterDrawMap > 0){
      //alert("jj")
      this.addressF = " ";
      if(this.searchTypes.value == 1 || this.searchTypes.value == 2 ){
        this.dataApi.GetAddressLocation(this.dataDetails[0].latitude , this.dataDetails[0].longitude).subscribe(Response=> { 
          // console.log("GetAddressLocation")
          // console.log(Response)
          if(Response.results.length > 0){
              this.addressF = Response.results[0].formatted_address;
          }
          // this.allFormateAddress.push(Response.results[0].formatted_address);
          setTimeout(() => {
            this.drawMaps();
          }, 0);
        },(err:any) => {
          // console.error(err.error);
          // console.error(err.error.statusCode);
          this.messageService.add({severity:'warn', summary: 'Warning', detail: `Something went wrong  while getting address location`});

          setTimeout(() => {
            this.drawMaps();
          }, 0);
        });
      }else if(this.searchTypes.value == 3 ){
        setTimeout(() => {
          this.drawMaps();
        }, 0);
      }
      // this.drawMaps();
      this.showMessage = false;

    }else{
      this.showMessageNotActive = this.labelMessage + " InActive For Long Time"
      this.showMessage = true;
      this.hiddenMap = true;
    }
  }else{
    this.hiddenMap = true;
    this.hiddentable = true;
  }
}

}
  constructor(private messageService: MessageService , private router: Router , private dataApi: GooglemapService) {}
  ngOnInit() {
    this.loading = false;
  }
 
  drawMaps(){
      // alert("drawMaps");
    this.loading = false;
    this.allFormateAddress = [];
    // console.log("drawMaps");
    this.counterlocations = 0;
    // console.log(google.maps.Map);
    this.map = new google.maps.Map(document.getElementById("map") as HTMLElement, {
    center: { lat: 26.820553 , lng: 30.802498 },
      // center: {  lat: 40.813078, lng: -73.046388 },
      zoom: 3, //3 
    });
    // console.log( "map" )
    // console.log( this.map )

    this.infoWindow = new google.maps.InfoWindow({
      content: "",
      disableAutoPan: true,
    });


  // Create an array of alphabetical characters used to label the markers.
  // const labels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  // Add some markers to the map.
  // console.log(this.locations)
  // console.log(this.dataDetails)

  this.markers = this.locations.map((position, i) => {

    if(this.dataDetails[i].worker_STATUS == "InActive Location"){
      this.color = "red";
     }
     if(this.dataDetails[i].worker_STATUS == "Active Location"){
      this.color = "green";
    }

    var label = this.addressF
    var labelDetails = 
    '<h6 style="font-size: 13px;">Worker Name : ' + this.dataDetails[i].worker_NAME +'</h6>'
    +'<h6 style="font-size: 13px;">Last Modified Date : ' + formatDate(this.dataDetails[i].last_MODIFIED_DATE, 'medium', 'en_US')+'</h6>'
    +'<h6 style="font-size: 13px;">Emp ROLE ID : ' + this.dataDetails[i].emp_ROLE_ID +'</h6>'
    +'<h6 style="font-size: 13px;">Org ROLE : ' + this.dataDetails[i].org_ROLE+'</h6<br>' 
    +'<h6 style="font-size: 13px;">Worker Status : <span style="color:'+this.color+'"> ' + this.dataDetails[i].worker_STATUS+'</span></h6>'

    // +'<h6 style="font-size: 13px;">Locations : (' + this.locations[i].lat+" , "+this.locations[i].lng +')</h6>'
     //longitude: 31.2361632, latitude:


    var marker = new google.maps.Marker({
      position,
      label : {
        text: label,
        color: "#a33", //4682B4
        fontSize: "13px",
        fontWeight: "bold",
        marginTop: "70px",
        className: 'marker-label',
      } ,
    //  icon: "http://maps.google.com/mapfiles/ms/micons/"+ this.color+".png",
    // icon: "assets/images/marker-"+ this.color+"1.png",
    // icon: "assets/images/workmarker-"+ this.color+".png",
     icon: "assets/images/usermarker-"+ this.color+".png",
    });


    // open info window Automatic
    // this.infoWindow.setContent(labelDetails);
    // this.infoWindow.open(this.map, marker);

    // markers can only be keyboard focusable when they have click listeners
    // open info window when marker is clicked
   

    marker.addListener("click", () => {
    //  console.log("clickkkTestAddress")
      // this.dataApi.GetAddressLocationAPI(this.dataDetails[i].latitude , this.dataDetails[i].longitude).subscribe(Response=> { 
      //   console.log(Response);
      // },(error:any) => {
      //   this.messageService.add({severity:'warn', summary: 'Warning', detail: `Something went wrong  while getting address location API`});
      // });
      
      this.infoWindow.setContent(labelDetails);
      this.infoWindow.open(this.map, marker);

      // this.infoWindow.setContent(labelDetails+'<h6 style="font-size: 13px;">Address : ' + this.allFormateAddress[0]+'</h6<br>' );
      // this.infoWindow.open(this.map, marker);
      // console.log(marker);
      // console.log(this.map);
      //alert(this.dataDetails[i].latitude)
      

    });
    return marker;
  });
  
  //  console.log("markers");
  // console.log("map");
  // console.log(this.map);

  

  // var markerBounds = new google.maps.LatLngBounds();
  // var randomPoint, i;
  // for (i = 0; i < 10; i++) {
  //   // Generate 10 random points within North East USA
  //   randomPoint = new google.maps.LatLng( 39.00 + (Math.random() - 0.5) * 20, -77.00 + (Math.random() - 0.5) * 20);
  //   // Draw a marker for each random point
  //   new google.maps.Marker({
  //     position: randomPoint, 
  //     map: this.map
  //   });
  //   // Extend markerBounds with each random point.
  //   markerBounds.extend(randomPoint);
  // }
  // // Finally we can call the Map.fitBounds() method to set the map to fit
  // // our markerBounds
  // console.log("randomPoint");
  // console.log(randomPoint);
  // console.log("markerBounds");
  // console.log(markerBounds);
  // this.map.fitBounds(markerBounds);

  var bound = new google.maps.LatLngBounds();
  // alert(bound);
 // console.log(bound)
  for (let i = 0; i < this.locations.length; i++) {
   // this.bounds.extend(this.locations[i]) // your marker position, must be a LatLng instance
 //  alert(this.locations[i].lat )
   if((this.locations[i].lat).toString()  !== "NaN" ){
    //console.log(new google.maps.LatLng(this.locations[i].lat, this.locations[i].lng) )
    bound.extend(new google.maps.LatLng(this.locations[i].lat, this.locations[i].lng) );
   }
  }
 // console.log("fitBounds")
 // console.log(this.map)
 // console.log(bound)
  this.map.fitBounds(bound); // map should be your map class
  //console.log(this.map.fitBounds(bound))

  // console.log(bound.getCenter(this.locations));
  // this.centerLocation = bound.getCenter(this.locations);
  // console.log("centerLocation")
  // console.log(this.centerLocation)
  // this.map.setCenter(bound.getCenter(this.locations));
  // console.log(this.map.setCenter(bound.getCenter(this.locations)));
  // console.log(this.centerLocation as google.maps.LatLng)

  // use default algorithm and renderer
  // Add a marker clusterer to manage the markers.
   const markerCluster =  new MarkerClusterer(this.map, this.markers);

  //new MarkerClusterer({ markers, map });
 //  this.markerCluster = this.markerClusterer.MarkerClusterer({map, markers}); //map, markers
  // const markerCluster = new MarkerClusterer({ map, markers });
 //  new MarkerClusterer(map, markers)

  }
  







  

  selectedRow(dataRow:any ,index:any){
  // alert(index)
  // console.log(index)
  // console.log(dataRow);
  // console.log(this.markers)
   var pt = this.markers[index].getPosition();
    //  console.log(pt);
    //  console.log(this.markers[index]);
  var  newpt = new google.maps.LatLng(pt.lat() + .02, pt.lng());
    //  console.log(newpt);
     this.map.panTo(newpt);
    if (this.infoWindow) {
      this.infoWindow.close();
    }
	// console.log(this.map)


  var labelDetails = 
  '<h6 style="font-size: 13px;">Worker Name : ' + dataRow?.worker_NAME +'</h6>'
    +'<h6 style="font-size: 13px;">Last Modified Date : ' + formatDate(dataRow?.last_MODIFIED_DATE, 'medium', 'en_US')+'</h6>'
    +'<h6 style="font-size: 13px;">Emp ROLE ID : ' + dataRow?.emp_ROLE_ID +'</h6>'
    +'<h6 style="font-size: 13px;">Org ROLE : ' + dataRow?.org_ROLE +'</h6<br>' 
    +'<h6 style="font-size: 13px;">Worker Status : <span style="color:'+this.color+'"> '  + dataRow?.worker_STATUS+' </span></h6<br>';
    // +'<h6 style="font-size: 13px;">Address : ' + this.allFormateAddress[index]+'</h6<br>' 

   this.infoWindow.setContent(labelDetails);
    this.infoWindow.setPosition(this.markers[index].getPosition());
    this.infoWindow.open(this.map, this.markers[index]);

    
    this.map.setZoom(100);
   // console.log(this.markers[index])
   // console.log(this.markers[index].getPosition())
   // console.log(this.markers[index].getPosition() as google.maps.LatLng)
    this.map.setCenter(this.markers[index].getPosition() as google.maps.LatLng);
  // console.log(this.map);


}



paginate(event:any) {
  // console.log(event);
  this.first= event.first + 1;
  this.last = (event.page +1) *  event.rows;
  if(event.page ==  event.pageCount -1){
    this.last = this.totalRecords ;
  }

  let zz = localStorage.getItem("searchTypesValue")
  if(zz!== null)
  this.searchTypesValue = JSON.parse(zz);
console.log(this.searchTypesValue)
this.loading = true;
this.counterDrawMappagenation = 0 ;
this.locations = [];
  this.dataApi.SearchByORGROLE(this.searchTypesValue.value , event.page , event.rows).subscribe(Response=> { //MA1CA
    console.log(Response)
     if(Response.statusCode == 200 ){ 
      this.loading = false;
       this.dataDetails =  Response.body;

       for(let x=0 ; x < this.dataDetails.length ; x++){
        if(this.dataDetails[x].latitude !== null && this.dataDetails[x].longitude !== null  ){
          this.counterDrawMappagenation ++ ;
        }
         this.locations.push({ lat: parseFloat(this.dataDetails[x].latitude) , lng: parseFloat(this.dataDetails[x].longitude) }) 
      }
      // alert(this.counterDrawMappagenation);
      if(this.counterDrawMappagenation > 0){
        this.mySearch?.GetCurrentQUOTA();
      setTimeout(() => {
        this.drawMaps();
      }, 0);
    }else if(this.counterDrawMappagenation ==  0){
     // alert ("jj")
      this.locations = [];
      this.counterDrawMappagenation =  -1 ;

      // this.markers = this.locations.map((position, i) => {
      //   return this.marker ;
      // });
    }


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
 






// this.mapsAPILoader.load().then(() => {
//   this.bounds = new google.maps.LatLngBounds(
//     new google.maps.LatLng(51.130739, -0.868052), // SW
//     new google.maps.LatLng(51.891257, 0.559417) // NE
//   );
//   console.log(this.bounds);
// });