package com.apiLetItOut.Api.services;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.RelaxationTechniquesRepository;

@Service
public class RelaxationTechniquesService {
    @Autowired 
    RelaxationTechniquesRepository relaxationTechniquesRepository;

    public List<Integer> allAudiosMinor5MinMethod()
    {
        LocalTime cinco = LocalTime.of(0, 5, 0, 0);
        return relaxationTechniquesRepository.allAudiosMinor5Min(cinco);
    }

    public Integer SearchAudioAccordingToLifeStyleAndIdMethod(int audioId, char auditory)
    {
        return relaxationTechniquesRepository.SearchAudioAccordingToLifeStyleAndId(audioId, auditory);
    }

    public Integer SearchAudioAccordingToAgeAndIdMethod(int audioId, char age)
    {
        return relaxationTechniquesRepository.SearchAudioAccordingToAgeAndId(audioId, age);
    }

    public Integer SearchAudioAccordingToHourAndId(int audioId, char schedule)
    {
        return relaxationTechniquesRepository.SearchAudioAccordingToHourAndId(audioId, schedule);
    }

    public String SearchPreferenceSensorialOfAudioMethod(int audioId)
    {
        return relaxationTechniquesRepository.SearchPreferenceSensorialOfAudio(audioId);
    }

    public String SearchPreferenceAuditiveOfAudioMethod(int audioId)
    {
        return relaxationTechniquesRepository.SearchPreferenceAuditiveOfAudio(audioId);
    }

    public Integer SearchAudioByPreferenceAuditive0AndIdMethod(int audioId, String preferenceAuditive)
    {
        return relaxationTechniquesRepository.SearchAudioByPreferenceAuditive0AndId(audioId, preferenceAuditive);
    }

    public Integer SearchAudioByPreferenceSensorialAndIdMethod(int audioId, String preferenceSensorial)
    {
        return relaxationTechniquesRepository.SearchAudioByPreferenceSensorialAndId(audioId, preferenceSensorial);
    }

    public Integer SearchAudioByIdAndDifferentLevelTAGMethod(int audioId, int levelTAGId)
    {
        return relaxationTechniquesRepository.SearchAudioByIdAndDifferentLevelTAG(audioId, levelTAGId);
    }

    public Integer SearchAudioByIdAndDifferentGenderMethod(int audioId, char gender, char todo)
    {
        return relaxationTechniquesRepository.SearchAudioByIdAndDifferentGender(audioId, gender, todo);
    }

    public Integer SearchAudioByIdAndDifferentAuditoryMethod(int audioId, char auditory)
    {
        return relaxationTechniquesRepository.SearchAudioByIdAndDifferentAuditory(audioId, auditory);
    }

    public String SearchUrlOfAudioIdMethod(int audioId)
    {
        return relaxationTechniquesRepository.SearchUrlOfAudioId(audioId);
    }

    public LocalTime SearchDurationByAudioIdMethod(int audioId)
    {
        return relaxationTechniquesRepository.SearchDurationByAudioId(audioId);
    }
}
