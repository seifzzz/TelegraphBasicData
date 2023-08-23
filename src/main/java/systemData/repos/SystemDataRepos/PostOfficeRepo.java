package systemData.repos.SystemDataRepos;

import org.springframework.data.jpa.repository.Query;
import systemData.models.BasicData.Country.Joins.CountryXVIP;
import systemData.models.BasicData.PostOffice.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostOfficeRepo extends JpaRepository<PostOffice, String>, JpaSpecificationExecutor<PostOffice> {

    @Query(value = "select * from TGH_POST_OFFICE where CITY_CODE=:code",nativeQuery = true)
    List<PostOffice> findByCityCode(String code);

    @Query(value = "select * from TGH_POST_OFFICE where COUNTRY_CODE=:code",nativeQuery = true)
    List<PostOffice> findByCountryCode(String code);
}
