package systemData.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TariffFeeReq {

       String planCode;
       Integer itemNo;
       Integer valid;
}
