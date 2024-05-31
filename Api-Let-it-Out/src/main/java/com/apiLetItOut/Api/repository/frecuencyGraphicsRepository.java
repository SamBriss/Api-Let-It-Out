package com.apiLetItOut.Api.repository;

import java.util.List;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.FrecuencyGraphics;

@Repository
public interface FrecuencyGraphicsRepository extends CrudRepository <FrecuencyGraphics, Integer>{
    @Query(value = "SELECT DATE(CURDATE() - INTERVAL a.a DAY) AS day, IFNULL(f.count, 0) AS frequency " +
            "FROM (SELECT 0 AS a UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6) " +
            "AS a LEFT JOIN frecuencygraphics AS f ON DATE(CURDATE() - INTERVAL a.a DAY) = DATE(f.date) AND userTAGId = :userTAGId " +
            "WHERE CURDATE() - INTERVAL a.a DAY BETWEEN DATE_SUB(CURDATE(), INTERVAL 6 DAY) AND CURDATE() " +
            "ORDER BY day ASC", nativeQuery = true)
    List<Object[]> SelectWeek(@Param("userTAGId") int userTAGId);
    @Query(value= "SELECT weeks.week_number, IFNULL(SUM(f.count), 0) AS frequency " +
    "FROM ( SELECT 1 AS week_number UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 ) " +
    "AS weeks LEFT JOIN ( SELECT (WEEK(date, 1) - WEEK(DATE_SUB(date, INTERVAL DAYOFMONTH(date) - 1 DAY), 1) + 1) " +
    "AS week_number, IFNULL(count, 0) AS count FROM frecuencygraphics WHERE userTAGId = :userTAGId AND YEAR(date) = YEAR(CURDATE()) " +
    "AND MONTH(date) = MONTH(CURDATE()) ) AS f ON weeks.week_number = f.week_number " +
    "GROUP BY weeks.week_number ORDER BY weeks.week_number ASC", nativeQuery = true)
    List<Object[]> SelectMonth (@Param("userTAGId") int userTAGId);
    @Query(value= "SELECT CONCAT(MONTHNAME(date), ' ', YEAR(date)) AS month, IFNULL(SUM(count), 0) " +
    "AS frequency FROM frecuencygraphics WHERE userTAGId = :userTAGId AND date BETWEEN DATE_SUB(CURDATE(), INTERVAL 5 MONTH) " +
    "AND CURDATE() GROUP BY YEAR(date), MONTH(date) ORDER BY date ASC;", nativeQuery = true)
    List<Object[]> SelectSixMonths (@Param("userTAGId") int userTAGId);
    @Query(value= "SELECT CONCAT(MONTHNAME(date), ' ', YEAR(date)) AS month, IFNULL(SUM(count), 0) AS frequency " + 
    "FROM frecuencygraphics WHERE userTAGId = :userTAGId AND (YEAR(date) BETWEEN YEAR(DATE_SUB(CURDATE(), INTERVAL 11 YEAR)) " + 
    "AND YEAR(CURDATE()) - 1 OR (YEAR(date) = YEAR(CURDATE()) AND MONTH(date) <= MONTH(CURDATE()))) " + 
    "GROUP BY YEAR(date), MONTH(date) ORDER BY YEAR(date) ASC, MONTH(date) ASC", nativeQuery = true)
    List<Object[]> SelectYear (@Param("userTAGId") int userTAGId);
    @Query(value = "SELECT CASE WHEN frecuencia_actual = frecuencia_anterior THEN 0 " +
    "WHEN (frecuencia_actual - frecuencia_anterior) > umbral_aumento THEN 2 " +
    "WHEN (frecuencia_actual - frecuencia_anterior) < umbral_disminucion THEN 1 ELSE 0 " +
    "END AS resultado FROM (SELECT " +
        "(SELECT SUM(count) FROM frecuencygraphics WHERE userTAGId = :userTAGId AND date BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 6 MONTH) AND CURRENT_DATE()) AS frecuencia_actual, " +
        "(SELECT SUM(count) FROM frecuencygraphics WHERE userTAGId = :userTAGId AND date BETWEEN DATE_SUB(DATE_SUB(CURRENT_DATE(), INTERVAL 6 MONTH), INTERVAL 6 MONTH) AND DATE_SUB(CURRENT_DATE(), INTERVAL 6 MONTH)) AS frecuencia_anterior, " +
        "(SELECT SUM(count) FROM frecuencygraphics WHERE userTAGId = :userTAGId AND date BETWEEN DATE_SUB(DATE_SUB(CURRENT_DATE(), INTERVAL 6 MONTH), INTERVAL 6 MONTH) AND DATE_SUB(CURRENT_DATE(), INTERVAL 6 MONTH)) * 0.5 AS umbral_aumento, " +
        "(SELECT SUM(count) FROM frecuencygraphics WHERE userTAGId = :userTAGId AND date BETWEEN DATE_SUB(DATE_SUB(CURRENT_DATE(), INTERVAL 6 MONTH), INTERVAL 6 MONTH) AND DATE_SUB(CURRENT_DATE(), INTERVAL 6 MONTH)) * 0.5 AS umbral_disminucion " +
    ") AS subconsulta", nativeQuery = true)
    String increaseOrDecrease(@Param("userTAGId") int userTAGId);

    @Query(value = "SELECT count FROM frecuencygraphics WHERE userTAGId = :userTAGId AND date = :date LIMIT 1", nativeQuery = true)
    Integer findCountByUserTAGIdAndDate(@Param("userTAGId") int userTAGId, @Param("date") LocalDate date);

    @Modifying
    @Query(value = "UPDATE frecuencygraphics SET count = count + 1 WHERE userTAGId = :userTAGId AND date = :date", nativeQuery = true)
    void updateCount(@Param("userTAGId") int userTAGId, @Param("date") LocalDate date);

    @Modifying
    @Query(value = "INSERT INTO frecuencygraphics (userTAGId, date, count) VALUES (:userTAGId, :date, 1)", nativeQuery = true)
    void insertNewRecord(@Param("userTAGId") int userTAGId, @Param("date") LocalDate date);
}
