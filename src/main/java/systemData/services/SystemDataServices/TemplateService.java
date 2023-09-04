package systemData.services.SystemDataServices;

import javassist.NotFoundException;
import systemData.models.BasicData.Template.Language;
import systemData.models.BasicData.Template.Template;
import systemData.models.BasicData.Template.TemplateType;
import systemData.payload.request.TemplateReq;
import systemData.payload.request.TemplateUpdateReq;
import systemData.repos.SystemDataRepos.LanguageRepo;
import systemData.repos.SystemDataRepos.TemplateRepo;
import systemData.repos.SystemDataRepos.TemplateTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class TemplateService {
    @Autowired
    TemplateRepo templateRepo;
    @Autowired
    TemplateTypeRepo templateTypeRepo;
    @Autowired
    LanguageRepo languageRepo;


   public List<TemplateType> getAllTemplateType(){
       List<TemplateType> templateTypes = templateTypeRepo.findAll();
       if(templateTypes.isEmpty()) return null;
       return templateTypes;
   }

   public List<TemplateReq> getAllTemplateByType(String code){
       List<Template> templates = templateRepo.findByType(code);
       if(templates == null || templates.isEmpty()) return null;
       List<TemplateReq> templateRes = new ArrayList<>();
       for(Template template :templates ) templateRes.add(new TemplateReq(template));
       return templateRes;
   }

   public TemplateType getTemplateTypeOne(String code){
       Optional<TemplateType> templateType = templateTypeRepo.findById(code);
       return templateType.orElse(null);
   }

   public TemplateReq getTemplate(String code){
       Optional<Template> template = templateRepo.findById(code);
       return template.map(TemplateReq::new).orElse(null);
   }
    public Template TemplateOne(String code){
        Optional<Template> template = templateRepo.findById(code);
        return template.orElse(null);
    }

   public TemplateType CreateTemplateType(TemplateType templateType){
       TemplateType checked = getTemplateTypeOne(templateType.getTEMP_TYPE_CODE());
       if(checked == null) return templateTypeRepo.save(templateType);
       return null;

   }

   public TemplateReq CreateTemplate(TemplateReq templateReq) throws NotFoundException {

       if(TemplateOne(templateReq.getTEMP_CODE()) != null) throw new NotFoundException("template code already exist");
       TemplateType templateType = getTemplateTypeOne(templateReq.getTEMP_TYPE_CODE());
       if(templateType == null) throw new NotFoundException("Template Type Code doesn't exist");
       Language language = getLanguageByCode(templateReq.getTEMP_LANG_CODE().toLowerCase(Locale.ROOT));
       if(language == null) throw new NotFoundException("language code doesn't exist");

       Template Created = Template.builder().TEMP_CODE(templateReq.getTEMP_CODE()).
               TEMP_LANG_CODE(language).LANG_DIRECTION(language.getLANG_DIRECTION()).
               TEMP_SUBJECT(templateReq.getTEMP_SUBJECT()).TEMP_NAME(templateReq.getTEMP_NAME()).
               TEMP_TYPE_CODE(templateType).build();
       return new TemplateReq(templateRepo.save(Created));

   }
    public List<Language> getAllLanguage(){
        List<Language> languages = languageRepo.findAll();
        if(languages.isEmpty()) return null;
        return languages;
    }
   public Language getLanguageByCode(String code){
       Optional<Language> language = languageRepo.findById(code);
       return language.orElse(null);
   }


   public boolean DeleteTemplate(String code){
       Optional<Template> template = templateRepo.findById(code);
       if(!template.isPresent()) return false;
       templateRepo.deleteById(code);
       return true;

   }

   public boolean DeleteTemplateType(String code){
       if(!templateTypeRepo.findById(code).isPresent()) return false;

       List<Template> templates = templateRepo.findByType(code);
       if(!templates.isEmpty()) throw new RuntimeException("Template Type has child from template, so you can't delete it");
       templateTypeRepo.deleteById(code);
       return true;

   }
   public TemplateType UpdateTemplateType(TemplateType templateType){
       if(getTemplateTypeOne(templateType.getTEMP_TYPE_CODE()) == null) return null;
       return templateTypeRepo.save(templateType);
   }
   public TemplateReq UpdateTemplate(TemplateReq templateReq,String code) throws NotFoundException {
       if(getTemplate(code) == null)  throw new NotFoundException("template code doesn't exist");
       TemplateType templateType = getTemplateTypeOne(templateReq.getTEMP_TYPE_CODE());
       if(templateType == null)  throw new NotFoundException("Template Type Code doesn't exist");
       Language language = getLanguageByCode(templateReq.getTEMP_LANG_CODE().toLowerCase(Locale.ROOT));
       if(language == null) throw new NotFoundException("language code doesn't exist");

       Template Updated = Template.builder().TEMP_CODE(code).
               TEMP_LANG_CODE(language).LANG_DIRECTION(language.getLANG_DIRECTION()).
               TEMP_SUBJECT(templateReq.getTEMP_SUBJECT()).TEMP_NAME(templateReq.getTEMP_NAME()).
               TEMP_TYPE_CODE(templateType).build();

       return new TemplateReq(templateRepo.save(Updated));
   }

}
