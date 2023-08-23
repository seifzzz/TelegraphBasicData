package systemData.models.BasicData.Template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TGH_TEMPLATE", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Template {
    @Id @JsonProperty("tempCode")
    String TEMP_CODE;
    @JsonProperty("tempName")
    String TEMP_NAME;
    @JsonProperty("tempLangCode")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEMP_LANG_CODE")
    Language TEMP_LANG_CODE;
    @JsonProperty("tempSubject")
    String TEMP_SUBJECT;
    @JsonProperty("tempTypeCode")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEMP_TYPE_CODE")
    TemplateType TEMP_TYPE_CODE;
    @JsonProperty("langDirection")
    String LANG_DIRECTION;
}
