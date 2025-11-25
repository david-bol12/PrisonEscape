package org.prisongame.items;

public class Cookie extends Item implements Eatable{

    int energy = 10;

    public Cookie() {
        super("Cookie", "A tasty treat");
    }

    @Override
    public int eat() {
        return energy;
    }
}
