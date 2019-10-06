public class Satellite {

    public static enum Type {
        A(1), B(10), C(100);

        private int value;

        private Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Type type;
    private String serial;
    private double efficiency;

    public Satellite(String serial, Type type, double efficiency) {
        this.type = type;
        this.serial = serial;
        this.efficiency = efficiency;
    }

    public Type getType() {
        return type;
    }

    public String getSerial() {
        return serial;
    }

    public double getEfficiency() {
        return efficiency;
    }
}
