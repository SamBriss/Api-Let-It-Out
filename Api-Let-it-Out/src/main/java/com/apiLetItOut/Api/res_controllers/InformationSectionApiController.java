package com.apiLetItOut.Api.res_controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("getDomains")
    public ResponseEntity<Map<String, Object>> getDomainsToShowArticles(@RequestParam("user") String user) {
        int userId  = -1;
        userId =userService.SearchUserTAGMethod(user);
        if(userId==-1)
        {
            userId = userService.SearchUsersByEmailMethod(user);
        }
        List<Integer> domainsId = psychiatricDomainService.SearchDomainsOfUserTAGMethod(userId);
        for (int domain : domainsId) {
            System.out.println(domain);
        }
        System.out.println("llega aqui id");
        List<Integer> scores = psychiatricDomainService.SearchScoreOfDomainsId(userId);
        for (int domain : scores) {
            System.out.println(domain);
        }
        List<String> domainNames = new ArrayList<>();
        System.out.println("llega aqui score");
        for (int domain : domainsId) {
            domainNames.add(domainsService.SearchNameOfDomain(domain));
        }
        System.out.println("llega aqui domain");
        for (String domain : domainNames) {
            System.out.println(domain);
        }
        Map<String, Object> responseData = new HashMap<>();
        for(int i = 0; i<3; i++)
        {
            System.out.println(i);
            responseData.put(domainNames.get(i), scores.get(i));
        }
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("getDomains/DataGeneral")
    public ResponseEntity<Map<String, Object>> getBasicDataOfDocs(@RequestParam("domains") List<String> domains ) {
        int[] articlesId = new int[3];
        int i=0;
        for (String domain : domains) {
            articlesId[i] = informativeArticlesService.SearchIdOfDocumentMethod(domain);
            i++;
        }
        String[] names = new String[3];
        String[] classifications = new String[3];
        int[] types = new int[3];
        i=0;
        for (int articleId : articlesId) {
            names[i] = informativeArticlesService.SearchNameOfDocumentMethod(articleId);
            classifications[i] = informativeArticlesService.SearchClassificationOfDocumentMethod(articleId);
            types[i] = informativeArticlesService.SearchTypeOfDocumentMethod(articleId);
            i++;
        }
        Map<String, Object> responseData = new HashMap<>();
        String key;
        i=0;
        for (int j : articlesId) {
            key = "name" + j;
            responseData.put(key, names[i]);
            i++;
        }
        i=0;
        for (int j : articlesId) {
            key = "classification" + j;
            responseData.put(key, classifications[i]);
            i++;
        }

        i=0;
        for (int j : articlesId) {
            key = "type" + j;
            responseData.put(key, types[i]);
            i++;
        }

        return ResponseEntity.ok(responseData);
    }
    
    
}
