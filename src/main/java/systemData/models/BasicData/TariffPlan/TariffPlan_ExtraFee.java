package systemData.models.BasicData.TariffPlan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TGH_TARRIF_ADD", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TariffPlan_ExtraFee {

    @EmbeddedId
    TarriffPlanExtraFeeId Id;


    @JsonProperty("valid")
    Integer VALID;
    @JsonProperty("taxEnabled")
    Integer TAX_ENABLED;
    @JsonProperty("tempEnabled")
    Integer TEMP_ENABLED;

}
