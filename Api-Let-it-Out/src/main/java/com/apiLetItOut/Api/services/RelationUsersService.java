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
}
