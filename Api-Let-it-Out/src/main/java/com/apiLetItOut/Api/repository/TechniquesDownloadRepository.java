package com.apiLetItOut.Api.repository;

import java.time.LocalDate;

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
}
