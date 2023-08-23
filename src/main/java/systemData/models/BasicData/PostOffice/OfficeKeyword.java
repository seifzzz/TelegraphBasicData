package systemData.models.BasicData.PostOffice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
@Entity
@Table(name = "TGH_OFFICE_KEYWORD", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficeKeyword {
    @Id
    @JsonProperty("keywordCode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long KEYWORD_CODE;
    @JsonProperty("keywordName")
    String  KEYWORD_NAME;
    @JsonProperty("numberRange")
    Integer NUMBER_RANGE;
    @JsonProperty("rangeStart")
    Integer  RANGE_START;
    @JsonProperty("rangeEnd")
    Integer RANGE_END;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OFFICE_CODE")
    @JsonIgnore
    PostOffice OFFICE_CODE;
    @JsonProperty("insertDate")
    Timestamp INSERT_DATE;
    @JsonProperty("numberRangeType")
    Integer  NUMBER_RANGE_TYPE;
    @JsonProperty("keywordNameDic")
    String KEYWORD_NAME_DIC;
}
