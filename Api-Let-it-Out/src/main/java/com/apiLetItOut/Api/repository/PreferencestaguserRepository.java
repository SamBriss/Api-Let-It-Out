package com.apiLetItOut.Api.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.PreferencesTAGUser;

import jakarta.transaction.Transactional;

@Repository
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
    @Transactional
    @Modifying
    @Query(value = "Update preferencestaguser " + 
            "set score = :score where userTAGId = :userTAGId and name = :name", nativeQuery = true)
    Integer UpdateUserPreferences(@Param("name") String name,
                                    @Param("score") int score,
                                    @Param("userTAGId") int userTAGId);

    @Query(value="Select score from preferencestaguser where userTAGId=:userTAGId and name LIKE CONCAT('%', :name, '%')", nativeQuery = true)
    Integer SearchScoreOfPreferenceUserTAG(@Param ("userTAGId") int userTAGId, @Param ("name") String name);

    @Query(value = "Select name from preferencestaguser where userTAGId=:userTAGId and categoryId = :categoryId and score = 0 LIMIT 2", nativeQuery = true)
    List<String> SearchPreferenceAuditive0Score(@Param ("userTAGId") int userTAGId, @Param("categoryId") int categoryId);

    @Query(value = "Select name from preferencestaguser where userTAGId=:userTAGId and categoryId = :categoryId ORDER BY score ASC LIMIT 1 ", nativeQuery = true)
    String SearchPreferenceAuditiveFavorite(@Param ("userTAGId") int userTAGId, @Param("categoryId") int categoryId);

    @Query(value = "Select name from preferencestaguser where userTAGId=:userTAGId and categoryId = :categoryId order by score ASC LIMIT 2", nativeQuery = true)
    List<String> SearchLastPreferencesSensoriales(@Param ("userTAGId") int userTAGId, @Param("categoryId") int categoryId);

    @Query(value = "Select name from preferencestaguser where userTAGId=:userTAGId and categoryId = :categoryId order by score DESC LIMIT 1", nativeQuery = true)
    String SearchLifeStylePreference(@Param ("userTAGId") int userTAGId, @Param("categoryId") int categoryId);
}
