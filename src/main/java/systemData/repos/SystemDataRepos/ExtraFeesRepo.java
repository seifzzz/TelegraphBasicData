package systemData.repos.SystemDataRepos;

import systemData.models.BasicData.TariffPlan.ExtraFee;
import systemData.models.BasicData.TariffPlan.Joins.TariffJoinFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtraFeesRepo extends JpaRepository<ExtraFee,Integer> {

    @Query("SELECT NEW systemData.models.BasicData.TariffPlan.Joins.TariffJoinFee(e.ITEM_NO,o.Id.PLAN_CODE.PLAN_CODE,e.ITEM_NAME,o.VALID) FROM ExtraFee e\n" +
            "INNER JOIN TariffPlan_ExtraFee o ON o.Id.ITEM_NO.ITEM_NO = e.ITEM_NO  AND o.Id.PLAN_CODE.PLAN_CODE=:code")
    List<TariffJoinFee> TARIFF_JOIN_FEES(String code);

}
