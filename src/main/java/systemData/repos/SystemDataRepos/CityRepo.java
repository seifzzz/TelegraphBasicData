package systemData.repos.SystemDataRepos;

import org.springframework.data.jpa.repository.Query;
import systemData.models.BasicData.Country.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepo extends JpaRepository<City, String> {
    @Query(value = "SELECT * FROM TGH_CITY where COUNTRY_CODE=:country",nativeQuery = true)
    List<City> findByCountry(String country);
}
