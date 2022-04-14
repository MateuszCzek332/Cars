import com.fasterxml.uuid.Generators;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import spark.Request;
import spark.Response;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

class App {

    static ArrayList<Car> cars = new ArrayList<>();
    public static void main(String[] args) {

        staticFiles.location("/public");
        post("/add", (req, res) ->  addFunction(req, res));
    }

    static String addFunction(Request req, Response res) {
        return req.body();
    }

}

class Car{
    
}
