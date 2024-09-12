package com.example.Utils;


import java.util.Random;

public class Utils {
    


    public static String generateISBN() {
        Random random = new Random();
        StringBuilder isbn = new StringBuilder();

        for (int i = 0; i < 13; i++) {
            isbn.append(random.nextInt(10)); 
        }

        return isbn.toString();
    }

    public static String generateNumero() {
        Random random = new Random();
        StringBuilder isbn = new StringBuilder();

        for (int i = 0; i < 13; i++) {
            isbn.append(random.nextInt(10)); 
        }

        return isbn.toString();
    }
}
