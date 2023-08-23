package systemData.models.BasicData.TariffPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TGH_ADD_VALUE", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtraFee {

    @Id @JsonProperty("itemNo")
    Integer ITEM_NO;
    @JsonProperty("itemName")
    String ITEM_NAME;
    @JsonProperty("addPrice")
    Long ADD_PRICE;
}
