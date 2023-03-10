package com.javapoke;

class Potion {
    // fields
    String name;
    int quantity;
    int value;

    // constructors
    public Potion(String name, int quantity, int value) {
        this.name = name;
        this.quantity = quantity;
        this.value = value;
    }

    // accessors
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Potion{" +
                "name='" + getName() + '\'' +
                ", quantity=" + getQuantity() +
                ", value=" + getValue() +
                '}';
    }
}