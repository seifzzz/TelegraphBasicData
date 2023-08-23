package systemData.repos.SystemDataRepos;

import systemData.models.BasicData.TariffPlan.TariffPlan_ExtraFee;
import systemData.models.BasicData.TariffPlan.TarriffPlanExtraFeeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TariffPlan_ExtraFeeRepo extends JpaRepository<TariffPlan_ExtraFee, TarriffPlanExtraFeeId> {
     @Query(value = "select * from TGH_TARRIF_ADD where PLAN_CODE=:code",nativeQuery = true)
     Optional<List<TariffPlan_ExtraFee>> FindByCodePlan(String code);

     @Query(value = "select * from TGH_TARRIF_ADD where ITEM_NO=:code",nativeQuery = true)
     List<TariffPlan_ExtraFee> FindByItemNo(Integer code);


     @Query(value = "update TGH_TARRIF_ADD set VALID=:valid where PLAN_CODE=:code and ITEM_NO=:item",nativeQuery = true)
     @Modifying
     @Transactional
     void UpdateValid(String code,Integer item,Integer valid);

     @Query(value = "DELETE FROM TGH_TARRIF_ADD where PLAN_CODE=:code",nativeQuery = true)
     @Modifying
     @Transactional
     void DeleteByPlanCode(String code);

     @Query(value = "DELETE FROM TGH_TARRIF_ADD where ITEM_NO=:code",nativeQuery = true)
     @Modifying
     @Transactional
     void DeleteByItemNo(Integer code);


}
