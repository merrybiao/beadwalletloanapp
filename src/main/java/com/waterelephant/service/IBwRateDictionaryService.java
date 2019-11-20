package com.waterelephant.service;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwRateDictionary;

@Service
public interface IBwRateDictionaryService {

	BwRateDictionary findBwRateDictionaryByAttr(BwRateDictionary bwRateDictionary);
}
