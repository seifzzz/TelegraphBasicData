package systemData.services.SystemDataServices;


import systemData.models.BasicData.Country.City;
import systemData.repos.SystemDataRepos.CityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    CityRepo cityRepo;

    public List<City> getCities(){
        List<City> allCities = cityRepo.findAll();
        if(allCities.isEmpty()) return null;
        return allCities;
    }
    public List<City> getCityByCountryCode(String code){
        List<City> allCities = cityRepo.findByCountry(code);
        if(allCities.isEmpty()) return null;
        return allCities;
    }

    public void deleteCity(String code){
         cityRepo.deleteById(code);
    }


    public City getCity(String code){
        Optional<City> city = cityRepo.findById(code);
        return city.orElse(null);
    }


}
