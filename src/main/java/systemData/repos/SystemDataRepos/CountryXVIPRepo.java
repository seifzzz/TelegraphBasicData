package systemData.repos.SystemDataRepos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import systemData.models.BasicData.Country.Joins.CountryXVIP;
import systemData.models.BasicData.Country.Joins.Ids.CountryXVipId;
import systemData.models.BasicData.TariffPlan.CountryPlan;

import java.util.List;
import java.util.Optional;

public interface CountryXVIPRepo extends JpaRepository<CountryXVIP, CountryXVipId> {
    @Query(value = "select * from TGH_COUNTRY_VIP where COUNTRY_CODE=:code",nativeQuery = true)
    Optional<List<CountryXVIP>> FindByCountryCode(String code);

    @Query(value = "select * from TGH_COUNTRY_VIP where PERSON_NO=:code",nativeQuery = true)
    Optional<CountryXVIP> findById(Long code);

    @Query(value = "DELETE FROM TGH_COUNTRY_VIP where PERSON_NO=:code",nativeQuery = true)
    @Modifying
    @Transactional
    void deleteById(Long code);

}
