package com.apiLetItOut.Api.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.Documents;

import jakarta.transaction.Transactional;

@Repository
public interface DocumentsRepository extends CrudRepository<Documents, Integer>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO documents " + 
    "(activityTId, referenceURL) VALUES (:activityTId, :referenceURL)", nativeQuery = true)
    Integer RegisterNewActvityDocument(@Param("activityTId") int activityTId,
                                    @Param("referenceURL") String referenceURL);
}
