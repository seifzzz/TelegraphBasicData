package systemData.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import systemData.models.BasicData.TariffPlan.TariffPlan_ExtraFee;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TariffFeeReq {

       String planCode;
       @JsonIgnore
       Integer itemNo;
       Integer valid;

       public TariffFeeReq(TariffPlan_ExtraFee tariffPlanExtraFee){
              this.planCode = tariffPlanExtraFee.getId().getPLAN_CODE().getPLAN_CODE();
              this.itemNo = tariffPlanExtraFee.getId().getITEM_NO().getITEM_NO();
              this.valid = tariffPlanExtraFee.getVALID();
       }

}
