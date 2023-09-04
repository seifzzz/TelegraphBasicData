package systemData.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import systemData.models.BasicData.Template.Template;

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


    public TemplateReq(Template template){
        this.TEMP_CODE = template.getTEMP_CODE();
        this.TEMP_NAME = template.getTEMP_NAME();
        this.TEMP_TYPE_CODE = template.getTEMP_TYPE_CODE().getTEMP_TYPE_CODE();
        this.TEMP_LANG_CODE = template.getTEMP_LANG_CODE().getLANG_CODE();
        this.TEMP_SUBJECT = template.getTEMP_SUBJECT();
    }



}
