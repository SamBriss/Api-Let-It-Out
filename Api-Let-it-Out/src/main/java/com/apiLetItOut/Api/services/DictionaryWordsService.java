package com.apiLetItOut.Api.services;

import java.util.List;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiLetItOut.Api.repository.DictionaryWordsRepository;

@Service
public class DictionaryWordsService {
    @Autowired
    DictionaryWordsRepository dictionaryWordsRepository;

    public Integer countByCategoryIdAndWord(int categoryId, String word) {
        return dictionaryWordsRepository.countByCategoryIdAndWord(categoryId, word);
    }

    public Integer findWordIdByCategoryAndWord(int categoryId, String word) {
        return dictionaryWordsRepository.findWordIdByCategoryAndWord(categoryId, word);
    } //findWordIdByHour

    public Integer findWordIdByHourMethod(String word) {
        return dictionaryWordsRepository.findWordIdByHour(word);
    } 

    public Integer findWordIdByCategoryAndWordInEmotionalCategories(String word) {
        List<Integer> emotionalCategoryIds = Arrays.asList(2, 3); // IDs de las categorías emocionales
        return dictionaryWordsRepository.findWordIdByCategoryAndWordInEmotionalCategories(word, emotionalCategoryIds);
    }

    public void registerNewWord(String word, int categoryId) {
        dictionaryWordsRepository.saveNewWord(word, categoryId);
    }

}
