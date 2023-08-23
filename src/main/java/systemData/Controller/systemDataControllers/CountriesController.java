package systemData.Controller.systemDataControllers;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import systemData.models.BasicData.Country.City;
import systemData.models.BasicData.Country.Country;
import systemData.models.BasicData.Country.Joins.CountryXLanguage;
import systemData.models.BasicData.Country.Joins.CountryXVIP;
import systemData.models.BasicData.PostOffice.PostOffice;
import systemData.models.BasicData.TariffPlan.CountryPlan;
import systemData.payload.request.*;
import systemData.payload.response.CountryPlanResponse;
import systemData.payload.response.Response;
import systemData.services.SystemDataServices.CityService;
import systemData.services.SystemDataServices.CountryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@RestController
@CrossOrigin("*")
@RequestMapping("countries")
public class CountriesController {
    @Autowired
    CountryService countryService;
    @Autowired
    CityService cityService;

    @PostMapping("all/{pageNumber}/{pageSize}")
    public ResponseEntity<?> AllCountriesByPaging(@PathVariable int pageNumber,@PathVariable int pageSize,@RequestBody Country country) throws NotFoundException {
        List<Country> countries = countryService.searchWithAllConditions(country,pageNumber,pageSize);
        if(countries == null) throw new NotFoundException("No Rows");

        HashMap<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("message","Successful Request");
        response.put("total", countryService.getCount());
        response.put("totalCountries",countries.size());
        response.put("PageSize",pageSize);
        response.put("pageNumber",pageNumber);
        response.put("body",countries);

        return  ResponseEntity.ok(response);
    }
    @GetMapping("{code}")
    public ResponseEntity<?> getCountryByCode(String code) throws NotFoundException {
        Country country = countryService.getCountry(code);
        if(country == null) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"Successful Request",country));
    }

    @GetMapping("{code}/plans")
    public ResponseEntity<?> getPlansCountryByCode(String code) throws NotFoundException {
        List<CountryPlanResponse> plans = countryService.getCountryPlans(code);
        if(plans == null) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"Successful Request",plans));
    }
    @GetMapping("{code}/vips")
    public ResponseEntity<?> getVipsCountryByCode(String code) throws NotFoundException {
        List<CountryXVIP> vips = countryService.getCountryVIPs(code);
        if(vips == null) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"Successful Request",vips));
    }
    @GetMapping("{code}/languages")
    public ResponseEntity<?> getLanguagesCountryByCode(String code) throws NotFoundException {
        List<CountryXLanguage> languages = countryService.getCountryLanguages(code);
        if(languages == null) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"Successful Request",languages));
    }
    @GetMapping("{code}/cities")
    public ResponseEntity<?> getCitiesCountryByCode(String code) throws NotFoundException {
        List<City> cities = cityService.getCityByCountryCode(code);
        if(cities == null) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"Successful Request",cities));
    }

   @DeleteMapping("{code}")
   public ResponseEntity<?> deleteCountry(@PathVariable String code) throws NotFoundException {
       boolean deleted = countryService.DeleteCountry(code);
       if(deleted) return  ResponseEntity.ok(new Response(200,"Country is deleted","Successful Request"));
       throw new NotFoundException("Id doesn't exist");
   }
    @DeleteMapping("{code}/plan/{id}")
    public ResponseEntity<?> deletePlan(@PathVariable String code,@PathVariable String id) throws NotFoundException {
        boolean deleted = countryService.deletePlan(code,id);
        if(deleted) return  ResponseEntity.ok(new Response(200,"Plan is deleted","Successful Request"));
        throw new NotFoundException("Id doesn't exist");
    }

    @DeleteMapping("{code}/language/{id}")
    public ResponseEntity<?> deleteLanguage(@PathVariable String code,@PathVariable String id) throws NotFoundException {
        boolean deleted = countryService.deleteLanguage(code,id);
        if(deleted) return  ResponseEntity.ok(new Response(200,"Language is deleted","Successful Request"));
        throw new NotFoundException("Id doesn't exist");
    }
    @DeleteMapping("vip/{code}")
    public ResponseEntity<?> deleteVip(@PathVariable Long code) throws NotFoundException {
        boolean deleted = countryService.deleteVips(code);
        if(deleted) return  ResponseEntity.ok(new Response(200,"Vip is deleted","Successful Request"));
        throw new NotFoundException("Id doesn't exist");
    }
    @DeleteMapping("cities/{code}")
    public ResponseEntity<?> deleteCity(@PathVariable String code) throws NotFoundException {
        boolean deleted = countryService.deleteCity(code);
        if(deleted) return  ResponseEntity.ok(new Response(200,"City is deleted","Successful Request"));
        throw new NotFoundException("Id doesn't exist");
    }
    @PostMapping("save")
    public ResponseEntity<?> createCountry(@RequestBody Country country) throws NotFoundException {
        Country saved = countryService.createCountry(country);
        return ResponseEntity.ok(new Response(200,"Country is created",country));
    }
    @PostMapping("{code}/save/plan")
    public ResponseEntity<?> createPlan(@PathVariable String code,@RequestBody CountryPlanReq req) throws NotFoundException {
        CountryPlan countryPlan = countryService.createCountryPlan(req,code);
       return ResponseEntity.ok(new Response(200,"Country plan is created",countryPlan));
    }
    @PostMapping("{code}/save/city")
    public ResponseEntity<?> createCity(@PathVariable String code,@RequestBody cityReq req) throws NotFoundException {
        City city = countryService.createCity(req,code);
        return ResponseEntity.ok(new Response(200,"City is created",city));
    }

    @PostMapping("{code}/save/vip")
    public ResponseEntity<?> createVip(@PathVariable String code,@RequestBody VipReq req) throws NotFoundException {
        CountryXVIP countryXVIP = countryService.createVip(req,code);
        return ResponseEntity.ok(new Response(200,"vip is created",countryXVIP));
    }
    @PostMapping("{code}/save/language")
    public ResponseEntity<?> createLanguage(@PathVariable String code,@RequestBody LanguageCountryReq req) throws NotFoundException {
        CountryXLanguage countryXLanguage = countryService.createLanguage(req,code);
        return ResponseEntity.ok(new Response(200,"Language is created",countryXLanguage));
    }

    @PatchMapping("update")
    public ResponseEntity<?> updateCountry(@RequestBody Country country) throws NotFoundException {
        Country saved = countryService.updateCountry(country);
        return ResponseEntity.ok(new Response(200,"Country is updated",saved));
    }
    @PatchMapping("{code}/update/plan")
    public ResponseEntity<?> updatePlan(@PathVariable String code,@RequestBody CountryPlanReq req) throws NotFoundException {
        CountryPlan countryPlan = countryService.createCountryPlan(req,code);
        return ResponseEntity.ok(new Response(200,"Country plan is updated",countryPlan));
    }
    @PatchMapping("{code}/update/vip")
    public ResponseEntity<?> updateVip(@PathVariable String code,@RequestBody VipReq req) throws NotFoundException {
        CountryXVIP countryXVIP = countryService.updateVip(req,code);
        return ResponseEntity.ok(new Response(200,"vips is updated",countryXVIP));
    }

    @PatchMapping("{code}/update/city")
    public ResponseEntity<?> updateCity(@PathVariable String code,@RequestBody cityReq req) throws NotFoundException {
        City city = countryService.updateCity(req,code);
        return ResponseEntity.ok(new Response(200,"City is updated",city));
    }
}
