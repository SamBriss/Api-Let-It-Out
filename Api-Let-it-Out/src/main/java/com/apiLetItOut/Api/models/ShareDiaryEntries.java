package com.apiLetItOut.Api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ShareDiaryEntries")
public class ShareDiaryEntries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ShareDiaryEntriesId;

    @ManyToOne
    private DiaryEntries diaryId;

    @ManyToOne
    private UsersTherapists usersTherapistsId;

    public int getShareDiaryEntriesId() {
        return ShareDiaryEntriesId;
    }

    public void setShareDiaryEntriesId(int shareDiaryEntriesId) {
        ShareDiaryEntriesId = shareDiaryEntriesId;
    }

    public DiaryEntries getDiaryEntries() {
        return diaryId;
    }

    public void setDiaryEntries(DiaryEntries diaryId) {
        this.diaryId = diaryId;
    }

    public UsersTherapists getUsersTherapists() {
        return usersTherapistsId;
    }

    public void setUsersTherapists(UsersTherapists usersTherapistsId) {
        this.usersTherapistsId = usersTherapistsId;
    }  
    
}
