package systemData.Specification;

import systemData.models.BasicData.TariffPlan.TariffPlan;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class TariffPlanSpec {
    public static Specification<TariffPlan> byFieldAndValue(String field, String value) {
        return (root, query, criteriaBuilder) -> {
            if (field == null || value == null) {
                return null;
            }

            Path<String> fieldPath = root.get(field);
            return criteriaBuilder.equal(fieldPath, value);
        };
    }
    public static Specification<TariffPlan> withAllConditions(TariffPlan tariffPlan) {
        return (Root<TariffPlan> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();


            if(tariffPlan.getPLAN_CODE() != null ) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("PLAN_CODE")),
                        "%" + (tariffPlan.getPLAN_CODE().toLowerCase() + "%")));
            }

            if(tariffPlan.getPLAN_NAME() != null){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("PLAN_NAME")),
                        "%" +tariffPlan.getPLAN_NAME().toLowerCase() + "%"));
            }

            if(tariffPlan.getTEMPLATE_ENABLE() != null){
                predicates.add(criteriaBuilder.equal(root.get("TEMPLATE_ENABLE"),tariffPlan.getTEMPLATE_ENABLE()));
            }
            if(tariffPlan.getDELIVERY_ENABLE() != null){
                predicates.add(criteriaBuilder.equal(root.get("DELIVERY_ENABLE"), tariffPlan.getDELIVERY_ENABLE()));
            }
            if(tariffPlan.getURGENT_ENABLE() != null){
                predicates.add(criteriaBuilder.equal(root.get("URGENT_ENABLE"), tariffPlan.getURGENT_ENABLE()));
            }
            if(tariffPlan.getDECORATION_ENABLE() != null){
                predicates.add(criteriaBuilder.equal(root.get("DECORATION_ENABLE"), tariffPlan.getDECORATION_ENABLE()));
            }

            if(tariffPlan.getWORD_PRICE() != null){
                predicates.add(criteriaBuilder.equal(root.get("WORD_PRICE"), tariffPlan.getWORD_PRICE()));
            }
            if(tariffPlan.getDECORATION_PRICE() != null){
                predicates.add(criteriaBuilder.equal(root.get("DECORATION_PRICE"), tariffPlan.getDECORATION_PRICE()));
            }
            if(tariffPlan.getPO_PRICE() != null){
                predicates.add(criteriaBuilder.equal(root.get("PO_PRICE"), tariffPlan.getPO_PRICE()));
            }
            if(tariffPlan.getURGENT_PRICE() != null){
                predicates.add(criteriaBuilder.equal(root.get("URGENT_PRICE"), tariffPlan.getURGENT_PRICE()));
            }

            if(tariffPlan.getTEMPLATE_PRICE() != null){
                predicates.add(criteriaBuilder.equal(root.get("TEMPLATE_PRICE"), tariffPlan.getTEMPLATE_PRICE()));
            }
            if(tariffPlan.getDELIVERY_PRICE() != null){
                predicates.add(criteriaBuilder.equal(root.get("DELIVERY_PRICE"), tariffPlan.getDELIVERY_PRICE()));
            } if(tariffPlan.getADMIN() != null){
                predicates.add(criteriaBuilder.equal(root.get("ADMIN"), tariffPlan.getADMIN()));
            }
            if(tariffPlan.getCHAR_WORD() != null){
                predicates.add(criteriaBuilder.equal(root.get("CHAR_WORD"), tariffPlan.getCHAR_WORD()));
            }
            if(tariffPlan.getINQUIRY_PRICE() != null){
                predicates.add(criteriaBuilder.equal(root.get("INQUIRY_PRICE"), tariffPlan.getINQUIRY_PRICE()));
            }
            if(tariffPlan.getSALES_TAX()!= null){
                predicates.add(criteriaBuilder.equal(root.get("SALES_TAX"), tariffPlan.getSALES_TAX()));
            }
            if(tariffPlan.getSMS_ENABLE()!= null){
                predicates.add(criteriaBuilder.equal(root.get("SMS_ENABLE"), tariffPlan.getSMS_ENABLE()));
            }
            if(tariffPlan.getWORDS_COUNT()!= null){
                predicates.add(criteriaBuilder.equal(root.get("WORDS_COUNT"), tariffPlan.getWORDS_COUNT()));
            }
            if(tariffPlan.getSMS_PRICE()!= null){
                predicates.add(criteriaBuilder.equal(root.get("SMS_PRICE"), tariffPlan.getSMS_PRICE()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    public static Specification<TariffPlan> withAnyConditions(TariffPlan tariffPlan) {
        return (Root<TariffPlan> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if(tariffPlan.getPLAN_CODE() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("PLAN_CODE")),
                        "%" + (tariffPlan.getPLAN_CODE().toLowerCase() + "%")));
            }

            if(tariffPlan.getPLAN_NAME() != null){
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("PLAN_NAME")),
                        "%" +tariffPlan.getPLAN_NAME().toLowerCase() + "%"));
            }

            if(tariffPlan.getTEMPLATE_ENABLE() != null){
                predicates.add(criteriaBuilder.equal(root.get("TEMPLATE_ENABLE"),tariffPlan.getTEMPLATE_ENABLE()));
            }
            if(tariffPlan.getDELIVERY_ENABLE() != null){
                predicates.add(criteriaBuilder.equal(root.get("DELIVERY_ENABLE"), tariffPlan.getDELIVERY_ENABLE()));
            }
            if(tariffPlan.getURGENT_ENABLE() != null){
                predicates.add(criteriaBuilder.equal(root.get("URGENT_ENABLE"), tariffPlan.getURGENT_ENABLE()));
            }
            if(tariffPlan.getDECORATION_ENABLE() != null){
                predicates.add(criteriaBuilder.equal(root.get("DECORATION_ENABLE"), tariffPlan.getDECORATION_ENABLE()));
            }

            if(tariffPlan.getWORD_PRICE() != null){
                predicates.add(criteriaBuilder.equal(root.get("WORD_PRICE"), tariffPlan.getWORD_PRICE()));
            }
            if(tariffPlan.getDECORATION_PRICE() != null){
                predicates.add(criteriaBuilder.equal(root.get("DECORATION_PRICE"), tariffPlan.getDECORATION_PRICE()));
            }
            if(tariffPlan.getPO_PRICE() != null){
                predicates.add(criteriaBuilder.equal(root.get("PO_PRICE"), tariffPlan.getPO_PRICE()));
            }
            if(tariffPlan.getURGENT_PRICE() != null){
                predicates.add(criteriaBuilder.equal(root.get("URGENT_PRICE"), tariffPlan.getURGENT_PRICE()));
            }

            if(tariffPlan.getTEMPLATE_PRICE() != null){
                predicates.add(criteriaBuilder.equal(root.get("TEMPLATE_PRICE"), tariffPlan.getTEMPLATE_PRICE()));
            }
            if(tariffPlan.getDELIVERY_PRICE() != null){
                predicates.add(criteriaBuilder.equal(root.get("DELIVERY_PRICE"), tariffPlan.getDELIVERY_PRICE()));
            } if(tariffPlan.getADMIN() != null){
                predicates.add(criteriaBuilder.equal(root.get("ADMIN"), tariffPlan.getADMIN()));
            }
            if(tariffPlan.getCHAR_WORD() != null){
                predicates.add(criteriaBuilder.equal(root.get("CHAR_WORD"), tariffPlan.getCHAR_WORD()));
            }
            if(tariffPlan.getINQUIRY_PRICE() != null){
                predicates.add(criteriaBuilder.equal(root.get("INQUIRY_PRICE"), tariffPlan.getINQUIRY_PRICE()));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }


}

