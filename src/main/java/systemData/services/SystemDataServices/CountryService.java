package systemData.services.SystemDataServices;

import javassist.NotFoundException;
import lombok.var;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import systemData.Specification.CountrySpec;
import systemData.models.BasicData.Country.City;
import systemData.models.BasicData.Country.Country;
import systemData.models.BasicData.Country.Joins.CountryXLanguage;
import systemData.models.BasicData.Country.Joins.CountryXVIP;
import systemData.models.BasicData.Country.Joins.Ids.CountryXLanguageId;
import systemData.models.BasicData.Country.Joins.Ids.CountryXVipId;
import systemData.models.BasicData.PostOffice.PostOffice;
import systemData.models.BasicData.TariffPlan.CountryPlan;
import systemData.models.BasicData.TariffPlan.CountryPlanId;
import systemData.models.BasicData.TariffPlan.TariffPlan;
import systemData.models.BasicData.Template.Language;
import systemData.payload.request.*;
import systemData.payload.response.CountryPlanResponse;
import systemData.repos.SystemDataRepos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    @Autowired
    CountryRepo countryRepo;
    @Autowired
    CountryPlanRepo countryPlanRepo;
    @Autowired
    CountryXVIPRepo countryXVIPRepo;
    @Autowired
    CountryXLanguageRepo countryXLanguageRepo;

    @Autowired
    CityService cityService;
    @Autowired
    CityRepo cityRepo;

    @Autowired
    PostOfficeRepo postOfficeRepo;
    @Autowired
    LanguageRepo languageRepo;

    @Autowired
    TariffPlanService tariffPlanService;


    public Country createCountry(Country country) throws NotFoundException {
        if(getCountry(country.getCOUNTRY_CODE()) != null) throw new NotFoundException("Code already exist");
        return countryRepo.save(country);

    }
    public Country updateCountry(Country country) throws NotFoundException {
        if(getCountry(country.getCOUNTRY_CODE()) == null) throw new NotFoundException("Code doesn't exist");
        return countryRepo.save(country);

    }

    public CountryXVIP createVip(VipReq vipReq,String code) throws NotFoundException {
        Country country = getCountry(code);
        if(country == null) throw new NotFoundException("country code doesn't exist");
        City city = cityService.getCity(vipReq.getCITY_CODE());
        if(city == null) throw new NotFoundException("city code already doesn't exist");
        Optional<PostOffice> postOffice = postOfficeRepo.findById(vipReq.getOFFICE_CODE());
        if(!postOffice.isPresent()) throw new NotFoundException("post office code already doesn't exist");

        if(countryXVIPRepo.findById(vipReq.getPERSON_NO()).isPresent()) throw new NotFoundException("Vip id already exist");

        var vip = CountryXVIP.builder().VIP_ENTITY("NORMAL").CITY_CODE(vipReq.getCITY_CODE()).
                OFFICE_CODE(vipReq.getOFFICE_CODE()).OFFICIAL_ADDRESS(vipReq.getOFFICIAL_ADDRESS()).
                FEES(vipReq.getFEES()).PERSON_NAME(vipReq.getPERSON_NAME()).PERSON_TITLE(vipReq.getPERSON_TITLE()).
                id(new CountryXVipId(country,vipReq.getPERSON_NO())).build();

        return countryXVIPRepo.save(vip);

    }

    public CountryXVIP updateVip(VipReq vipReq,String code) throws NotFoundException {
        Country country = getCountry(code);
        if(country == null) throw new NotFoundException("country code doesn't exist");
        City city = cityService.getCity(vipReq.getCITY_CODE());
        if(city == null) throw new NotFoundException("city code already doesn't exist");
        Optional<PostOffice> postOffice = postOfficeRepo.findById(vipReq.getOFFICE_CODE());
        if(!postOffice.isPresent()) throw new NotFoundException("post office code already doesn't exist");

        if(!countryXVIPRepo.findById(vipReq.getPERSON_NO()).isPresent()) throw new NotFoundException("Vip id doesn't exist");

        var vip = CountryXVIP.builder().VIP_ENTITY("NORMAL").CITY_CODE(vipReq.getCITY_CODE()).
                OFFICE_CODE(vipReq.getOFFICE_CODE()).OFFICIAL_ADDRESS(vipReq.getOFFICIAL_ADDRESS()).
                FEES(vipReq.getFEES()).PERSON_NAME(vipReq.getPERSON_NAME()).PERSON_TITLE(vipReq.getPERSON_TITLE()).
                id(new CountryXVipId(country,vipReq.getPERSON_NO())).build();

        return countryXVIPRepo.save(vip);

    }



    public CountryXLanguage createLanguage(LanguageCountryReq languageCountryReq,String code) throws NotFoundException {
        Country country = getCountry(code);
        if(country == null) throw new NotFoundException("country code doesn't exist");
        Optional<Language> language = languageRepo.findById(languageCountryReq.getLangCode());
        if(!language.isPresent())  throw new NotFoundException("language code doesn't exist");

        var lang = CountryXLanguage.builder().countryXLanguageId(new CountryXLanguageId(country,language.get())).build();

        return countryXLanguageRepo.save(lang);

    }
    public City createCity(cityReq Req,String code) throws NotFoundException {
        City city = cityService.getCity(Req.getCITY_CODE());
        if(city != null) throw new NotFoundException("city code already exist");
        Country country = getCountry(code);
        if(country == null) throw new NotFoundException("country code doesn't exist");

        var saved = City.builder().CITY_NAME(Req.getCITY_NAME()).COUNTRY_CODE(country).
                CITY_CODE(Req.getCITY_CODE()).build();
        return cityRepo.save(saved);
    }

    public City updateCity(cityReq Req,String code) throws NotFoundException {
        City city = cityService.getCity(Req.getCITY_CODE());
        if(city == null) throw new NotFoundException("city code doesn't exist");
        Country country = getCountry(code);
        if(country == null) throw new NotFoundException("country code doesn't exist");

        var saved = City.builder().CITY_NAME(Req.getCITY_NAME()).COUNTRY_CODE(country).
                CITY_CODE(Req.getCITY_CODE()).build();
        return cityRepo.save(saved);
    }


    public CountryPlan createCountryPlan(CountryPlanReq countryPlanReq,String code) throws NotFoundException {
        Country country = getCountry(code);
        if(country == null) throw new NotFoundException("country code doesn't exist");
        TariffPlan tariffPlan = tariffPlanService.getPlan(countryPlanReq.getPLAN_CODE());
        if(tariffPlan == null) throw new NotFoundException("plan code doesn't exist");

        var saved = CountryPlan.builder().DEFAULT_PLAN(countryPlanReq.getDEFAULT_PLAN()).
                    ACTIVE(countryPlanReq.getACTIVE()).PLAN_NAME(countryPlanReq.getPLAN_NAME()).
                countryPlanId(new CountryPlanId(country,tariffPlan)).build();
        return countryPlanRepo.save(saved);
    }

    public Long getCount(){
        return countryRepo.count();
    }

    public List<Country> getCountries(){
        List<Country> allCountries = countryRepo.findAll();
        if(allCountries.isEmpty()) return null;
        return countryRepo.findAll();
    }

    public Country getCountry(String code){
        Optional<Country> country = countryRepo.findById(code);
        return country.orElse(null);
    }

    public List<CountryPlanResponse> getCountryPlans(String code){
        List<CountryPlanResponse> countryPlans = countryPlanRepo.countryPlanXAdmin(code);
        if(!countryPlans.isEmpty()) return countryPlans;
        return null;
    }

    public List<CountryXVIP> getCountryVIPs(String code){
        Optional<List<CountryXVIP>> countryPlans = countryXVIPRepo.FindByCountryCode(code);
        return countryPlans.orElse(null);
    }

    public List<CountryXLanguage> getCountryLanguages(String code){
        Optional<List<CountryXLanguage>> countryPlans = countryXLanguageRepo.FindByCountryCode(code);
        return countryPlans.orElse(null);
    }
    public List<Country> searchWithAllConditions(Country country,int pageNumber,int pageSize) {
        Specification<Country> spec = CountrySpec.withAllConditions(country);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Country> countries =  countryRepo.findAll(spec,pageable);
        if(countries.isEmpty()) return null;
        return countries.getContent();
    }

    public Long getCountCountries(){
        return countryRepo.count();
     }

    public boolean deleteCity(String code) throws NotFoundException {
        List<PostOffice> offices = postOfficeRepo.findByCityCode(code);
        if(offices != null ) throw new RuntimeException("offices has child from city, you must delete it");

        if(cityService.getCity(code) == null) throw new NotFoundException("Invalid Code");
        cityService.deleteCity(code);
        return true;
    }

    public boolean deleteVips(Long code) throws NotFoundException {
        if(!countryXVIPRepo.findById(code).isPresent()) throw new NotFoundException("Invalid Id");
        countryXVIPRepo.deleteById(code);
        return true;
    }

    public boolean deleteLanguage(String code,String lang) throws NotFoundException {
        Optional<CountryXLanguage> country = countryXLanguageRepo.findById(code,lang);
        if(!(country.isPresent())) throw new NotFoundException("Invalid Id");
        countryXLanguageRepo.deleteById(code,lang);
        return true;
    }
    public boolean deletePlan(String code,String lang) throws NotFoundException {
        if(!countryPlanRepo.findById(code,lang).isPresent()) throw new NotFoundException("Invalid Id");
        countryPlanRepo.deleteById(code,lang);
        return true;
    }

    public boolean DeleteCountry(String code) throws NotFoundException {

        if(getCountry(code) == null) throw new NotFoundException("Invalid Code");
        List<PostOffice> offices = postOfficeRepo.findByCountryCode(code);
        if(offices != null) throw new RuntimeException("offices has child from country, you must delete it");

        if(cityService.getCityByCountryCode(code) != null) throw new RuntimeException("Country has child from city, you must delete it");
        if(getCountryLanguages(code) != null) throw new RuntimeException("Country has child from language, you must delete it");
        if(getCountryVIPs(code) != null)  throw new RuntimeException("Country has child from vips, you must delete it");
        if (getCountryPlans(code) != null)  throw new RuntimeException("Country has child from plans, you must delete it");


       countryRepo.deleteById(code);
       return true;
    }
}
