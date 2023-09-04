package systemData.Controller.systemDataControllers;


import javassist.NotFoundException;
import systemData.models.BasicData.Country.City;
import systemData.models.BasicData.Country.Country;
import systemData.models.BasicData.PostOffice.AlternateOffice;
import systemData.models.BasicData.PostOffice.OfficeKeyword;
import systemData.models.BasicData.PostOffice.PostOffice;
import systemData.payload.request.PostOfficeReq;
import systemData.payload.response.Response;
import systemData.services.SystemDataServices.CityService;
import systemData.services.SystemDataServices.CountryService;
import systemData.services.SystemDataServices.PostOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("postOffice")
public class PostOfficeController {
    @Autowired
    PostOfficeService postOfficeService;
    @Autowired
    CountryService countryService;
    @Autowired
    CityService cityService;

    @GetMapping("getCountries")
    public ResponseEntity<?> AllCountries() throws NotFoundException {
        List<Country> countries = countryService.getCountries();
        if(countries == null) throw new NotFoundException("No Rows");

        return  ResponseEntity.ok(new Response(200,"Successful Request",countries));
    }
    @GetMapping("getCities")
    public ResponseEntity<?> AllCities() throws NotFoundException {
        List<City> cities = cityService.getCities();
        if(cities == null) throw new NotFoundException("No Rows");

        return  ResponseEntity.ok(new Response(200,"Successful Request",cities));
    }

    @GetMapping("getCities/{code}")
    public ResponseEntity<?> AllCities(String code) throws NotFoundException {
        List<City> cities = cityService.getCityByCountryCode(code);
        if(cities == null) throw new NotFoundException("Id doesn't exist");
        return  ResponseEntity.ok(new Response(200,"Successful Request",cities));
    }



