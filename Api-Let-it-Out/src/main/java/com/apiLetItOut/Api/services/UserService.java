package com.apiLetItOut.Api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public int SearchUser(String username, String email)
    {
        return userRepository.SearchUsers(username, email);
    }
    public int SearchUserByUsernameMethod(String username, String password)
    {
        return userRepository.LogInUserByUsername(username, password);
    }
    public int SearchUserByEmailMethod(String email, String password)
    {
        return userRepository.LogInUserByEmail(email, password);
    }
    public Integer SearchUserTAGMethod(String username)
    {
        return userRepository.SearchUsersByUsername(username);
    }
    public Integer SearchUsersByEmailMethod(String email)
    {
        return userRepository.SearchUsersByEmail(email);
    }
    public int RegisterNewUserMethod(String username, String password, String email, String name, String lastNameP, String lastNameM, String tel, int age, String gender, String token)
    {
        return userRepository.RegisterNewUser(username, password, email, name, lastNameP, lastNameM, tel, age, gender, token);
    }
    public int SearchUsersEmailMethod(String username, String email)
    {
        return userRepository.SearchUsersEmail(username, email);
    }
    public java.util.List<Object[]> findInfoForTokenMethod(String username, String email) {
        return userRepository.findInfoForToken(username, email);
    }
    public Integer updateTokenMethod(String tokenValue, String username, String email)
    {
        return userRepository.updateToken(tokenValue, username, email);
    }
    public String SearchUsernameByIdMethod(int userId)
    {
        return userRepository.SearchUsernameById(userId);
    }
    public String SearchNameMethod(int userId)
    {
        return userRepository.SearchUserName(userId);
    }
    public String SearchUserLPMethod(int userId)
    {
        return userRepository.SearchUserLP(userId);
    }
    public String SearchUserLMMethod(int userId)
    {
        return userRepository.SearchUserLM(userId);
    }
    public Integer SearchUserAgeMethod(int userId)
    {
        return userRepository.SearchUserAge(userId);
    }
    public String SearchUserTelMethod(int userId)
    {
        return userRepository.SearchUserTel(userId);
    }
    public String SearchUserGenderMethod(int userId)
    {
        return userRepository.SearchUserGender(userId);
    }
    public String SearchUserPasswordMethod(int userId)
    {
        String passwordEncrypt = userRepository.SearchUserPassword(userId);
        return passwordEncrypt;
    }
    public Integer UpdateUsersMethod (int userId, String name, String lastNameP, String lastNameM, int age, String gender, String tel, String password )
    {
        return userRepository.UpdateUsers(userId, password, name, lastNameP, lastNameM, tel, age, gender);
    }
    public String SearchEmailByUsernameMethod(String username)
    {
        return userRepository.SearchEmailByUsername(username);
    }
    public String SearchUsernameByEmailMethod(String email)
    {
        return userRepository.SearchUsernameByEmail(email);
    }
    public Integer SearchUsersByTelMethod(String tel)
    {
        return userRepository.SearchUsersByTel(tel);
    }
    public Integer UpdatePasswordMethod(String tel, String password)
    {
        return userRepository.UpdatePassword(password, tel);
    }
    public Integer SelectAlreadyhavetoken(){
        return userRepository.SelectAlreadyhavetoken();
    }      
}
