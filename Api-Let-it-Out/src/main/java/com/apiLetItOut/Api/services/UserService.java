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
    public int SearchUserTAGMethod(String username)
    {
        return userRepository.SearchUsersByUsername(username);
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
}
