package systemData.models.BasicData.TariffPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import systemData.models.BasicData.Country.Country;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryPlanId implements Serializable {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COUNTRY_CODE")
    @JsonProperty("CountryCode")
    Country COUNTRY_CODE;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PLAN_CODE")
    @JsonProperty("PlanCode")
    TariffPlan PLAN_CODE;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryPlanId id = (CountryPlanId) o;

        if (!PLAN_CODE.equals(id.PLAN_CODE)) return false;
        return COUNTRY_CODE.equals(id.COUNTRY_CODE);
    }

    @Override
    public int hashCode() {
        int result = PLAN_CODE.hashCode();
        result = 31 * result + COUNTRY_CODE.hashCode();
        return result;
    }



}
