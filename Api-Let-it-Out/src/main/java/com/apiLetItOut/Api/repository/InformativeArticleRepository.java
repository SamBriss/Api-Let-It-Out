package com.apiLetItOut.Api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.InformativeArticles;

@Repository
public interface InformativeArticleRepository extends CrudRepository <InformativeArticles, Integer> {
    @Query(value = "SELECT articleId FROM informativeArticles WHERE name LIKE %:topic%", nativeQuery = true)
    Integer SearchIdOfDocument(@Param("topic") String topic);

    @Query(value = "Select name FROM informativeArticles WHERE  articleId = :articleId", nativeQuery=true)
    String SearchNameOfDocument (@Param("articleId") int articleId);

    @Query(value = "Select url FROM informativeArticles WHERE  articleId = :articleId", nativeQuery=true)
    String SearchUrlOfDocument (@Param("articleId") int articleId);

    @Query(value = "Select topicClassification FROM informativeArticles WHERE  articleId = :articleId", nativeQuery=true)
    String SearchClassificationOfDocument (@Param("articleId") int articleId);

    @Query(value = "Select sinopsis FROM informativeArticles WHERE  articleId = :articleId", nativeQuery=true)
    String SearchSinopsisOfDocument (@Param("articleId") int articleId);

    @Query(value = "Select typeOfDocument FROM informativeArticles WHERE  articleId = :articleId", nativeQuery=true)
    int SearchTypeOfDocument (@Param("articleId") int articleId);
}
