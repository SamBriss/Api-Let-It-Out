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
    @Query(value = "INSERT INTO Directions (street, numExt, numInt, colony) VALUES (:street, :numExt, :numInt, :colony)", nativeQuery=true)
    Integer RegisterNewDirection(@Param("street") String street,
                                @Param("numExt") int numExt, 
                                @Param("numInt") int numInt,
                                @Param("colony") String colony);

    @Query (value= "Select directionId from directions where street=:street AND colony=:colony AND numExt=:numExt AND numInt=:numInt LIMIT 1", nativeQuery=true)
    Integer SearchDirectionId(@Param("street") String street,
    @Param("numExt") int numExt, 
    @Param("numInt") int numInt,
    @Param("colony") String colony);

    @Query(value = "Select street from directions where directionId = :directionId", nativeQuery = true)
    String SearchStreet(@Param("directionId") Integer directionId);

    @Query(value = "Select colony from directions where directionId = :directionId", nativeQuery = true)
    String SearchColony(@Param("directionId") Integer directionId);
    
    @Query(value = "select numExt from directions where directionId = :directionId", nativeQuery = true)
    Integer SearchNumExt(@Param("directionId") Integer directionId);

    @Query(value = "select numInt from directions where directionId = :directionId", nativeQuery = true)
    Integer SearchNumInt(@Param("directionId") Integer directionId);

    @Transactional
    @Modifying
    @Query(value = "Update directions set street=:street, colony=:colony, numExt=:numExt, numInt=:numInt where directionId=:directionId", nativeQuery = true)
    Integer UpdateDirection(@Param("street") String street,
    @Param("numExt") int numExt, 
    @Param("numInt") int numInt,
    @Param("colony") String colony,
    @Param("directionId") int directionId);
}