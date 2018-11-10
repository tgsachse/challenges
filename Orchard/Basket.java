// Written by Tiger Sachse.
// Part of Orchard.

// A basket class that holds fruit.
public class Basket implements Comparable<Tree> {
    private Tree.Fruit type;
    private Integer grossWeight;

    // Create this basket with no contents.
    public Basket() {
        type = null;
        grossWeight = 0;
    }

    // Initialize this basket with the haul from
    // a specific tree.
    public Basket(Tree tree) {
        type = tree.getType();
        grossWeight = tree.getWeight();
    }

    // Get the gross weight of this basket.
    public Integer getGrossWeight() {
        return grossWeight;
    }

    // Get the fruit type of this basket.
    public Tree.Fruit getType() {
        return type;
    }

    // Collect fruit from a tree. This method will do
    // nothing if the tree's fruit type does not match
    // the type of fruit already in this basket (unless
    // the basket is empty).
    public void collect(Tree tree) {
        if (type == null) {
            type = tree.getType();
        }

        if (type == tree.getType()) {
            grossWeight += tree.getWeight();
        }
    }

    // Dump the contents of this basket and refill from
    // a new tree. This will reset this basket's fruit type
    // and the gross weight of the basket.
    public void dumpAndRefill(Tree tree) {
        type = tree.getType();
        grossWeight = tree.getWeight();
    }

    // Compare the gross weight of this basket to the total
    // weight of fruit in a tree.
    @Override
    public int compareTo(Tree tree) {
        return grossWeight.compareTo(tree.getWeight());
    }

    // Return a string representation of this basket.
    @Override
    public String toString() {
        return String.format(
            "Basket that holds '%s' with a gross weight of %d",
            (type != null) ? type.toString() : "NULL",
            grossWeight
        ); 
    }
}
