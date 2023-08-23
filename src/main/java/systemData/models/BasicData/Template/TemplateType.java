package systemData.models.BasicData.Template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TGH_TEMPLATE_TYPE", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateType {
    @Id @JsonProperty("tempTypeCode")
    String TEMP_TYPE_CODE;
    @JsonProperty("tempTypeName")
    String TEMP_TYPE_NAME;
}
