// Written by Tiger Sachse.
// Part of OrchardStroll.

// A tree class that provides fruit.
class Tree {
    private Fruit fruitType;
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
    public Tree(Fruit fruitType, int weight) {
        this.fruitType = fruitType;
        this.weight = new Integer(weight);
    }

    // Get the fruit type for this tree.
    public Fruit getType() {
        return fruitType;
    }

    // Get the fruit weight of this tree.
    public int getWeight() {
        return weight;
    }
    
    // Return a string representation of this tree.
    @Override
    public String toString() {
        return String.format(
            "Tree of type '%s' with a total weight of %d",
            fruitType.toString(),
            weight
        ); 
    }
}
