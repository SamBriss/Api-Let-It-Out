package com.apiLetItOut.Api.res_controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.DomainsService;
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
import org.springframework.web.bind.annotation.RequestBody;



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

    @PostMapping("getDomains")
    public ResponseEntity<Map<String, Object>> getDomainsToShowArticles(@RequestParam("user") String user) {
        int userId  = -1;
        userId =userService.SearchUserTAGMethod(user);
        if(userId==-1)
        {
            userId = userService.SearchUsersByEmailMethod(user);
        }
        List<Integer> domainsId = new ArrayList<>();
        domainsId = psychiatricDomainService.SearchDomainsOfUserTAGMethod(userId);
        List<Integer> scores = new ArrayList<>();
        scores = psychiatricDomainService.SearchScoreOfDomainsId(userId);
        List<String> domainNames = new ArrayList<>();
        for (int domain : domainsId) {
            domainNames.add(domainsService.SearchNameOfDomain(domain));
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
    public String postMethodName(@RequestBody String entity) {
        
        return entity;
    }
    
    
}
