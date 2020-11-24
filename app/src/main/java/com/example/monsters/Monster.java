package com.example.monsters;

public class Monster {
    private int id;
    private int power;

    public Monster() {
        id=-1;
        power=-1;
    }

    public Monster(int id,int power)
    {
        this.id = id;
        this.power=power;
    }

    public void setId(int id){
        this.id=id;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getId(){
        return this.id;
    }

    public int getPower(){
        return this.power;
    }
}

