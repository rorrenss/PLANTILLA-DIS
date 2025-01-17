package es.ufv.inputoutput;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import es.ufv.model.Turismo;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class InputOutput {

    Gson gson = new Gson();
    public ArrayList<Turismo> leerTurismo(String path){
        try(FileReader reader = new FileReader(path)){
            Gson gson = new Gson();
            return gson.fromJson(reader, new TypeToken<ArrayList<Turismo>>(){}.getType());
        }catch (JsonIOException | JsonSyntaxException | IOException e ){
            e.printStackTrace();
            return null;
        }

    }
    public ArrayList<Turismo> escribirTurismo(String path, ArrayList<Turismo> turismos){
        try(FileWriter writer = new FileWriter(path)){
            gson.toJson(turismos, writer);
            return turismos;
        }catch (JsonIOException | IOException e ){
            e.printStackTrace();
            return null;
        }
    }
}

