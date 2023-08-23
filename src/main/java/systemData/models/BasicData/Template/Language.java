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
@Table(name = "TGH_LANGUAGE", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Language {
    @Id @JsonProperty("langCode")
    private String LANG_CODE;
    @JsonProperty("langName")
    private String LANG_NAME;
    @JsonProperty("langDirection")
    private String LANG_DIRECTION;
}
