package com.apiLetItOut.Api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.AttacksMethods;

@Repository
public interface AttackMethodsRepository extends CrudRepository<AttacksMethods, Integer>{
    @Query(value = "select method from attacksmethods where attackMethodId= :attackMethodId", nativeQuery=true)
    String SearchNameMethod(@Param("attackMethodId") int attackMethodId);
}
