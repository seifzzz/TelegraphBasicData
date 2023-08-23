package systemData.models.BasicData.TariffPlan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarriffPlanExtraFeeId implements Serializable {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PLAN_CODE")
    //@JsonProperty("PlanCode")
    @JsonIgnore

    TariffPlan PLAN_CODE;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ITEM_NO")
    @JsonProperty("itemNo")
    ExtraFee ITEM_NO;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TarriffPlanExtraFeeId id = (TarriffPlanExtraFeeId) o;

        if (!PLAN_CODE.equals(id.PLAN_CODE)) return false;
        return ITEM_NO.equals(id.ITEM_NO);
    }

    @Override
    public int hashCode() {
        int result = PLAN_CODE.hashCode();
        result = 31 * result + ITEM_NO.hashCode();
        return result;
    }
}
