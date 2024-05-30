package com.apiLetItOut.Api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.DictionaryWords;

import jakarta.transaction.Transactional;

@Repository
public interface DictionaryWordsRepository extends CrudRepository <DictionaryWords, Integer>{
    @Query(value = "SELECT COUNT(*) FROM dictionarywords WHERE categoryId = :categoryId AND word = :word", nativeQuery = true)
    Integer countByCategoryIdAndWord(int categoryId, String word);

    @Query(value = "SELECT dictionaryWordId FROM dictionarywords WHERE categoryId = :categoryId AND :word LIKE CONCAT('%', word, '%') LIMIT 1", nativeQuery = true)
    Integer findWordIdByCategoryAndWord(int categoryId, String word);

    @Query(value = "SELECT dictionaryWordId FROM dictionarywords WHERE categoryId = 10 AND :word LIKE CONCAT(word, '%') LIMIT 1", nativeQuery = true)
    Integer findWordIdByHour(String word);

    @Query(value = "SELECT dw.dictionaryWordId FROM DictionaryWords dw WHERE dw.categoryId IN (:categoryIds) AND :word LIKE CONCAT('%', word, '%')", nativeQuery = true)
    Integer findWordIdByCategoryAndWordInEmotionalCategories(@Param("word") String word, 
                                                            @Param("categoryIds") List<Integer> categoryIds);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO dictionarywords (word, categoryId) VALUES (:word, :categoryId)", nativeQuery = true)
    void saveNewWord(@Param("word") String word, 
                    @Param("categoryId") int categoryId);
}
