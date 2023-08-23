package systemData.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import systemData.models.BasicData.TariffPlan.CountryPlan;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryPlanResponse {
    @JsonProperty("planCode")
    String PLAN_CODE;
    @JsonProperty("countryCode")
    String COUNTRY_CODE;
    @JsonProperty("admin")
    Integer ADMIN;
    @JsonProperty("planName")
    String PLAN_NAME;
    @JsonProperty("active")
    Integer ACTIVE;
    @JsonProperty("defaultPlan")
    Integer DEFAULT_PLAN;


}
