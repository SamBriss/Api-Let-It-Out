package com.apiLetItOut.Api.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.Directions;

import jakarta.transaction.Transactional;

@Repository
public interface DirectionsRepository extends CrudRepository <Directions, Integer>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Directions (street, numExt, numInt, colony) VALUES (:street, :numExt, :numInt, :colony) LIMIT 1", nativeQuery=true)
    Integer RegisterNewDirection(@Param("street") String street,
                                @Param("numExt") int numExt, 
                                @Param("numInt") int numInt,
                                @Param("colony") String colony);
}