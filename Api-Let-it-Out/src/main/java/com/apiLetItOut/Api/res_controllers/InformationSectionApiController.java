package com.apiLetItOut.Api.res_controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.CognitiveDistortionsService;
import com.apiLetItOut.Api.services.DomainsService;
import com.apiLetItOut.Api.services.InformativeArticlesService;
import com.apiLetItOut.Api.services.PsychiatricDomainService;
import com.apiLetItOut.Api.services.UserService;
import com.apiLetItOut.Api.services.UserTAGService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("api/informationSection/")
public class InformationSectionApiController {
    @Autowired
    UserTAGService userTAGService;

    @Autowired
    UserService userService;

    @Autowired
    PsychiatricDomainService psychiatricDomainService;

    @Autowired 
    DomainsService domainsService;

    @Autowired
    InformativeArticlesService informativeArticlesService;

    @Autowired
    CognitiveDistortionsService cognitiveDistortionsService;

    @PostMapping("dataGeneral")
    public ResponseEntity<Map<String, Object>> getBasicDataOfDocs(@RequestParam("user") String user) {
        //Obtención de datos
        int userId = -1;
        userId = userService.SearchUserTAGMethod(user); //busca por username
        if (userId == -1) { // 
            userId = userService.SearchUsersByEmailMethod(user);// Si no lo encuentra
        }
        int userTAGId = userTAGService.FindUserTAGMethod(userId);
        //Datos generales
        int age = userService.SearchUserAgeMethod(userId);
        String rangeAge="niños";
        if(age < 21 && age>13)
        {
            rangeAge = "adolescentes";
        } else if(age>20 && age<36)
        {
            rangeAge="adulto joven";
        } else if(age>34 && age<60)
        {
            rangeAge = "adulto";
        } else if(age>59)
        {
            rangeAge="adultos mayores";
        }
        String gender = userService.SearchUserGenderMethod(userId);
        if(gender.equals("F") || gender.equals("f"))
        {
            gender = "mujeres";
        }
        if(gender.equals("M") || gender.equals("m"))
        {
            gender = "hombres";
        }
        if(gender.equals("P") || gender.equals("p"))
        {
            gender = "Todos";
        }
        int levelTAGId = userTAGService.SearchLevelTAGMethod(userTAGId);
        String levelTAG = "Leve";
        if(levelTAGId==2)
        {
            levelTAG="Moderada";
        }
        else if (levelTAGId==3)
        {
            levelTAG="Severo";
        }
        else if(levelTAGId==4)
        {
            levelTAG="Grave";
        }
        //Distorsiones Cognitivas
        List<String> cognitiveDistortions = fillListWithDistortions();
        List<String> cognitiveDistortionsOfUser = cognitiveDistortionsService.SearchCongitiveDistortionsOfUserMethod(userTAGId);
        if(cognitiveDistortionsOfUser != null && !cognitiveDistortionsOfUser.isEmpty())
        {
            cognitiveDistortions.removeAll(cognitiveDistortionsOfUser);
        }
        // Dominios
        List<Integer> domainsId = psychiatricDomainService.SearchDomainsOfUserTAGMethod(userTAGId);
        List<String> namesDomains = new ArrayList<>();
        for (Integer domainId : domainsId) {
            namesDomains.add(domainsService.SearchNameOfDomain(domainId));
        }
        //Obtencion de datos a mandar
        List<String> topics = fillListOfTopics(levelTAG, rangeAge, gender, namesDomains, cognitiveDistortionsOfUser, cognitiveDistortions);
        List<String> names = new ArrayList<>();
        List<String> classifications = new ArrayList<>();
        List<Integer> types = new ArrayList<>();
        for (String topic : topics) {
            System.out.println("el topid es " + topic);
            try
            {
                List<Integer> articlesId = informativeArticlesService.SearchIdOfDocumentsMethod(topic);
                if (articlesId != null) {
                    for(Integer articleId: articlesId){
                        int id = articleId.intValue(); // Solo llamamos a intValue() si articleId no es null
                        String name = informativeArticlesService.SearchNameOfDocumentMethod(id);
                        if(!names.contains(name))
                        {
                            names.add(name);
                            String classification = informativeArticlesService.SearchClassificationOfDocumentMethod(id);
                            classifications.add(classification);
                            int type = informativeArticlesService.SearchTypeOfDocumentMethod(id);
                            types.add(type);
                        }else
                        {
                            continue;
                        }
                    }
                }
            }catch (NullPointerException ex)
            {
                continue;
            }
        }
        
        
        Map<String, Object> responseData = new HashMap<>();
        for (int i = 0; i < names.size(); i++) {
            int j = i;
            System.out.println("name"+j + " es: " + names.get(j));
            responseData.put("name" + j, names.get(j));
            System.out.println("class"+j + " es: " + classifications.get(j));
            responseData.put("classification" + j, classifications.get(j));
            System.out.println("types"+j + " es: " + types.get(j));
            responseData.put("type" + j, types.get(j));
        }
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("dataGeneral/domains")
    public ResponseEntity<Map<String, Object>> getBasicDataDomains(@RequestParam("user") String user)
    {
        int userId = -1;
        userId = userService.SearchUserTAGMethod(user); //busca por username
        if (userId == -1) { // 
            userId = userService.SearchUsersByEmailMethod(user);// Si no lo encuentra
        }
        int userTAGId = userTAGService.FindUserTAGMethod(userId);
        // Dominios
        List<Integer> domainsId = psychiatricDomainService.SearchDomainsOfUserTAGMethod(userTAGId);
        List<String> namesDomains = new ArrayList<>();
        for (Integer domainId : domainsId) {
            namesDomains.add(domainsService.SearchNameOfDomain(domainId));
        }
        //Obtencion de datos a mandar
        List<String> names = new ArrayList<>();
        List<String> classifications = new ArrayList<>();
        List<Integer> types = new ArrayList<>();
        for (String domain : namesDomains) {
            System.out.println("el domain es " + domain);
            try
            {
                List<Integer> articlesId = informativeArticlesService.SearchIdOfDocumentsMethod(domain);
                for(Integer articleId: articlesId){
                    if (articleId != null) {
                        int id = articleId.intValue(); // Solo llamamos a intValue() si articleId no es null
                        String name = informativeArticlesService.SearchNameOfDocumentMethod(id);
                        names.add(name);
                        String classification = informativeArticlesService.SearchClassificationOfDocumentMethod(id);
                        classifications.add(classification);
                        int type = informativeArticlesService.SearchTypeOfDocumentMethod(id);
                        types.add(type);
                    }
                }
            }catch (NullPointerException ex)
            {
                continue;
            }
        }
        
        
        Map<String, Object> responseData = new HashMap<>();
        for (int i = 0; i < domainsId.size(); i++) {
            System.out.println("name"+i + " es: " + names.get(i));
            responseData.put("name" + i, names.get(i));
            System.out.println("class"+i + " es: " + classifications.get(i));
            responseData.put("classification" + i, classifications.get(i));
            System.out.println("types"+i + " es: " + types.get(i));
            responseData.put("type" + i, types.get(i));
        }
        return ResponseEntity.ok(responseData);

    }

    public List<String> fillListWithDistortions()
    {
        List<String> cognitiveDistortions = new ArrayList<>();
        cognitiveDistortions.add("Leer la mente");
        cognitiveDistortions.add("Adivinar el futuro");
        cognitiveDistortions.add("Catastrofización");
        cognitiveDistortions.add("Maximizar");
        cognitiveDistortions.add("Minimizar");
        cognitiveDistortions.add("Etiquetar");
        cognitiveDistortions.add("Descalificar");
        cognitiveDistortions.add("Filtro negativo");
        cognitiveDistortions.add("Sobregeneralizacion negativa");
        cognitiveDistortions.add("Pensamiento del todo o nada");
        cognitiveDistortions.add("Orientación hacia el remordimiento");
        cognitiveDistortions.add("“Debo” o “Tengo”");
        cognitiveDistortions.add("Personalización");
        cognitiveDistortions.add("Comparación falsa");
        cognitiveDistortions.add("“Y que sí...”");
        cognitiveDistortions.add("Razonamiento emocional");
        return cognitiveDistortions;
    }

    public List<String> fillListOfTopics(String levelTAG, String age, String gender, List<String> domains, List<String> distortionRecognized, List<String> distortions)
    {
        List<String> topics = new ArrayList<>();
        topics.add(age);
        if(gender.equals("Todos"))
        {
            topics.add("Mujeres");
            topics.add("LGBTQ+");
        }
        if(gender.equals("Mujeres"))
        {
            topics.add("Mujeres");
        }
        List<String> domainsGeneral = informativeArticlesService.SearchNamesOfClassficationMethod("Distorsiones Cognitivas. Información general");
        for(String tag: domainsGeneral)
        {
            topics.add(tag);
        }
        //distortion
        if(distortionRecognized!=null)
        {
            for (String distortion : distortionRecognized) {
                topics.add(distortion);
            }
        }
        
        //domains
        for (String domain : domains) {
            topics.add(domain);
        }
        //tag
        List<String> tagInformation = informativeArticlesService.SearchNamesOfClassficationMethod("Trastorno de Ansiedad Generalizada");
        for(String tag: tagInformation)
        {
            topics.add(tag);
        }
        //tag
        List<String> sintomas = informativeArticlesService.SearchNamesOfClassficationMethod("Sintomas de la ansiedad");
        for(String tag: sintomas)
        {
            topics.add(tag);
        }
        List<String> levels = informativeArticlesService.SearchNamesOfClassficationMethod("Niveles de ansiedad");
        for(String tag: levels)
        {
            topics.add(tag);
        }
        List<String> techniques = informativeArticlesService.SearchNamesOfClassficationMethod("Técnicas de relajación");
        for(String tag: techniques)
        {
            topics.add(tag);
        }
        List<String> fobiaSocial = informativeArticlesService.SearchNamesOfClassficationMethod("Fobia Social");
        for(String tag: fobiaSocial)
        {
            topics.add(tag);
        }
        List<String> toc = informativeArticlesService.SearchNamesOfClassficationMethod("Trastorno obsesivo-compulsivo");
        for(String tag: toc)
        {
            topics.add(tag);
        }
        List<String> todo = informativeArticlesService.SearchNamesOfClassficationMethod("Todo sobre la ansiedad");
        for(String tag: todo)
        {
            topics.add(tag);
        }
        for (String distortion : distortions) {
            topics.add(distortion);
        }
        List<String> panico = informativeArticlesService.SearchNamesOfClassficationMethod("Trastorno de Pánico");
        for(String tag: panico)
        {
            topics.add(tag);
        }
        List<String> agorafobia = informativeArticlesService.SearchNamesOfClassficationMethod("Agorafobia");
        for(String tag: agorafobia)
        {
            topics.add(tag);
        }
        List<String> fobias = informativeArticlesService.SearchNamesOfClassficationMethod("Fobias");
        for(String tag: fobias)
        {
            topics.add(tag);
        }
        return topics;
    }

    @PostMapping("especificData")
    public ResponseEntity<Map<String, String>> getDataOfDocs (@RequestParam("name") String name)
    {
        Map<String, String> data = new HashMap<>();
        try
        {
            Integer articleId = informativeArticlesService.SearchIdOfDocumentMethod(name);
            if (articleId != null) {
                int id = articleId.intValue(); // Solo llamamos a intValue() si articleId no es null
                String classification = informativeArticlesService.SearchClassificationOfDocumentMethod(id);
                String sinopsis = informativeArticlesService.SearchSinopsisOfDocumentMethod(id);
                String url = informativeArticlesService.SearchUrlOfDocumentMethod(id);
                if(classification!=null && sinopsis!=null && url!=null)
                {
                    data.put("name", name);
                    data.put("classification", classification);
                    data.put("sinopsis", sinopsis);
                    data.put("url", url);
                }
            }
            

        }catch (NullPointerException ex)
        {
            data.put("name", "hola");
            data.put("classification", "hola");
            data.put("sinopsis", "hola");
            data.put("url", "hola");
        }
        return ResponseEntity.ok(data);
    }
}
