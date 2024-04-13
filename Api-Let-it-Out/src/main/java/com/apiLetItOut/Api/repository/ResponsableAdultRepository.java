package com.apiLetItOut.Api.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.ResponsableAdult;

import jakarta.transaction.Transactional;

@Repository
public interface ResponsableAdultRepository extends CrudRepository <ResponsableAdult, Integer>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO responsableadult " + 
            "(userTAGId, nameResponsable, parentesco) " +
            "VALUES (:userTAGId, :nameResponsable, :parentesco)", nativeQuery = true)
    Integer RegisterNewAdutResponsable(@Param("userTAGId") int userTAGId,
                        @Param("nameResponsable") String nameResponsable,
                        @Param("parentesco") String parentesco);
    
}
