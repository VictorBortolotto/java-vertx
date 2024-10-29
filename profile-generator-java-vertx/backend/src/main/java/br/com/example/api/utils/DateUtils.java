package br.com.example.api.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
 
  public static LocalDateTime parseStringToLocalDateTime(String date) {
    return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
  }
  
}
