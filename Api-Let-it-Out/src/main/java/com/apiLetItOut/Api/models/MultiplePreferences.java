package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "MultiplePreferences")
public class MultiplePreferences {
    public int getMultiplePreferenceId() {
        return multiplePreferenceId;
    }

    public void setMultiplePreferenceId(int multiplePreferenceId) {
        this.multiplePreferenceId = multiplePreferenceId;
    }

    public PreferencesTAGUser getPreference() {
        return preference;
    }

    public void setPreference(PreferencesTAGUser preference) {
        this.preference = preference;
    }

    public RelaxationTechniqueAudios getAudio() {
        return audio;
    }

    public void setAudio(RelaxationTechniqueAudios audio) {
        this.audio = audio;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int multiplePreferenceId;

    @ManyToOne
    private PreferencesTAGUser preference;

    @ManyToOne
    private RelaxationTechniqueAudios audio;

    // Getters and setters
}
