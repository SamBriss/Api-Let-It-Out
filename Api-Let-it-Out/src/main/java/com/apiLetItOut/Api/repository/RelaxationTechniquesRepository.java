package com.apiLetItOut.Api.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.RelaxationTechniqueAudios;

@Repository
public interface RelaxationTechniquesRepository extends CrudRepository<RelaxationTechniqueAudios, Integer>{

    @Query(value = "SELECT audioId FROM relaxationtechniqueaudios WHERE duration < CONCAT('', :duration, '')", nativeQuery = true)
    List<Integer> allAudiosMinor5Min(@Param("duration") LocalTime duration);

    @Query(value = "SELECT audioId FROM relaxationtechniqueaudios where techniqueLevel<= :techniqueLevel", nativeQuery = true)
    List<Integer> allAudiosOfLevelAndBelowUser(@Param("techniqueLevel") int techniqueLevel);

    @Query(value = "Select audioId FROM relaxationtechniqueaudios where audioId = :audioId and auditory = :auditory", nativeQuery = true)
    Integer SearchAudioAccordingToLifeStyleAndId(@Param("audioId") int audioId, @Param("auditory") char auditory);

    @Query(value = "Select audioId FROM relaxationtechniqueaudios where audioId = :audioId and age != CONCAT('', :age, '')", nativeQuery = true)
    Integer SearchAudioAccordingToAgeAndId(@Param("audioId") int audioId, @Param("age") char age);

    @Query(value = "Select audioId FROM relaxationtechniqueaudios where audioId = :audioId and schedule != CONCAT('', :schedule, '')", nativeQuery = true)
    Integer SearchAudioAccordingToHourAndId(@Param("audioId") int audioId, @Param("schedule") char schedule);

    @Query(value = "Select audioId FROM relaxationtechniqueaudios where audioId = :audioId and preferenceAuditive = CONCAT('', :preferenceAuditive, '')", nativeQuery = true)
    Integer SearchAudioByPreferenceAuditive0AndId(@Param("audioId") int audioId, @Param("preferenceAuditive") String preferenceAuditive);

    @Query(value = "Select audioId FROM relaxationtechniqueaudios where audioId = :audioId and preferenceSensorial = CONCAT('', :preferenceSensorial, '')", nativeQuery = true)
    Integer SearchAudioByPreferenceSensorialAndId(@Param("audioId") int audioId, @Param("preferenceSensorial") String preferenceSensorial);

    @Query(value = "Select audioId FROM relaxationtechniqueaudios where audioId=:audioId and levelTAGId != :levelTAGId", nativeQuery = true)
    Integer SearchAudioByIdAndDifferentLevelTAG(@Param("audioId") int audioId, @Param("levelTAGId") int levelTAGId);

    @Query(value = "Select audioId FROM relaxationtechniqueaudios where audioId=:audioId and (gender != CONCAT('', :gender, '') OR gender != CONCAT('', :todo , ''))", nativeQuery = true)
    Integer SearchAudioByIdAndDifferentGender(@Param("audioId") int audioId, @Param("gender") char gender, @Param("todo") char todo);

    @Query(value = "Select audioId FROM relaxationtechniqueaudios where audioId=:audioId and auditory != CONCAT('', :auditory , '')", nativeQuery = true)
    Integer SearchAudioByIdAndDifferentAuditory(@Param("audioId") int audioId, @Param("auditory") char auditory);

    @Query(value = "Select preferenceSensorial from relaxationtechniqueaudios where audioId = :audioId", nativeQuery = true)
    String SearchPreferenceSensorialOfAudio(@Param("audioId") int audioId);

    @Query(value = "Select preferenceSensorial from relaxationtechniqueaudios where audioId = :audioId", nativeQuery = true)
    String SearchPreferenceAuditiveOfAudio(@Param("audioId") int audioId);

    @Query(value = "Select url from relaxationtechniqueaudios where audioId = :audioId", nativeQuery = true)
    String SearchUrlOfAudioId(@Param("audioId") int audioId);

    @Query(value = "Select audioId from relaxationtechniqueaudios where url = :url", nativeQuery = true)
    Integer SearchAudioIdByUrl(@Param("url") String url);

    @Query(value = "Select duration from relaxationtechniqueaudios where audioId = :audioId", nativeQuery = true)
    LocalTime SearchDurationByAudioId(@Param("audioId") int audioId);
}
