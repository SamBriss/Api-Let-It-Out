package com.apiLetItOut.Api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.UsersTAG;

import jakarta.transaction.Transactional;


@Repository
public interface UserTAGRepository extends CrudRepository<UsersTAG, Integer> {

    @Query(value = "SELECT userTAGId FROM userstag WHERE userId=:userId", nativeQuery = true)
    Integer countUsersByUserId(@Param("userId") int userId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO userstag (userId, levelTAGId, medsExistence, " +
        "registerDate, levelTAGQuestionaireDate, umbral, levelTechiniques) " +
        "VALUES (:userId, :levelTAGId, :medsExistence, " + 
        ":registerDate, :levelTAGQuestionaireDate, :umbral, :levelTechiniques)", nativeQuery = true)
    Integer RegisterNewUserTAG( @Param("userId") int userId,
                        @Param("levelTAGId") int levelTAGId,
                        @Param("medsExistence") int medsExistence,
                        @Param("registerDate") Date registerDate,
                        @Param("levelTAGQuestionaireDate") Date levelTAGQuestionaireDate,
                        @Param("umbral") int umbral,
                        @Param("levelTechiniques") int levelTechiniques);

    @Query(value= "Select userTAGId FROM userstag WHERE userId=:userId", nativeQuery = true)
    Integer FoundIdUserTag (@Param("userId") int userId);

    @Query(value= "Select userId FROM userstag WHERE userTAGId=:userTAGId", nativeQuery = true)
    Integer FoundUserIdByUserTAG (@Param("userTAGId") int userTAGId);

    @Query(value = "Select levelTAGId From userstag where userTAGId=:userTAGId", nativeQuery = true)
    Integer SearchLevelTAG(@Param("userTAGId") int userTAGId);

    @Query(value = "Select medsExistence from userstag where userTAGId=:userTAGId", nativeQuery = true)
    Boolean SearchMedsExistenceTAG(@Param("userTAGId") int userTAGId);

    @Transactional
    @Modifying
    @Query(value= "Update UsersTAG SET levelTAGId = :levelTAGId, medsExistence = :medsExistence, umbral = :umbral where userTAGId = :userTAGId", nativeQuery = true)
    Integer UpdateUserTAGLevelMedsExistence(@Param("levelTAGId") int levelTAGId,
                                            @Param("medsExistence") int medsExistence,
                                            @Param("umbral") int umbral,
                                            @Param("userTAGId") int userTAGId);

    @Query(value = "Select userTAGId from usersTAG ut join users u on ut.userId = u.userId where u.age>:bottomLimitAge AND age<:topLimitAge AND ut.levelTAGId = :levelTAGId", nativeQuery = true)
    List<Integer> SearchUsersSimilarsId (@Param("bottomLimitAge") int bottomLimitAge,
                                    @Param("topLimitAge") int topLimitAge,
                                    @Param("levelTAGId") int levelTAGId);

    @Query(value = "Select username from usersTAG", nativeQuery = true)
    List<String> SearchAllUsersTAG();
    
    @Query(value = "Select t.userTAGId from users u INNER JOIN userstag t ON u.userId=t.userId WHERE u.username=:username", nativeQuery = true)
    Integer GetUserTAGIdByeUsername(@Param("username") String username);

    @Query(value = "Select t.userTAGId from users u INNER JOIN userstag t ON u.userId=t.userId WHERE u.email=:email", nativeQuery = true)
    Integer GetUserTAGIdByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value= "Update UsersTAG SET levelTechiniques = :levelTechiniques where userTAGId = :userTAGId", nativeQuery = true)
    Integer UpdateUserTAGLevelTechniques(@Param("levelTechiniques") int levelTechiniques,
                                            @Param("userTAGId") int userTAGId);

    @Query(value = "Select levelTechiniques From userstag where userTAGId=:userTAGId", nativeQuery = true)
    Integer SearchLevelTechnique(@Param("userTAGId") int userTAGId);

    @Query(value = "Select 	levelTAGQuestionaireDate From userstag where userTAGId=:userTAGId", nativeQuery = true)
    Date SelectDateLevelQuiz(@Param("userTAGId") int userTAGId);

    @Transactional
    @Modifying
    @Query(value= "UPDATE userstag SET levelTAGQuestionaireDate = CURRENT_DATE(), levelTAGId =:levelTAGId WHERE userTAGId =:userTAGId", nativeQuery = true)
    Integer UpdateUserTAGLevelTAGId(@Param("levelTAGId") int levelTAGId,
                                            @Param("userTAGId") int userTAGId);
}