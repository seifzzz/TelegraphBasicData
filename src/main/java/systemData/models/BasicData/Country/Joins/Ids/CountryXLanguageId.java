package systemData.models.BasicData.Country.Joins.Ids;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import systemData.models.BasicData.Country.Country;
import systemData.models.BasicData.Template.Language;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryXLanguageId implements Serializable {

    @JsonProperty("CountryCode")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COUNTRY_CODE")
    @JsonIgnore
    Country COUNTRY_CODE;
    @JsonProperty("LangCode")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LANG_CODE")
    Language LANG_CODE;

    ;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
