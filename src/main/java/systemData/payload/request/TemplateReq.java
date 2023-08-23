package systemData.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateReq {
    @Id
    @JsonProperty("tempCode")
    String TEMP_CODE;
    @JsonProperty("tempName")
    String TEMP_NAME;
    @JsonProperty("tempLangCode")
    String TEMP_LANG_CODE;
    @JsonProperty("tempSubject")
    String TEMP_SUBJECT;
    @JsonProperty("tempTypeCode")
    String TEMP_TYPE_CODE;
}
