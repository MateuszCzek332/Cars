public class Airbag {
    private String description;
    private boolean value;

    public Airbag(String description, boolean value) {
        this.description = description;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Airbag{" +
                "description='" + description + '\'' +
                ", value=" + value +
                '}';
    }
}
