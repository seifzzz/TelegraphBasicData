package systemData.repos.SystemDataRepos;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import systemData.models.BasicData.Country.Joins.CountryXLanguage;
import systemData.models.BasicData.PostOffice.AlternateOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlternateOfficeRepo extends JpaRepository<AlternateOffice, String> {

    @Query(value = "select * from TGH_POST_OFFICE_ALT where OFFICE_CODE=:code",nativeQuery = true)
    @Transactional
    List<AlternateOffice> FindByOfficeCode(String code);

    @Query(value = "DELETE FROM TGH_POST_OFFICE_ALT where OFFICE_CODE=:code and KEYWORD_CODE=:code1",nativeQuery = true)
    @Modifying
    @Transactional
    void DeleteByOfficeCodeAndKeyWordOffice(String code,String code1);

}
