import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

/**
 * Utility class for file operations related to Pharmacy data.
 */
public class FileHelper {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Reads a Pharmacy object from a JSON file.
     *
     * @param filePath the path to the JSON file
     * @return the Pharmacy object read from the file, or null if an error occurs
     */
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
