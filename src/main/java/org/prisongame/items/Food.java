package org.prisongame.items;

public class Food extends Item {

    private final int energy;

    public Food(String name, String description, int value, int energy) {
        super(name, null, value);
        super.setDescription(description + "\nGives " + energy + " energy");
        this.energy = energy;
    }

    public int eat() {
        return energy;
    }

    public final static Food cookie = new Food("Cookie", "A tasty treat", 2, 10);
    public final static Food bread = new Food("Bread", "Simple but filling", 4, 25);
    public final static Food burrito = new Food("Burrito", "It looks dodgy...", 6, 40);
}
