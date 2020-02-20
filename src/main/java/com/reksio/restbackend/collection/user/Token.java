package com.reksio.restbackend.collection.user;

public enum Token {
    BLUE(1, 15, 5),
    GREEN(2, 30, 10),
    PURPLE(3, 45, 15),
    RED(4, 60, 20);

    private final int priority;
    private final int extraTimeInDays;
    private final int price;

    Token(int priority, int extraTimeInDays, int price) {
        this.priority = priority;
        this.extraTimeInDays = extraTimeInDays;
        this.price = price;
    }

    public int getPriority() {
        return priority;
    }

    public int getExtraTimeInDays() {
        return extraTimeInDays;
    }

    public int getPrice() {
        return price;
    }
}
