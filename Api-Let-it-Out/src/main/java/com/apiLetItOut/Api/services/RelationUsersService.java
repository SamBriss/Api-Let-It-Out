package com.apiLetItOut.Api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.RelationUsersRepository;

@Service
public class RelationUsersService {
    @Autowired
    RelationUsersRepository relationUsersRepository;
    public int RegisterNewRelationUsersMethod(int userTAGId, int userTherapistId)
    {
        return relationUsersRepository.RegisterNewRelationUsers(userTAGId, userTherapistId);
    }
    public List<Integer> SearchTherapistByTAGMethod(int userTAGId)
    {
        return relationUsersRepository.SearchTherapistByTAG(userTAGId);
    }
    public List<Integer> SearchTAGByTherapistMethos(int userTherapistId)
    {
        return relationUsersRepository.SearchTAGByTherapist(userTherapistId);
    }
    public int CountRequestQuantityVinculationMethod(int userTherapistId)
    {
        return relationUsersRepository.CountRequestQuantityVinculation(userTherapistId);
    }
    public int DeleteVinculationMethod(int userTAGId, int userTherapistId)
    {
        return relationUsersRepository.DeleteVinculation(userTAGId, userTherapistId);
    }
    public int ExistenceOfUserTAGWithTherapistMethod(int userTAGId)
    {
        return relationUsersRepository.ExistenceOfUserTAGWithTherapist(userTAGId);
    }
    public List<Object[]> SearchRelationTherapistsByUserTAGIdMethod(int userTAGId)
    {
        return relationUsersRepository.SearchRelationTherapistsByUserTAGId(userTAGId);
    }
    public int ExistenceOfVinculationMethod(int userTAGId, int userTherapistId)
    {
        return relationUsersRepository.ExistenceOfVinculation(userTAGId, userTherapistId);
    }
    public int CountMaxVinculationMethod(int userTAGId)
    {
        return relationUsersRepository.CountMaxVinculation(userTAGId);
    }
    public List<Object[]> SearchDataOfPatientsMethod(int userTherapistId)
    {
        return relationUsersRepository.SearchDataOfPatients(userTherapistId);
    }
    public List<Object[]> SearchDataOfPatientsUsersTAGIdMethod(int userTherapistId)
    {
        return relationUsersRepository.SearchDataOfPatientsUsersTAGId(userTherapistId);
    }
    public List<Object[]> SearchTherapistRelatedTAG(int userTAGId, int userTherapistId)
    {
        return relationUsersRepository.SearchTherapistRelatedTAG(userTherapistId, userTAGId);
    }
    public List<Object[]> SearchDataOfTherapistTherapistIdMethod(int userTAGId)
    {
        return relationUsersRepository.SearchDataOfTherapistUserTherapistId(userTAGId);
    }
    public List<Object[]> SearchPatientsDataForGraphicsMethod(int userTherapistId)
    {
        return relationUsersRepository.SearchPatientsDataForGraphics(userTherapistId);
    }
    public List<Integer> SearchPatientsLevelTAGMethod(int userTherapistId, int levelTAGId)
    {
        return relationUsersRepository.SearchPatientsLevelTAG(userTherapistId, levelTAGId);
    }
    public List<Integer> SearchPatientsAgeMethod(int userTherapistId, int topLimitAge, int bottomLimitAge)
    {
        return relationUsersRepository.SearchPatientsAge(userTherapistId, topLimitAge, bottomLimitAge);
    }
    
    public Integer SearchCountUseButtonsByUserIdMethod(int userTAGId)
    {
        return relationUsersRepository.SearchCountUseButtonsByUserId(userTAGId);
    }

    public Integer SearchCountUseDiaryByUserIdMethod(int userTAGId)
    {
        return relationUsersRepository.SearchCountUseDiaryByUserId(userTAGId);
    }
}
