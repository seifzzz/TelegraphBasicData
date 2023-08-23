package systemData.repos.SystemDataRepos;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import systemData.models.BasicData.PostOffice.AlternateOffice;
import systemData.models.BasicData.PostOffice.OfficeKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfficeKeywordRepo extends JpaRepository<OfficeKeyword, Long> {

    @Query(value = "select * from TGH_OFFICE_KEYWORD where OFFICE_CODE=:code",nativeQuery = true)
    @Transactional
    List<OfficeKeyword> FindByOfficeCode(String code);

    @Query(value = "DELETE FROM TGH_OFFICE_KEYWORD where OFFICE_CODE=:code",nativeQuery = true)
    @Modifying
    @Transactional
    void DeleteByOfficeCode(String code);

    @Query(value = "DELETE FROM TGH_OFFICE_KEYWORD where OFFICE_CODE=:code and ALT_OFFICE_CODE=:code1",nativeQuery = true)
    @Modifying
    @Transactional
    void DeleteByOfficeCodeAndAltOffice(String code,Long code1);



}
