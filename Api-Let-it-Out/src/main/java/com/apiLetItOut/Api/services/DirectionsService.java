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
}
