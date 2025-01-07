/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.all2you.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author mstevz
 */
@Converter
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDate, String>{

    public static final String PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter patternFormater = DateTimeFormatter.ofPattern(LocalDateTimeAttributeConverter.PATTERN);
    
    
    
    
    @Override
    public String convertToDatabaseColumn(LocalDate x) {
        String dateValue = null;
        
        if (x != null){
            dateValue = x.format(patternFormater);
        }
        
        return dateValue;
    }

    @Override
    public LocalDate convertToEntityAttribute(String y) {
        LocalDate date = null;
        
        if(y != "" || y != null){
            try{
                date = LocalDate.parse(y, patternFormater);
            }
            catch(Exception e){
                date = null;
            }
        }
        
        return date;
    }
    
}
