package systemData.models.BasicData.TariffPlan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TGH_TARIFF_PLAN", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TariffPlan {
    @Id @JsonProperty("planCode")
    String PLAN_CODE;
    @JsonProperty("planName")
    String PLAN_NAME;
    @JsonProperty("templateEnable")
    Integer TEMPLATE_ENABLE;
    @JsonProperty("deliveryEnable")
    Integer DELIVERY_ENABLE;
    @JsonProperty("urgentEnable")
    Integer URGENT_ENABLE;
    @JsonProperty("decorationEnable")
    Integer DECORATION_ENABLE;
    @JsonProperty("wordPrice")
    Long WORD_PRICE;
    @JsonProperty("decorationPrice")
    Long DECORATION_PRICE;
    @JsonProperty("poPrice")
    Long PO_PRICE;
    @JsonProperty("urgentPrice")
    Long URGENT_PRICE;
    @JsonProperty("templatePrice")
    Long TEMPLATE_PRICE;
    @JsonProperty("deliveryPrice")
    Long DELIVERY_PRICE;
    @JsonProperty("admin")
    Integer ADMIN;
    @JsonProperty("charWord")
    Long CHAR_WORD;
    @JsonProperty("inquiryPrice")
    Integer INQUIRY_PRICE;
    @JsonProperty("salesTax")
    Long SALES_TAX;
    @JsonProperty("smsEnable")
    Integer SMS_ENABLE;
    @JsonProperty("smsPrice")
    Long SMS_PRICE;
    @JsonProperty("wordsCount")
    Integer WORDS_COUNT;
}
