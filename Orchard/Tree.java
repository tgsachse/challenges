// Written by Tiger Sachse.
// Part of Orchard.

// A tree class that provides fruit.
class Tree {
    private Fruit type;
    private Integer weight;

    public enum Fruit {
        LIME,
        PEAR,
        MANGO,
        LEMON,
        APPLE,
        BANANA,
        ORANGE,
        TOMATO,
        CHERRY,
        STRAWBERRY,
        WATERMELON
    }

    // Initialize a tree with a fruit type and fruit weight.
    public Tree(Fruit type, int weight) {
        this.type = type;
        this.weight = new Integer(weight);
    }

    // Get the fruit type for this tree.
    public Fruit getType() {
        return type;
    }

    // Get the fruit weight of this tree.
    public Integer getWeight() {
        return weight;
    }
    
    // Return a string representation of this tree.
    @Override
    public String toString() {
        return String.format(
            "Tree of type '%s' with a total weight of %d",
            type.toString(),
            weight
        ); 
    }
}
