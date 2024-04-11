package com.senai.projeto01.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class Data {
    public static LocalDate converterStringParaData(String dataString) throws Exception {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            return LocalDate.parse(dataString, formato);
        } catch (Exception e) {
            log.error("Data invalida.");
            throw new Exception("Data invalida.");
        }
    }

    public static String converterDataParaString(LocalDate data) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formato.format(data);
    }
}