    @PostMapping("AdvancedSearchByAllConditions/{pageNumber}/{pageSize}")
    public ResponseEntity<?> AdvancedSearchByAllConditions(@RequestBody PostOfficeReq postOfficeReq,
                                                           @PathVariable int pageNumber,
                                                           @PathVariable int pageSize) throws NotFoundException {
        List<PostOffice> postOffices = postOfficeService.searchWithAllConditions(postOfficeReq,pageNumber,pageSize);
        if(postOffices == null) throw new NotFoundException("No Rows");

        HashMap<String, Object>  response = new HashMap<>();
        response.put("statusCode",200);
        response.put("message","successful request");
        response.put("total",postOfficeService.getCount());
        response.put("PageSize",pageSize);
        response.put("pageNumber",pageNumber);
        response.put("officesSizes",postOffices.size());
        response.put("body",postOffices);

        return  ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<?> UpdatePostOffice(@RequestBody PostOfficeReq postOfficeReq) throws NotFoundException {
        PostOffice postOffice = postOfficeService.UpdateBuildPostOffice(postOfficeReq);
        if(postOffice == null) throw new NotFoundException("Id doesn't exist");
        return  ResponseEntity.ok(new Response(200,"Post Office is updated",postOfficeService.SavePostOffice(postOffice)));
    }

    @PostMapping
    public ResponseEntity<?> CreatePostOffice(@RequestBody PostOfficeReq postOfficeReq) throws NotFoundException {
        PostOffice postOffice = postOfficeService.BuildPostOffice(postOfficeReq);
        if(postOffice == null) throw new NotFoundException("Id already exist");
        return  ResponseEntity.ok(new Response(200,"Post Office is created",postOfficeService.SavePostOffice(postOffice)));
    }


    @GetMapping("{code}")
    public ResponseEntity<?> onePostOfficeById(@PathVariable String code) throws NotFoundException {
        PostOffice postOffice = postOfficeService.getOffice(code);
        if(postOffice == null) throw new NotFoundException("Id doesn't exist");
        return  ResponseEntity.ok(new Response(200,"Successful Request",postOffice));
    }


    @DeleteMapping("{code}")
    public ResponseEntity<?> DeletePostOffice(@PathVariable String code) throws NotFoundException {
        boolean deleted = postOfficeService.DeletePostOffice(code);
       if(deleted) return  ResponseEntity.ok(new Response(200,"Office is deleted","successful Request"));
       throw new NotFoundException("Id doesn't exist");
      }

    @DeleteMapping("alternate/{alt}")
    public ResponseEntity<?> DeleteAlternateOffice(@PathVariable String alt) throws NotFoundException {
        boolean deleted = postOfficeService.DeleteAlt(alt);
        if(deleted) return  ResponseEntity.ok(new Response(200,"Alternate Office is deleted","successful Request"));
        throw new NotFoundException("Id doesn't exist");
    }
    @DeleteMapping("keyword/{keyCode}")
    public ResponseEntity<?> DeleteKeyword(@PathVariable Long keyCode) throws NotFoundException {
        boolean deleted = postOfficeService.DeleteKeyword(keyCode);
        if(deleted) return  ResponseEntity.ok(new Response(200,"Keyword is deleted","successful Request"));
        throw new NotFoundException("Id doesn't exist");
    }

    @GetMapping("{code}/alternate")
    public ResponseEntity<?> PostOfficeAlternate(@PathVariable String code) throws NotFoundException {
        List<AlternateOffice> alternateOffices = postOfficeService.getAlternateOfficeByOffice(code);
        if(alternateOffices == null) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"successful Request",alternateOffices));
    }

    @GetMapping("{code}/officeKeyword")
    public ResponseEntity<?> PostOfficeKeyword(@PathVariable String code) throws NotFoundException {
        List<OfficeKeyword> officeKeywords = postOfficeService.getOfficeKeywordByOffice(code);
        if(officeKeywords == null) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"successful Request",officeKeywords));
    }
    @GetMapping("country/{code}")
    public ResponseEntity<?> PostOfficeCountry(@PathVariable String code) throws NotFoundException {
        List<PostOffice> postOffices = postOfficeService.getPostOfficesByCountry(code);
        if(postOffices == null) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"successful Request",postOffices));
    }
    @GetMapping("city/{code}")
    public ResponseEntity<?> PostOfficeCity(@PathVariable String code) throws NotFoundException {
        List<PostOffice> postOffices = postOfficeService.getPostOfficesByCity(code);
        if(postOffices == null) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"successful Request",postOffices));
    }

    @PostMapping("{code}/officeKeyword/save")
    public ResponseEntity<?> SavingAllKeyword(@PathVariable String code,@RequestBody List<OfficeKeyword> officeKeywords) throws NotFoundException {
        List<OfficeKeyword> checked = postOfficeService.SaveKeyList(officeKeywords,code);
        if(checked == null) throw new NotFoundException("Id already exist");
        return  ResponseEntity.ok(new Response(200,"Keyword is created",checked));
    }
    @PostMapping("{code}/alternate/save")
    public ResponseEntity<?> SavingAllAlternate(@PathVariable String code,@RequestBody List<AlternateOffice> alternateOffices) throws NotFoundException {
        List<AlternateOffice> checked = postOfficeService.SaveAltList(alternateOffices,code);
        if(checked == null) throw new NotFoundException("Id already exist");
        return  ResponseEntity.ok(new Response(200,"alternate is created",checked));
    }

    @PatchMapping("{code}/officeKeyword/update")
    public ResponseEntity<?> UpdateAllKeyword(@PathVariable String code,@RequestBody List<OfficeKeyword> officeKeywords) throws NotFoundException {
        List<OfficeKeyword> checked = postOfficeService.updateKeyList(officeKeywords,code);
        if(checked == null) throw new NotFoundException("Id doesn't exist");
        return  ResponseEntity.ok(new Response(200,"keyword is updated",checked));
    }
    @PatchMapping("{code}/alternate/update")
    public ResponseEntity<?> UpdateAllAlternate(@PathVariable String code,@RequestBody List<AlternateOffice> alternateOffices) throws NotFoundException {
        List<AlternateOffice> checked = postOfficeService.updateAltList(alternateOffices,code);
        if(checked == null) throw new NotFoundException("Id doesn't exist");
        return  ResponseEntity.ok(new Response(200,"alternate is updated",checked));
    }


}
