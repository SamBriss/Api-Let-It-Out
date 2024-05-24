package com.apiLetItOut.Api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.apiLetItOut.Api.models.TechniquesDownload;

import jakarta.transaction.Transactional;

public interface TechniquesDownloadRepository extends CrudRepository<TechniquesDownload, Integer>{
    @Transactional
    @Modifying
    @Query(value = "delete from techniquesDownload where userTAGId=:userTAGId", nativeQuery=true)
    Integer deleteDownloads(@Param("userTAGId") int userTAGId);

    @Transactional
    @Modifying
    @Query(value = "insert into techniquesDownload (dateDownload, userTAGId, audioId, complete) VALUES (:dateDownload, :userTAGId, :audioId, :completed)", nativeQuery=true)
    Integer InsertDownloads(@Param("dateDownload") LocalDate dateDownload,
                            @Param("userTAGId") int userTAGId,
                            @Param("audioId") int audioId,
                            @Param("completed") int completed);

    @Transactional
    @Modifying
    @Query(value = "update techniquesDownload set complete = 1 where userTAGId=:userTAGId and dateDownload = :dateDownload", nativeQuery = true)
    Integer CheckComplete(@Param("dateDownload") LocalDate dateDownload,
                            @Param("userTAGId") int userTAGId);

    @Query(value = "select audioId from techniquesdownload where userTAGId:userTAGId and dateDownload = :dateDownload", nativeQuery=true)
    List<Integer> SearchAudioIdByDate(@Param("dateDownload") LocalDate dateDownload,
                            @Param("userTAGId") int userTAGId);

    @Transactional
    @Modifying
    @Query(value = "update techniquesDownload set complete = 1 where userTAGId=:userTAGId and dateDownload = :dateDownload and audioId=:audioId", nativeQuery = true)
    boolean CheckCompleteAudioId(@Param("dateDownload") LocalDate dateDownload,
                            @Param("userTAGId") int userTAGId,
                            @Param("audioId") int audioId);
    
    @Query(value = "select r.url from techniquesDownload t JOIN relaxationTechniqueAudios r ON t.audioId=r.audioId "+
            " where t.userTAGId=:userTAGId and complete=1 ORDER BY dateDownload DESC LIMIT 15;", nativeQuery = true)
    List<String> SearchAudiosDownloaded(@Param("userTAGId") int userTAGId);

    @Query(value = "select r.url from techniquesDownload t JOIN relaxationTechniqueAudios r ON t.audioId=r.audioId "+
            " where t.userTAGId=:userTAGId and dateDownload=:dateDownload LIMIT 15;", nativeQuery = true)
    List<String> SearchAudiosToDownload(@Param("userTAGId") int userTAGId,
                @Param("dateDownload") LocalDate dateDownload);
}
