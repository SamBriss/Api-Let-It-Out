package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.DirectionsRepository;

@Service
public class DirectionsService {
    @Autowired
    DirectionsRepository directionsRepository;
    public int RegisterNewDirectionsMethod(String street, int numExt, int numInt, String colony)
    {
        return directionsRepository.RegisterNewDirection(street, numExt, numInt, colony);
    }

    public int SearchDirectionIdMethod(String street, int numExt, int numInt, String colony)
    {
        return directionsRepository.SearchDirectionId(street, numExt, numInt, colony);
    }

    public String SearchStreetMethod(int directionId)
    {
        return directionsRepository.SearchStreet(directionId);
    }

    public String SearchColonyMethod(int directionId)
    {
        return directionsRepository.SearchColony(directionId);
    }

    public Integer SearchNumExtMethod(int directionId)
    {
        return directionsRepository.SearchNumExt(directionId);
    }

    public Integer SearchNumIntMethod(int directionId)
    {
        return directionsRepository.SearchNumInt(directionId);
    }

    public Integer UpdateDirectionMethod(String street, String colony, int numExt, int numInt, int directionId)
    {
        return directionsRepository.UpdateDirection(street, numExt, numInt, colony, directionId);
    }
}
