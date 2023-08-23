package systemData.services.SystemDataServices;

import javassist.NotFoundException;
import javassist.compiler.ast.Keyword;
import lombok.var;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import systemData.Specification.PostOfficeSpec;
import systemData.models.BasicData.Country.City;
import systemData.models.BasicData.Country.Country;
import systemData.models.BasicData.PostOffice.AlternateOffice;
import systemData.models.BasicData.PostOffice.OfficeKeyword;
import systemData.models.BasicData.PostOffice.PostOffice;
import systemData.payload.request.PostOfficeReq;
import systemData.repos.SystemDataRepos.AlternateOfficeRepo;
import systemData.repos.SystemDataRepos.OfficeKeywordRepo;
import systemData.repos.SystemDataRepos.PostOfficeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostOfficeService {
    @Autowired
    PostOfficeRepo postOfficeRepo;
    @Autowired
    CityService cityService;
    @Autowired
    CountryService countryService;

    @Autowired
    AlternateOfficeRepo alternateOfficeRepo;

    @Autowired
    OfficeKeywordRepo officeKeywordRepo;


    public List<PostOffice> getAllPostOffices(){
        List<PostOffice> allOffices = postOfficeRepo.findAll();
        if(allOffices.isEmpty()) return null;
        return allOffices;
    }

    public Long getCount(){
        return postOfficeRepo.count();
    }

    public PostOffice SavePostOffice(PostOffice postOffice){return postOfficeRepo.save(postOffice);}



    public PostOffice BuildPostOffice(PostOfficeReq postOfficeReq){
        Country country = countryService.getCountry(postOfficeReq.getCOUNTRY_CODE());
        if(country == null) throw new RuntimeException("Invalid country code");
        City city = cityService.getCity(postOfficeReq.getCITY_CODE());
        if( city == null) throw new RuntimeException("Invalid city code");

        if(getOffice(postOfficeReq.getOFFICE_CODE()) != null) throw new RuntimeException("code already exist");

        return PostOffice.builder().OFFICE_CODE(postOfficeReq.getOFFICE_CODE()).
                IN_SERVICE(postOfficeReq.getIN_SERVICE()).DEST_IND(postOfficeReq.getDEST_IND()).
                INTERNATIONAL(postOfficeReq.getINTERNATIONAL()).OFF_TEL_NO(postOfficeReq.getOFF_TEL_NO()).
                OUTGOING(postOfficeReq.getOUTGOING()).INCOMING(postOfficeReq.getINCOMING()).
                OFF_ADDRESS(postOfficeReq.getOFF_ADDRESS()).ARRIVAL_COUNT(postOfficeReq.getARRIVAL_COUNT()).
                NOTES(postOfficeReq.getNOTES()).OFFICE_NAME(postOfficeReq.getOFFICE_NAME()).CITY_CODE(city)
                .COUNTRY_CODE(country).build();
    }


    public PostOffice UpdateBuildPostOffice(PostOfficeReq postOfficeReq){
        Country country = countryService.getCountry(postOfficeReq.getCOUNTRY_CODE());
        if(country == null) return null;
        City city = cityService.getCity(postOfficeReq.getCITY_CODE());
        if( city == null) return null;

        if(getOffice(postOfficeReq.getOFFICE_CODE()) == null) throw new RuntimeException("code isn't exist to update");

        return PostOffice.builder().OFFICE_CODE(postOfficeReq.getOFFICE_CODE()).
                IN_SERVICE(postOfficeReq.getIN_SERVICE()).DEST_IND(postOfficeReq.getDEST_IND()).
                INTERNATIONAL(postOfficeReq.getINTERNATIONAL()).OFF_TEL_NO(postOfficeReq.getOFF_TEL_NO()).
                OUTGOING(postOfficeReq.getOUTGOING()).INCOMING(postOfficeReq.getINCOMING()).
                OFF_ADDRESS(postOfficeReq.getOFF_ADDRESS()).ARRIVAL_COUNT(postOfficeReq.getARRIVAL_COUNT()).
                NOTES(postOfficeReq.getNOTES()).OFFICE_NAME(postOfficeReq.getOFFICE_NAME()).CITY_CODE(city)
                .COUNTRY_CODE(country).build();
    }
    public List<PostOffice> getPostOfficesPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return postOfficeRepo.findAll(pageable).getContent();
    }

    public List<PostOffice> searchWithAllConditions(PostOfficeReq postOfficeReq,int pageNumber, int pageSize) {
        Specification<PostOffice> spec = PostOfficeSpec.withAllConditions(postOfficeReq);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PostOffice> postOffices =  postOfficeRepo.findAll(spec,pageable);
        if(postOffices.isEmpty()) return null;

        return postOffices.getContent();
    }

    public List<PostOffice> searchWithAnyConditions(PostOfficeReq postOfficeReq) {
        Specification<PostOffice> spec = PostOfficeSpec.withAnyCondition(postOfficeReq);
        List<PostOffice> postOffices =  postOfficeRepo.findAll(spec);
        if(postOffices.isEmpty()) return null;
        return postOffices;
    }


    public List<PostOffice> getPostOfficesByAttribute(String attribute,String value){

        Specification<PostOffice> specification = PostOfficeSpec.byFieldAndValue(attribute,value);

        List<PostOffice> allOffices = postOfficeRepo.findAll(specification);
        if(allOffices.isEmpty()) return null;
        return allOffices;
    }

    public PostOffice getOffice(String code){
        Optional<PostOffice> postOffice = postOfficeRepo.findById(code);
        return postOffice.orElse(null);
    }

    public List<AlternateOffice> getAlternateOfficeByOffice (String code){
        List<AlternateOffice>alternateOffices = alternateOfficeRepo.FindByOfficeCode(code);
        if(!alternateOffices.isEmpty()) return alternateOffices;
        return null;
    }

    public List<OfficeKeyword> getOfficeKeywordByOffice (String code){
        List<OfficeKeyword>officeKeywords = officeKeywordRepo.FindByOfficeCode(code);
        if(!officeKeywords.isEmpty()) return officeKeywords;
        return null;
    }

    public Boolean DeletePostOffice(String code){
        PostOffice checked =  getOffice(code);
        if(checked == null) return false;
        List<AlternateOffice> alternateOffices = getAlternateOfficeByOffice(code);
        if(!alternateOffices.isEmpty()) throw new RuntimeException("you can't delete this office because it have alternate office");
        List<OfficeKeyword> keywords = getOfficeKeywordByOffice(code);
        if(!keywords.isEmpty()) throw new RuntimeException("you can't delete this office because it have office keyword");
        postOfficeRepo.deleteById(code);
        return true;
    }

    public Boolean DeleteAlt(String id){
        Optional<AlternateOffice> alternateOffice = alternateOfficeRepo.findById(id);
        if(alternateOffice.isPresent()) {
            alternateOfficeRepo.deleteById(id);
            return true;
        }
        return false;
    }



    public Boolean DeleteKeyword(Long id){
        Optional<OfficeKeyword> keyword = officeKeywordRepo.findById(id);
        if(keyword.isPresent()) {
            officeKeywordRepo.deleteById(id);
            return true;
        }
        return false;
    }



    //Saving Keyword List
    public List<OfficeKeyword> checkKeyList(List<OfficeKeyword> officeKeywords,String code) throws NotFoundException {
        PostOffice office = getOffice(code);
        if(getOffice(code) == null) throw new NotFoundException("This post office doesn't exist");

        for(OfficeKeyword  officeKeyword : officeKeywords){
             officeKeyword.setOFFICE_CODE(office);
        }
        return officeKeywords;
    }
    public List<OfficeKeyword> saveKey(List<OfficeKeyword> officeKeywords){
        List<OfficeKeyword> saved = new ArrayList<>();
        for(OfficeKeyword  officeKeyword : officeKeywords){
            saved.add(officeKeywordRepo.save(officeKeyword));
        }
        return saved;
    }
    public List<OfficeKeyword> SaveKeyList(List<OfficeKeyword> officeKeywords,String code) throws NotFoundException {
        if(checkKeyList(officeKeywords,code) == null) return null;
        return saveKey(officeKeywords);
    }

    //Saving Alt List

    public List<AlternateOffice> checkAltList(List<AlternateOffice> alternateOffices,String code) throws NotFoundException {
        PostOffice office = getOffice(code);
        if(getOffice(code) == null) throw new NotFoundException("Office Id doesn't exist");

        for(AlternateOffice  alternateOffice : alternateOffices){
            if(getOffice(alternateOffice.getALT_OFFICE_CODE()) == null) throw new NotFoundException("Alternate Office Id doesn't exist");
            if(Objects.equals(code, alternateOffice.getALT_OFFICE_CODE()))
                throw new NotFoundException("You can't make alternate post office Id same with post office Id");
            alternateOffice.setOFFICE_CODE(office);
        }
        return alternateOffices;
    }


    public List<AlternateOffice> saveAlt(List<AlternateOffice> alternateOffices){
        List<AlternateOffice> saved = new ArrayList<>();
        for(AlternateOffice alternateOffice : alternateOffices){
            saved.add(alternateOfficeRepo.save(alternateOffice));
        }
        return saved;
    }

    public List<AlternateOffice> SaveAltList(List<AlternateOffice> alternateOffices,String code) throws NotFoundException {
        if(checkAltList(alternateOffices,code) == null) return null;
        return saveAlt(alternateOffices);
    }

    //Update Alternate Office

    public List<AlternateOffice> checkUpdateAltList(List<AlternateOffice> alternateOffices,String code){
        PostOffice office = getOffice(code);
        if(getOffice(code) == null)return null;

        for(AlternateOffice  alternateOffice : alternateOffices){

            if(!(alternateOfficeRepo.findById(alternateOffice.getALT_OFFICE_CODE()).isPresent())) return null;
            alternateOffice.setOFFICE_CODE(office);
        }
        return alternateOffices;
    }
    public List<AlternateOffice> updateAltList(List<AlternateOffice> alternateOffices,String code){
        if(checkUpdateAltList(alternateOffices,code) == null) return null;
        return saveAlt(alternateOffices);
    }

    //Update OfficeKeyword
    public List<OfficeKeyword> checkUpdateKeyList(List<OfficeKeyword> officeKeywords,String code){
        PostOffice office = getOffice(code);
        if(getOffice(code) == null) return null;
        for(OfficeKeyword  officeKeyword : officeKeywords){
            if(!officeKeywordRepo.findById(officeKeyword.getKEYWORD_CODE()).isPresent()) return null;
            officeKeyword.setOFFICE_CODE(office);
        }
        return officeKeywords;
    }
    public List<OfficeKeyword> updateKeyList(List<OfficeKeyword> officeKeywords,String code){

        if(checkUpdateKeyList(officeKeywords,code) == null) return null;
        return saveKey(officeKeywords);
    }


}
