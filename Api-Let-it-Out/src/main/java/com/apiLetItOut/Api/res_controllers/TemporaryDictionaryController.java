package com.apiLetItOut.Api.res_controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiLetItOut.Api.services.DictionaryWordsService;
import com.apiLetItOut.Api.services.TemporaryDictionaryService;

@RestController
@RequestMapping("api")
public class TemporaryDictionaryController {
    @Autowired
    TemporaryDictionaryService temporaryDictionaryService;

    @Autowired
    DictionaryWordsService dictionaryWordsService;
    
}
