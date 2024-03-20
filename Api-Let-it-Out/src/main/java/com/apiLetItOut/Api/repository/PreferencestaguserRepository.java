package com.apiLetItOut.Api.repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.apiLetItOut.Api.models.PreferencesTAGUser;

import jakarta.transaction.Transactional;

public interface PreferencestaguserRepository extends CrudRepository <PreferencesTAGUser, Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO preferencestaguser " + 
            "(name, score, userTAGId, categoryId) " +
            "VALUES (:name, :score, :userTAGId, :categoryId)", nativeQuery = true)
    Integer RegisterNewUserPreferences(@Param("name") String name,
                        @Param("score") int score,
                        @Param("userTAGId") int userTAGId,
                        @Param("categoryId") int categoryId);

}
