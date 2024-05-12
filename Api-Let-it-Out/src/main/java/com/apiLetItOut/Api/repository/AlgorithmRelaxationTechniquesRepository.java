package com.apiLetItOut.Api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.TechniquesRanking;

import jakarta.transaction.Transactional;


@Repository
public interface AlgorithmRelaxationTechniquesRepository extends CrudRepository<TechniquesRanking, Integer> {

    @Query(value = "Select COUNT(*) FROM techniques_ranking", nativeQuery = true)
    int countRankingExitenceRegisters();

    @Query(value = "Select categoryId FROM techniques_ranking WHERE rankingId=:lastCount", nativeQuery = true)
    Integer getLastCategoryId(@Param("lastCount") int lastCount);
    
    @Query(value = "SELECT categoryId FROM techniques_ranking " +
    "ORDER BY rankingId DESC LIMIT 1", nativeQuery = true)
    int getCategoryIdFromLastRankingId();



// get scores by preferences

    @Query(value = "SELECT l.audioId, r.url, AVG(l.score) " +
    "FROM listenedaudiosfeedback l " +
    "INNER JOIN relaxationtechniqueaudios r ON l.audioId = r.audioId " +
    "WHERE r.preferenceSensorial = :preference " +
    "AND l.feedbackDate >= DATE_SUB(CURDATE(), INTERVAL 6 WEEK) " +
    "GROUP BY l.audioId", nativeQuery = true)
    List<Object[]> getAverageScoreRepetitionsByAudioIdAndSensorialPreference(@Param("preference") String preference);

    @Query(value = "SELECT l.audioId, r.url, AVG(l.score) " +
    "FROM listenedaudiosfeedback l " +
    "INNER JOIN relaxationtechniqueaudios r ON l.audioId = r.audioId " +
    "WHERE r.preferenceAuditve=:preference " +
    "AND l.feedbackDate>=DATE_SUB(CURDATE(), INTERVAL 6 WEEK) " +
    "GROUP BY l.audioId", nativeQuery = true)
    List<Object[]> getAverageScoreRepetitionsByAudioIdAndAuditivePreference(@Param("preference") String preference);
    
    @Query(value = "SELECT l.audioId, r.url, AVG(l.score) " +
    "FROM listenedaudiosfeedback l " +
    "INNER JOIN relaxationtechniqueaudios r ON l.audioId = r.audioId " +
    "WHERE l.feedbackDate >= DATE_SUB(CURDATE(), INTERVAL 6 WEEK) "+
    "AND TIME_TO_SEC(r.duration) >:secondsMin " +
    "AND TIME_TO_SEC(r.duration) <:secondsMax "+
    "GROUP BY l.audioId", nativeQuery = true)
    List<Object[]> getAverageScoreRepetitionsByAudioIdAndDuration(@Param("secondsMin") int secondsMin,
                                                                  @Param("secondsMax") int secondsMax);

    @Query(value = "SELECT l.audioId, r.url, AVG(l.score) " +
    "FROM listenedaudiosfeedback l " +
    "INNER JOIN relaxationtechniqueaudios r ON l.audioId = r.audioId " +
    "WHERE r.auditory=:preference " +
    "AND l.feedbackDate>=DATE_SUB(CURDATE(), INTERVAL 6 WEEK) " +
    "GROUP BY l.audioId", nativeQuery = true)
    List<Object[]> getAverageScoreRepetitionsByAudioIdAndLifestylePreference(@Param("preference") String preference);
    
    @Query(value = "SELECT l.audioId, r.url, AVG(l.score) " +
    "FROM relaxationtechniqueaudios r " +
    "INNER JOIN listenedaudiosfeedback l ON r.audioId=l.audioId " +
    "INNER JOIN usersTAG t ON l.userTAGId=t.userTAGId " + // Agregado espacio al final
    "INNER JOIN users u ON t.userId=u.userId " + // Agregado espacio al final
    "WHERE u.age>=:startAge AND u.age<=:endAge " +
    "AND l.feedbackDate>=DATE_SUB(CURDATE(), INTERVAL 6 WEEK) " +
    "GROUP BY l.audioId", nativeQuery = true)
List<Object[]> getAverageScoreRepetitionsAudioIdByAgeUser(@Param("startAge") int startAge,
                                                            @Param("endAge") int endAge);

                                                              
// get repetitions by preference

    @Query(value = "Select COUNT(l.audioId) from listenedaudiosfeedback l WHERE l.audioid=:audioId", nativeQuery = true)
    int getCountOfAudioIdInFeedbacks(@Param("audioId") int audioId);
    
    @Query(value = "Select COUNT(*) from listenedaudiosfeedback", nativeQuery = true)
    int getCountOfFeedbacks();
    
    @Query(value = "Select COUNT(l.audioId) from listenedaudiosfeedback l INNER JOIN relaxationtechniqueaudios r "+
    "ON l.audioId=r.audioId WHERE r.preferenceSensorial=:preference", nativeQuery = true)
    int getCountOfFeedbacksByPreferenceSensorial(@Param("preference") String preference);

