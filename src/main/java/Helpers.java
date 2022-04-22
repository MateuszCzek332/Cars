import java.util.ArrayList;
import java.util.Random;

public class Helpers {
    static String[] models = {"Opel", "Ford", "Honda"};
    static String[] imgs = {"img1.jpg", "img2.jpg", "img3.jpg"};
    static int[] vats = {0, 7, 22};

    Car getRandomCar(){
        Random random = new Random();

        String newmodel = this.models[random.nextInt(this.models.length)];
        int newyear = 1950 + random.nextInt(73);
        String randomColor = " rgb(" + random.nextInt(256) + ", " + random.nextInt(256) + ", " + random.nextInt(256) + ")";
        ArrayList<Airbag> newairbags = new ArrayList<>();
        newairbags.add(new Airbag("kierowca", random.nextBoolean()));
        newairbags.add(new Airbag("pasazer", random.nextBoolean()));
        newairbags.add(new Airbag("kanapa", random.nextBoolean()));
        newairbags.add(new Airbag("boczne", random.nextBoolean()));

        String newimg =  this.imgs[random.nextInt(this.imgs.length)];
        int newvat =  this.vats[random.nextInt(this.vats.length)];
        int newcena =  5000 + random.nextInt(30000);
        String newdata = this.getRandomDate();

        return new Car(null, newmodel, newairbags, newdata, newimg, newcena, newvat, newyear, randomColor);
    }

    String getRandomDate(){
        Random random = new Random();
        int year = 2000 + random.nextInt(23);
        int month = random.nextInt(13);
        int day = random.nextInt(30);
        CustomDate newdata =  new CustomDate( year, month, day);
        return newdata.getDate();
    }
}
