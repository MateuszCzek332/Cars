import com.fasterxml.uuid.Generators;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import spark.Request;
import spark.Response;

import java.awt.desktop.SystemEventListener;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

import static spark.Spark.post;
import static spark.Spark.staticFiles;

class App {

    static ArrayList<Car> cars = new ArrayList<>();
    public static void main(String[] args) {
        staticFiles.location("/public");
        post("/add", (req, res) -> addFunction(req, res));
        post("/json", (req, res) -> daneFunction(req, res));
        post("/delete",(req, res) -> delFunction(req, res));
        post("/update",(req, res) -> updateFunction(req, res));
    }

    static String addFunction(Request req, Response res) {

        Gson gson = new Gson();
        Car newcar = gson.fromJson(req.body(), Car.class);
        newcar.setUuid(Generators.randomBasedGenerator().generate());
        if(cars.isEmpty())
            newcar.setId(1);
        else
            newcar.setId(cars.get(cars.size()-1).getId() + 1);

        cars.add(newcar);
        return gson.toJson(newcar);
    }

    static String daneFunction(Request req, Response res) {

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Car>>() {}.getType();
        return gson.toJson(cars, listType);
    }

    static String delFunction(Request req, Response res) {
        for (Car el: cars) {
            if(Objects.equals(req.body().substring(1, 37), el.getUuid().toString())) {
                cars.remove(el);
                break;
            }
        }
        return req.body();
    }

    static String updateFunction(Request req, Response res) {
        Gson gson = new Gson();
        Car updateCar = gson.fromJson(req.body(), Car.class);
        System.out.println(updateCar);
        for (Car el: cars) {
            if( Objects.equals( el.getUuid(), updateCar.getUuid() )){
                System.out.println(el);
                el.setModel(updateCar.getModel());
                el.setYear(updateCar.getYear());
            }
        }
        return req.body();
    }
    
}