import java.util.*;

public class Catalog implements Iterable<CatalogEntry> {

    private ArrayList<CatalogEntry> entries;

    public Catalog() {
        entries = new ArrayList<>();
    }

    @Override
    public Iterator<CatalogEntry> iterator() {
        return entries.iterator();
    }

    public void addEntry(Satellite satellite1, Satellite satellite2, long distance) {
        entries.add(new CatalogEntry(satellite1, satellite2, distance));
    }


    // TESTING MAIN (will remove)
    public static void main(String[] args) {
        Satellite C1 = new Satellite("C1", Satellite.Type.C, .65);
        Satellite B1 = new Satellite("B1", Satellite.Type.B, .95);
        Satellite B2 = new Satellite("B2", Satellite.Type.B, .88);
        Satellite A1 = new Satellite("A1", Satellite.Type.A, .99);

        Catalog catalog = new Catalog();
        catalog.addEntry(C1, B1, 7);
        catalog.addEntry(C1, B2, 5);
        catalog.addEntry(B1, B2, 15);
        catalog.addEntry(B1, A1, 1000);

        for (CatalogEntry entry : catalog) {
            System.out.println(entry);
        }
    }
}
