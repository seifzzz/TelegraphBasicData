package systemData.repos.SystemDataRepos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import systemData.models.BasicData.Country.Joins.CountryXLanguage;
import systemData.models.BasicData.Country.Joins.CountryXVIP;
import systemData.models.BasicData.Country.Joins.Ids.CountryXLanguageId;
import systemData.models.BasicData.Country.Joins.Ids.CountryXVipId;

import java.util.List;
import java.util.Optional;

public interface CountryXLanguageRepo extends JpaRepository<CountryXLanguage, CountryXLanguageId> {
    @Query(value = "select * from TGH_COUNTRY_LANGUAGE where COUNTRY_CODE=:code",nativeQuery = true)
    @Transactional
    Optional<List<CountryXLanguage>> FindByCountryCode(String code);

    @Query(value = "select * from TGH_COUNTRY_LANGUAGE where COUNTRY_CODE=:code and LANG_CODE=:lang",nativeQuery = true)
    @Transactional
    Optional<CountryXLanguage> findById(String code,String lang);

    @Query(value = "DELETE FROM TGH_COUNTRY_LANGUAGE where COUNTRY_CODE=:code and LANG_CODE=:lang",nativeQuery = true)
    @Modifying
    @Transactional
    void deleteById(String code,String lang);

}
