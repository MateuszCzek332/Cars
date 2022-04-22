public class CustomDate {
    private int day;
    private int month;
    private int year;

    public CustomDate( int year, int month, int day) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getDate() {
        return year +  "/" + month + "/" + day;
    }

    @Override
    public String toString() {
        return "CustomDate{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}