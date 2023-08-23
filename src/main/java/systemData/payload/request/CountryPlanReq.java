package systemData.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import systemData.models.BasicData.Country.Country;
import systemData.models.BasicData.TariffPlan.TariffPlan;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryPlanReq {
    @JsonProperty("PlanCode")
    String PLAN_CODE;
    @JsonProperty("PlanName")
    String PLAN_NAME;
    @JsonProperty("active")
    Integer ACTIVE;
    @JsonProperty("defaultPlan")
    Integer DEFAULT_PLAN;
}
