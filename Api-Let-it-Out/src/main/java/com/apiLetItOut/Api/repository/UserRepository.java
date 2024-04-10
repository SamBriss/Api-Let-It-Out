
package com.apiLetItOut.Api.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiLetItOut.Api.models.Users;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer>{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Users (username, password, email, name, lastnamep, lastnamem, tel, age, gender, token)VALUES (:username, :password, :email, :name, :lastNameP, :lastNameM, :tel, :age, :gender, :token)", nativeQuery = true)
    Integer RegisterNewUser(@Param("username") String username,
                        @Param("password") String password,
                        @Param("email") String email,
                        @Param("name") String name,
                        @Param("lastNameP") String lastNameP,
                        @Param("lastNameM") String lastNameM,
                        @Param("tel") String tel,
                        @Param("age") int age,
                        @Param("gender") String gender,
                        @Param("token") String token);

    @Query(value= "Select userId FROM Users WHERE username=:username AND email=:email", nativeQuery = true)
    Integer SearchUsers (@Param("username") String username,
                        @Param("email") String email);
    @Query(value= "SELECT userId FROM users WHERE username=:username AND password=:password", nativeQuery = true)
    Integer LogInUserByUsername (@Param("username") String username,
                        @Param("password") String password);

    @Query(value= "Select userId FROM users WHERE email=:email AND password=:password", nativeQuery = true)
    Integer LogInUserByEmail ( @Param("email") String email,
                        @Param("password") String password);

    @Query(value= "Select userId FROM Users WHERE username=:username", nativeQuery = true)
    Integer SearchUsersByUsername (@Param("username") String username);
    
    @Query(value= "Select userId FROM Users WHERE email=:email", nativeQuery = true)
    Integer SearchUsersByEmail (@Param("email") String email);

    @Query(value= "Select COUNT(*) FROM Users WHERE username=:username OR email=:email", nativeQuery = true)
    int SearchUsersEmail (@Param("username") String username,
                        @Param("email") String email);
                    
    @Query(value= "SELECT u.age, u.name, u.lastnameP FROM Users u WHERE u.username=:username OR u.email=:email")
    java.util.List<Object[]> findInfoForToken(@Param("username") String username, @Param("email") String email);
                                  
    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.token = :tokenValue WHERE u.username = :username OR u.email = :email")
    Integer updateToken(@Param("tokenValue") String tokenValue, @Param("username") String username, @Param("email") String email);

    @Query(value= "Select username FROM Users WHERE userId=:userId", nativeQuery = true)
    String SearchUsernameById (@Param("userId") int userId);

    @Query(value = "Select name from Users where userId = :userId", nativeQuery = true)
    String SearchUserName (@Param("userId") int userId);

    @Query(value = "Select lastNameP from Users where userId = :userId", nativeQuery = true)
    String SearchUserLP (@Param("userId") int userId);

    @Query(value = "Select lastNameM from Users where userId = :userId", nativeQuery = true)
    String SearchUserLM (@Param("userId") int userId);

    @Query(value = "Select age from Users where userId = :userId", nativeQuery = true)
    Integer SearchUserAge (@Param("userId") int userId);

    @Query(value = "Select tel from Users where userId = :userId", nativeQuery = true)
    String SearchUserTel (@Param("userId") int userId);

    @Query(value = "Select gender from Users where userId = :userId", nativeQuery = true)
    String SearchUserGender (@Param("userId") int userId);

    @Query(value = "Select password from Users where userId = :userId", nativeQuery = true)
    String SearchUserPassword (@Param("userId") int userId);

    @Transactional
    @Modifying
    @Query(value = "Update Users set name =:name, lastNameP = :lastNameP, lastNameM = :lastNameM, age = :age, tel =:tel, gender =:gender, password = :password where userId=:userId", nativeQuery = true)
    Integer UpdateUsers(@Param("userId") int userId, 
                        @Param("password") String password,
                        @Param("name") String name,
                        @Param("lastNameP") String lastNameP,
                        @Param("lastNameM") String lastNameM,
                        @Param("tel") String tel,
                        @Param("age") int age,
                        @Param("gender") String gender);

    @Query (value = "Select email from Users where username =:username", nativeQuery = true)
    String SearchEmailByUsername(@Param("username") String username);
    
    @Query (value = "Select username from Users where email =:email", nativeQuery = true)
    String SearchUsernameByEmail(@Param("email") String email);

    @Query (value = "Select u.userId from Users u INNER JOIN userstherapists t ON u.userId=t.userId WHERE t.userTherapistId=:userTherapistId", nativeQuery = true)
    Integer FindUserIdByUserTherapistId(@Param("userTherapistId") int userTherapistId);
}
