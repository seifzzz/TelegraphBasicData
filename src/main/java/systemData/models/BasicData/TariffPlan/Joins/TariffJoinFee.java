package systemData.models.BasicData.TariffPlan.Joins;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TariffJoinFee{
    @JsonProperty("itemNo")
    Integer ITEM_NO;
    @JsonIgnore
    String PLAN_CODE;
    @JsonProperty("itemName")
    String ITEM_NAME;
    @JsonProperty("valid")
    Integer VALID;

}
