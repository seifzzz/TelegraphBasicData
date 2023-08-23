package systemData.repos.SystemDataRepos;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import systemData.models.BasicData.TariffPlan.CountryPlan;
import systemData.models.BasicData.TariffPlan.CountryPlanId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import systemData.models.BasicData.TariffPlan.Joins.TariffJoinFee;
import systemData.payload.response.CountryPlanResponse;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryPlanRepo extends JpaRepository<CountryPlan, CountryPlanId> {

    @Query(value = "select * from TGH_COUNTRY_PLAN where PLAN_CODE=:code",nativeQuery = true)
    Optional<List<CountryPlan>> FindByCodePlan(String code);

    @Query(value = "select * from TGH_COUNTRY_PLAN where COUNTRY_CODE=:code",nativeQuery = true)
    Optional<List<CountryPlan>> FindByCountryCode(String code);

    @Query(value = "select * from TGH_COUNTRY_PLAN where COUNTRY_CODE=:country and PLAN_CODE=:plan",nativeQuery = true)
    Optional<CountryPlan> findById(String country,String plan);

    @Query(value = "DELETE FROM TGH_COUNTRY_PLAN where PLAN_CODE=:code",nativeQuery = true)
    @Modifying
    @Transactional
    void DeleteByPlanCode(String code);

    @Query(value = "DELETE FROM TGH_COUNTRY_PLAN where COUNTRY_CODE=:country and PLAN_CODE=:plan",nativeQuery = true)
    @Modifying
    @Transactional
    void deleteById(String country,String plan);


    @Query("SELECT NEW systemData.payload.response.CountryPlanResponse(e.countryPlanId.PLAN_CODE.PLAN_CODE,e.countryPlanId.COUNTRY_CODE.COUNTRY_CODE,o.ADMIN,e.PLAN_NAME,e.ACTIVE,e.DEFAULT_PLAN) FROM CountryPlan e " +
            "INNER JOIN TariffPlan o ON e.countryPlanId.PLAN_CODE.PLAN_CODE = o.PLAN_CODE  AND e.countryPlanId.COUNTRY_CODE.COUNTRY_CODE=:code")
    List<CountryPlanResponse> countryPlanXAdmin(String code);

}
