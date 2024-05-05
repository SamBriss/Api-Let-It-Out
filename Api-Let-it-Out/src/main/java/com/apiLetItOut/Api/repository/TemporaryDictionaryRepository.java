package com.apiLetItOut.Api.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.TemporaryDictionary;

import jakarta.transaction.Transactional;

@Repository
public interface TemporaryDictionaryRepository extends CrudRepository<TemporaryDictionary, Integer>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO temporarydictionary (word, categoryId, repetitions)VALUES (:word, :categoryId, :repetitions)", nativeQuery = true)
    Integer RegisterNewTemporaryWord(@Param("word") String word,
                        @Param("categoryId") int categoryId,
                        @Param("repetitions") int repetitions);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM temporarydictionary WHERE temporaryDictionaryId = :temporaryDictionaryId", nativeQuery = true)
    Integer DeleteTemporaryWord(@Param("temporaryDictionaryId") int temporaryDictionaryId);

    @Query(value = "SELECT temporaryDictionaryId FROM temporarydictionary WHERE word = :word AND categoryId = :categoryId", nativeQuery = true)
    Integer SelectTemporaryId(@Param("word") String word,
                        @Param("categoryId") int categoryId);
}
