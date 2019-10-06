class CatalogEntry {

    public final long distance;
    public final Satellite satellite1;
    public final Satellite satellite2;

    public CatalogEntry(Satellite satellite1, Satellite satellite2, long distance) {
        this.distance = distance;
        this.satellite1 = satellite1;
        this.satellite2 = satellite2;
    }

    public String toString() {
        return String.format("Satellite %s is %d units from satellite %s.",
            satellite1.getSerial(),
            distance,
            satellite2.getSerial()
        );
    }
}
