package systemData.Specification;

import systemData.models.BasicData.PostOffice.PostOffice;
import systemData.payload.request.PostOfficeReq;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class PostOfficeSpec {

    public static Specification<PostOffice> byFieldAndValue(String field, String value) {
        return (root, query, criteriaBuilder) -> {
            if (field == null || value == null) {
                return null;
            }

            Path<String> fieldPath = root.get(field);
            return criteriaBuilder.equal(fieldPath, value);
        };
    }

    public static Specification<PostOffice> withAllConditions(PostOfficeReq postOfficeReq) {
        return (Root<PostOffice> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if(postOfficeReq.getOFFICE_CODE() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("OFFICE_CODE")),
                        "%" + postOfficeReq.getOFFICE_CODE().toLowerCase() + "%"
                ));
            }

            if(postOfficeReq.getOFFICE_NAME() != null){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("OFFICE_NAME")),
                        "%" + postOfficeReq.getOFFICE_NAME().toLowerCase() + "%"));
            }

            if( postOfficeReq.getOFF_TEL_NO() != null){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("OFF_TEL_NO")),
                        "%" + postOfficeReq.getOFF_TEL_NO().toLowerCase() + "%"));
            }
            if(postOfficeReq.getINTERNATIONAL() != null){
                predicates.add(criteriaBuilder.equal(root.get("INTERNATIONAL"), postOfficeReq.getINTERNATIONAL()));
            }
            if(postOfficeReq.getCOUNTRY_CODE() != null){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("COUNTRY_CODE").get("COUNTRY_CODE")),
                        "%" + postOfficeReq.getCOUNTRY_CODE().toLowerCase() + "%"));
            }
            if(postOfficeReq.getCITY_CODE() != null){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("CITY_CODE").get("CITY_CODE")),
                        "%" + postOfficeReq.getCITY_CODE().toLowerCase() + "%"));
            }
            if(postOfficeReq.getOUTGOING() != null){
                predicates.add(criteriaBuilder.equal(root.get("OUTGOING"), postOfficeReq.getOUTGOING()));
            }
            if(postOfficeReq.getINCOMING() != null){
                predicates.add( criteriaBuilder.equal(root.get("INCOMING"), postOfficeReq.getINCOMING()));
            }
            if(postOfficeReq.getARRIVAL_COUNT() != null){
                predicates.add(criteriaBuilder.equal(root.get("ARRIVAL_COUNT"), postOfficeReq.getARRIVAL_COUNT()));
            }
            if(postOfficeReq.getDEST_IND() != null){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("DEST_IND")),
                        "%" + postOfficeReq.getDEST_IND().toLowerCase() + "%"));
            }
            if (postOfficeReq.getIN_SERVICE() != null){
                predicates.add( criteriaBuilder.equal(root.get("IN_SERVICE"),postOfficeReq.getIN_SERVICE()));
            }
            if(postOfficeReq.getNOTES() != null){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("NOTES")),
                        "%" + postOfficeReq.getNOTES().toLowerCase() + "%"));;
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<PostOffice> withAnyCondition(PostOfficeReq postOfficeReq) {
        return (Root<PostOffice> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if(postOfficeReq.getOFFICE_CODE() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("OFFICE_CODE")),
                        "%" + postOfficeReq.getOFFICE_CODE().toLowerCase() + "%"
                ));
            }

            if(postOfficeReq.getOFFICE_NAME() != null){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("OFFICE_NAME")),
                        "%" + postOfficeReq.getOFFICE_NAME().toLowerCase() + "%"));
            }

            if(postOfficeReq.getOFF_TEL_NO() != null){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("OFF_TEL_NO")),
                        "%" + postOfficeReq.getOFF_TEL_NO().toLowerCase() + "%"));
            }
            if(postOfficeReq.getINTERNATIONAL() != null){
                predicates.add(criteriaBuilder.equal(root.get("INTERNATIONAL"), postOfficeReq.getINTERNATIONAL()));
            }
            if(postOfficeReq.getCOUNTRY_CODE() != null){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("COUNTRY_CODE").get("COUNTRY_CODE")),
                        "%" + postOfficeReq.getCOUNTRY_CODE().toLowerCase() + "%"));
            }
            if(postOfficeReq.getCITY_CODE() != null){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("CITY_CODE").get("CITY_CODE")),
                        "%" + postOfficeReq.getCITY_CODE().toLowerCase() + "%"));
            }
            if(postOfficeReq.getOUTGOING() != null){
                predicates.add(criteriaBuilder.equal(root.get("OUTGOING"), postOfficeReq.getOUTGOING()));
            }
            if(postOfficeReq.getINCOMING() != null){
                predicates.add( criteriaBuilder.equal(root.get("INCOMING"), postOfficeReq.getINCOMING()));
            }
            if(postOfficeReq.getARRIVAL_COUNT() != null){
                predicates.add(criteriaBuilder.equal(root.get("ARRIVAL_COUNT"), postOfficeReq.getARRIVAL_COUNT()));
            }
            if(postOfficeReq.getDEST_IND() != null){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("DEST_IND")),
                        "%" + postOfficeReq.getDEST_IND().toLowerCase() + "%"));
            }
            if (postOfficeReq.getIN_SERVICE() != null){
                predicates.add( criteriaBuilder.equal(root.get("IN_SERVICE"),postOfficeReq.getIN_SERVICE()));
            }
            if(postOfficeReq.getNOTES() != null){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("NOTES")),
                        "%" + postOfficeReq.getNOTES().toLowerCase() + "%"));;
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }


}

