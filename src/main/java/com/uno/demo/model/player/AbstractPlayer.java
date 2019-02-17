package com.uno.demo.model.player;

import lombok.Data;

@Data
public abstract class AbstractPlayer {

    private final String name;
    private final int age;

    public AbstractPlayer(String name, int age){
        this.name = name;
        this.age = age;
    }
}
