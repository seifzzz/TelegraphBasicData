package systemData.Specification;

import org.springframework.data.jpa.domain.Specification;
import systemData.models.BasicData.Country.Country;
import systemData.models.BasicData.PostOffice.PostOffice;
import systemData.payload.request.PostOfficeReq;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CountrySpec {

    public static Specification<Country> withAllConditions(Country country) {
        return (Root<Country> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if(country.getCOUNTRY_CODE()!= null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("COUNTRY_CODE")),
                        "%" + country.getCOUNTRY_CODE().toLowerCase() + "%"));
            }
            if(country.getCOUNTRY_NAME()!= null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("COUNTRY_NAME")),
                        "%" + country.getCOUNTRY_NAME().toLowerCase() + "%"));
            }
            if(country.getDEST_IND()!= null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("DEST_IND")),
                        "%" + country.getDEST_IND().toLowerCase() + "%"));
            }
            if(country.getCAPITAL()!= null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("CAPITAL")),
                        "%" + country.getCAPITAL().toLowerCase() + "%"));
            }
            if(country.getENGLISH_NAME()!= null) {
                predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("ENGLISH_NAME")),
                            "%" + country.getENGLISH_NAME().toLowerCase() + "%"));
            }
            if(country.getDEST_IND_ENG()!= null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("ENGLISH_NAME")),
                        "%" + country.getDEST_IND_ENG().toLowerCase() + "%"));
            }
            if (country.getACTIVE() != null){
                predicates.add( criteriaBuilder.equal(root.get("ACTIVE"),country.getACTIVE()));
            }
            if (country.getINTERNATIONAL() != null){
                predicates.add( criteriaBuilder.equal(root.get("INTERNATIONAL"),country.getINTERNATIONAL()));
            }



            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }



}