    @Query(value = "Select COUNT(l.audioId) from listenedaudiosfeedback l INNER JOIN relaxationtechniqueaudios r "+
    "ON l.audioId=r.audioId WHERE r.preferenceAuditve=:preference", nativeQuery = true)
    int getCountOfFeedbacksByPreferenceAuditive(@Param("preference") String preference);
    
    @Query(value = "Select COUNT(l.audioId) from listenedaudiosfeedback l INNER JOIN relaxationtechniqueaudios r "+
    "ON l.audioId=r.audioId WHERE r.auditory=:preference", nativeQuery = true)
    int getCountOfFeedbacksByPreferenceAuditory(@Param("preference") String preference);

    
    @Query(value = "Select COUNT(l.audioId) from listenedaudiosfeedback l INNER JOIN relaxationtechniqueaudios r "+
    "ON l.audioId=r.audioId WHERE TIME_TO_SEC(r.duration) >:secondsMin " +
    "AND TIME_TO_SEC(r.duration) <:secondsMax ", nativeQuery = true)
    int getCountOfFeedbacksByPreferenceDuration(@Param("secondsMin") int secondsMin,
                                                @Param("secondsMax") int secondsMax);


    @Query(value = "Select COUNT(l.audioId) from relaxationtechniqueaudios r "+
    "INNER JOIN listenedaudiosfeedback l ON r.audioId=l.audioId " +
    "INNER JOIN usersTAG t ON l.userTAGId=t.userTAGId "+
    "INNER JOIN users u ON t.userId=u.userId "+
    "WHERE u.age >:startAge AND u.age <:endAge", nativeQuery = true)
    int getCountOfFeedbacksByPreferenceAge(@Param("startAge") int startAge,
                                                      @Param("endAge") int endAge);


// add ranking
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO techniques_ranking (audioId, finalScore, countUses, usersScore, executionDate, categoryId, preferenceId) VALUES(:audioId, :finalScore, :countUses, :usersScore, :executionDate, :categoryId, :preferenceId)", nativeQuery = true)
    Integer addNewRankingPreferenceCategory(@Param("audioId") int audioId,
                        @Param("finalScore") double finalScore,
                        @Param("countUses") int countUses,
                        @Param("usersScore") double usersScore,
                        @Param("executionDate") Date executionDate,
                        @Param("categoryId") int categoryId,
                        @Param("preferenceId") String preferenceId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO techniques_ranking (executionDate, categoryId, preferenceId) VALUES(:executionDate, :categoryId, :preferenceId)", nativeQuery = true)
    Integer addNewRankingPreferenceCategorywithNull(
                        @Param("executionDate") Date executionDate,
                        @Param("categoryId") int categoryId,
                        @Param("preferenceId") String preferenceId);

    @Query(value = "SELECT executionDate FROM techniques_ranking ORDER BY executionDate DESC LIMIT 1", nativeQuery = true)
    Object selectLastExecutionDate();

    // 11-04-2024
    
    @Query(value = "SELECT t.categoryId, t.executionDate FROM techniques_ranking t " +
    "ORDER BY rankingId DESC LIMIT 1", nativeQuery = true)
    Object[] getCategoryIdAndDateFromLastRankingId();
    
    @Query(value = "Select r.categoryId, r.preferenceId, a.name, a.url, r.countUses, r.usersScore, r.finalScore, a.audioId, a.techniqueLevel, :levelTechniques as levelTechniques, :count as count from techniques_ranking r INNER JOIN relaxationtechniqueaudios a ON r.audioId=a.audioId "+
    "WHERE r.preferenceId=:preference AND r.executionDate=:date AND r.audioId >0", nativeQuery = true)
    Object getTechniqueOfTheWeekForEachUser(String preference, Date date, int levelTechniques, int count);
    
    @Query(value = "SELECT executionDate FROM techniques_ranking " +
    "ORDER BY rankingId DESC LIMIT 1", nativeQuery = true)
    Date getDateFromLastRankingId();

    // 13-04-2023
    @Query(value = "Select c.count from count_ranking_technique_by_user c INNER JOIN usersTAG t ON c.userTAGId=t.userTAGId INNER JOIN users u ON t.userId=u.userId WHERE u.username=:username", nativeQuery = true)
    int getCountByUserTAG(@Param("username") String username);
    
    @Transactional
    @Modifying
    @Query(value = "Update count_ranking_technique_by_user set count =:count WHERE userTAGId =:userTAGId", nativeQuery = true)
    Integer UpdateCountRanking(@Param("userTAGId") int userTAGId, 
                        @Param("count") int count);

    @Query(value = "Select userTAGId from count_ranking_technique_by_user", nativeQuery = true)
    List<Integer> GetUserTAGIdFromCountRanking();

}