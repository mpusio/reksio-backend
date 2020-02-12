package com.reksio.restbackend.collection.user;

public enum Token {
    BLUE(1, 15, 5),
    GREEN(2, 30, 10),
    PURPLE(3, 45, 15),
    RED(4, 60, 20);

    Token(int priority, int extraTimeInDays, int price) {

    }
}
