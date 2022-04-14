import com.fasterxml.uuid.Generators;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import spark.Request;
import spark.Response;
import java.lang.reflect.Type;
import java.util.ArrayList;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

class App {

    static ArrayList<Car> cars = new ArrayList<>();
    public static void main(String[] args) {
        staticFiles.location("/public");
        post("/add", (req, res) -> addFunction(req, res));
        post("/json", (req, res) -> daneFunction(req, res));
//        post("/delete",(req, res) -> addFunction(req, res));
//        post("/update",(req, res) -> addFunction(req, res));
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

}