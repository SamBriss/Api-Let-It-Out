package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.ResponsableAdultRepository;

@Service
public class ResponsableAdultService {
    @Autowired
    ResponsableAdultRepository responsableAdultRepository;
    public Integer responsableAdultServiceMethod(int userTAGId, String nameResponsable, String parentesco) 
    {
        return responsableAdultRepository.RegisterNewAdutResponsable(userTAGId, nameResponsable, parentesco);
    }

}
