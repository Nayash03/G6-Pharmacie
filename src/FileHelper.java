import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

public class FileHelper {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Pharmacy lireFichier(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            PharmacyWrapper wrapper = gson.fromJson(reader, PharmacyWrapper.class);
            return wrapper != null ? wrapper.getPharmacie() : null;
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier : " + filePath);
            return null;
        }
    }
}
