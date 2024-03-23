package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.UserTherapistRepository;

@Service
public class UserTherapistService {
    @Autowired
    UserTherapistRepository userTherapistRepository;

    public int RegisterNewUserTherapistMethod(int userId, String licence, boolean contract, int vinculationCode,
            int directionId) {
        return userTherapistRepository.RegisterNewUserTherapist(userId, licence, contract, vinculationCode,
                directionId);
    }

    public Integer FindUserTherapistsMethod(int userId) {
        return userTherapistRepository.FindUserTherapists(userId);
    }

    public Integer FindTherapistCodeMethod(int vinculationCode) {
        return userTherapistRepository.SearchTherapistCode(vinculationCode);
    }

    public Integer FoundTherapistIdMethod(int userId)
    {
        return userTherapistRepository.FoundIdUserTherapist(userId);
    }

    public Integer updateTherapistCodeMethod(int userTherapistId, int vinculationCode)
    {
        return userTherapistRepository.updateTherapistCode(userTherapistId, vinculationCode);
    }

    public Integer FindTherapistIdByCodeMethod(int vinculationCode) {
        return userTherapistRepository.SearchTherapistIdByCode(vinculationCode);
    }

    public Integer SearchTherapistExistanceCodeMethod(int userTherapistId)
    {
        return userTherapistRepository.SearchTherapistExistanceCode(userTherapistId);
    }

    public Integer SearchIdByTherapistMethod(int userTherapistId)
    {
        return userTherapistRepository.FoundUserIdByUserTherapist(userTherapistId);
    }

    public Integer SearchIdTherapistByUserIdMethod(int userId)
    {
        return userTherapistRepository.SearchIdTherapistByUserId(userId);
    }

    public String SearchLicenceTherapistMethod(int userTherapistId)
    {
        return userTherapistRepository.SearchLicenceTherapist(userTherapistId);
    }

    public Integer SearchDirectionIdTherapistMethod(int userTherapistId)
    {
        return userTherapistRepository.SearchDirectionIdTherapist(userTherapistId);
    }

    public Integer UpdateLicenceTherapistMethod(int userTherapistId, String licence)
    {
        return userTherapistRepository.UpdateLicenceTherapist(userTherapistId, licence);
    }
}
