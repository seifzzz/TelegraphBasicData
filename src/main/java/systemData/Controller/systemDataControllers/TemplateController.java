package systemData.Controller.systemDataControllers;

import javassist.NotFoundException;
import systemData.models.BasicData.Template.Language;
import systemData.models.BasicData.Template.Template;
import systemData.models.BasicData.Template.TemplateType;
import systemData.payload.request.TemplateReq;
import systemData.payload.response.Response;
import systemData.services.SystemDataServices.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("Template")
public class TemplateController {
    @Autowired
    TemplateService templateService;
    @GetMapping("allTemplateType")
    public ResponseEntity<?> AllTemplateType() throws NotFoundException {
        List<TemplateType> templateTypes = templateService.getAllTemplateType();
        if(templateTypes == null || templateTypes.isEmpty()) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"All Template Type",templateTypes));
    }

    @GetMapping("allLanguages")
    public ResponseEntity<?> AllLanguages() throws NotFoundException {
        List<Language> languages = templateService.getAllLanguage();
        if(languages == null || languages.isEmpty()) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"All Languages",languages));
    }

    @GetMapping("allTemplate/{code}")
    public ResponseEntity<?> AllTemplateType(@PathVariable String code) throws NotFoundException {
        List<Template> templates = templateService.getAllTemplateByType(code);
        if(templates == null || templates.isEmpty()) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"Successful Request",templates));
    }

    @GetMapping("TemplateType/{code}")
    public ResponseEntity<?> TemplateTypeOne(@PathVariable String code) throws NotFoundException {
        TemplateType templateType = templateService.getTemplateTypeOne(code);
        if(templateType == null) throw new NotFoundException("Id doesn't exist");
        return  ResponseEntity.ok(new Response(200,"Successful Request",templateType));
    }

    @GetMapping("Languages/{code}")
    public ResponseEntity<?> LanguageOne(@PathVariable String code) throws NotFoundException {
        Language language = templateService.getLanguageByCode(code);
        if(language == null) throw new NotFoundException("Id doesn't exist");
        return  ResponseEntity.ok(new Response(200,"Successful Request",language));
    }

    @GetMapping("{code}")
    public ResponseEntity<?> TemplateOne(@PathVariable String code) throws NotFoundException {
        Template template = templateService.getTemplate(code);
        if(template == null) throw new NotFoundException("Id doesn't exist");
        return  ResponseEntity.ok(new Response(200,"Successful Request",template));
    }

    @PostMapping("saveTemplate")
    public ResponseEntity<?> createTemplate(@RequestBody TemplateReq templateReq) throws NotFoundException {
        Template template = templateService.CreateTemplate(templateReq);
        if(template == null) throw new NotFoundException("Id already exist");
        return ResponseEntity.ok(new Response(200,"Template is created",template));
    }
    @PostMapping("saveTemplateType")
    public ResponseEntity<?> createTemplateType(@RequestBody TemplateType templateType) throws NotFoundException {
          TemplateType created = templateService.CreateTemplateType(templateType);
        if(created == null) throw new NotFoundException("Id already exist");
        return ResponseEntity.ok(new Response(200,"Template type is created",created));
    }

    @DeleteMapping("{code}")
    public ResponseEntity<?> deleteTemplate(@PathVariable String code) throws NotFoundException {
        boolean deleted = templateService.DeleteTemplate(code);
        if(deleted) return ResponseEntity.ok( new Response(200,"Template is deleted","Successful Request"));
        throw new NotFoundException("Id doesn't exist");
    }
    @DeleteMapping("TemplateType/{code}")
    public ResponseEntity<?> deleteTemplateType(@PathVariable String code) throws NotFoundException {
        boolean deleted = templateService.DeleteTemplateType(code);
        if(deleted) return ResponseEntity.ok( new Response(200,"Template Type is deleted","Successful Request"));
        throw new NotFoundException("Id doesn't exist");
    }
    @PatchMapping("{code}")
    public ResponseEntity<?> updateTemplate (@RequestBody TemplateReq templateReq) throws NotFoundException {
        Template template = templateService.UpdateTemplate(templateReq);
        if(template == null) throw new NotFoundException("Id doesn't exist");
        return ResponseEntity.ok(new Response(200,"Template is updated",template));

    }
    @PatchMapping("TemplateType/{code}")
    public ResponseEntity<?> updateTemplateType(@RequestBody TemplateType templateType) throws NotFoundException {
        TemplateType updated = templateService.UpdateTemplateType(templateType);
        if (updated == null) throw new NotFoundException("Id doesn't exist");
        return ResponseEntity.ok(new Response(200,"Template Type is updated",updated));
    }

}

