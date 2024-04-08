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

    public int SearchAudioAccordingToLifeStyleAndIdMethod(int audioId, char auditory)
    {
        return relaxationTechniquesRepository.SearchAudioAccordingToLifeStyleAndId(audioId, auditory);
    }

    public int SearchAudioAccordingToAgeAndIdMethod(int audioId, char age)
    {
        return relaxationTechniquesRepository.SearchAudioAccordingToAgeAndId(audioId, age);
    }

    public int SearchAudioAccordingToHourAndId(int audioId, char schedule)
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

    public int SearchAudioByPreferenceAuditive0AndIdMethod(int audioId, String preferenceAuditive)
    {
        return relaxationTechniquesRepository.SearchAudioByPreferenceAuditive0AndId(audioId, preferenceAuditive);
    }

    public int SearchAudioByPreferenceSensorialAndIdMethod(int audioId, String preferenceSensorial)
    {
        return relaxationTechniquesRepository.SearchAudioByPreferenceSensorialAndId(audioId, preferenceSensorial);
    }

    public int SearchAudioByIdAndDifferentLevelTAGMethod(int audioId, int levelTAGId)
    {
        return relaxationTechniquesRepository.SearchAudioByIdAndDifferentLevelTAG(audioId, levelTAGId);
    }

    public int SearchAudioByIdAndDifferentGenderMethod(int audioId, char gender, char todo)
    {
        return relaxationTechniquesRepository.SearchAudioByIdAndDifferentGender(audioId, gender, todo);
    }

    public int SearchAudioByIdAndDifferentAuditoryMethod(int audioId, char auditory)
    {
        return relaxationTechniquesRepository.SearchAudioByIdAndDifferentAuditory(audioId, auditory);
    }

    public String SearchUrlOfAudioIdMethod(int audioId)
    {
        return relaxationTechniquesRepository.SearchUrlOfAudioId(audioId);
    }
}
