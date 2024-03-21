package com.apiLetItOut.Api.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.UserTAGRequestRepository;

@Service
public class UserTAGRequestService {
    @Autowired
    UserTAGRequestRepository userTAGRequestRepository;
    public int RegisterRequestTAGMethod(int userTAGId, int status, int userTherapistId)
    {
        return userTAGRequestRepository.RegisterNewRequestUserTAG(userTAGId, status, userTherapistId);
    }
    public List<Integer> FoundUserTAGIdByTherapistMethod(int userTherapistId)
    {
        return userTAGRequestRepository.FoundIdUserTAGByTherapist(userTherapistId);
    }
    public int CountRequestQuantityMethod(int userTherapistId)
    {
        return userTAGRequestRepository.CountRequestQuantity(userTherapistId);
    }
    public int DeleteRequestMethod(int userTAGId, int userTherapistId)
    {
        return userTAGRequestRepository.DeleteRequest(userTAGId, userTherapistId);
    }
}
