package com.apiLetItOut.Api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.FrecuencyGraphics;

@Repository
public interface frecuencyGraphicsRepository extends CrudRepository <FrecuencyGraphics, Integer>{
    // @Query(value= "SELECT DATE(seq.d) AS day, IFNULL(f.count, 0) AS frequency " +
    // "FROM (SELECT CURDATE() - INTERVAL (a.a) DAY AS d FROM (SELECT 0 AS a UNION ALL " + 
    // "SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 AS a" +
    // ")AS seq " +
    // "LEFT JOIN frecuencygraphics AS f ON DATE(seq.d) = DATE(f.date) AND userTAGId = :userTAGId " +
    // "WHERE seq.d BETWEEN DATE_SUB(CURDATE(), INTERVAL 6 DAY) AND CURDATE() ORDER BY seq.d ASC", nativeQuery = true)
    // String SelectWeek (@Param("userTAGId") int userTAGId);
    // @Query(value= "SELECT (WEEK(date, 1) - WEEK(DATE_SUB(date, INTERVAL DAYOFMONTH(date) - 1 DAY), 1) + 1) AS week_number, " +
    // "IFNULL(SUM(count), 0) AS frequency FROM frecuencygraphics WHERE userTAGId =:userTAGId' AND YEAR(date) = YEAR(CURDATE()) " + 
    // "AND MONTH(date) = MONTH(CURDATE()) GROUP BY week_number ORDER BY week_number ASC", nativeQuery = true)
    // String SelectMonth (@Param("userTAGId") int userTAGId);
    // @Query(value= "SELECT DATE(date) AS month, IFNULL(SUM(count), 0) AS frequency FROM frecuencygraphics " +
    // "WHERE userTAGId = :userTAGId AND date BETWEEN DATE_SUB(CURDATE(), INTERVAL 5 MONTH) AND CURDATE() " +
    // "GROUP BY YEAR(date), MONTH(date) ORDER BY date ASC", nativeQuery = true)
    // String SelectSixMonths (@Param("userTAGId") int userTAGId);
    // @Query(value= "SELECT DATE(date) AS month, IFNULL(SUM(count), 0) AS frequency FROM frecuencygraphics " +
    // "WHERE userTAGId = :userTAGId AND YEAR(date) BETWEEN YEAR(DATE_SUB(CURDATE(), INTERVAL 11 YEAR)) AND YEAR(CURDATE()) " +
    // "GROUP BY YEAR(date), MONTH(date) ORDER BY date ASC", nativeQuery = true)
    // String SelectYear (@Param("userTAGId") int userTAGId);
}
