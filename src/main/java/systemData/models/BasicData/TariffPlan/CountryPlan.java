package systemData.models.BasicData.TariffPlan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TGH_COUNTRY_PLAN", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryPlan {

    @EmbeddedId
    @JsonIgnore
    CountryPlanId countryPlanId;
    @JsonProperty("PlanName")
    String PLAN_NAME;
    @JsonProperty("active")
    Integer ACTIVE;
    @JsonProperty("defaultPlan")
    Integer DEFAULT_PLAN;
}
