import java.io.IOException;

/**
 * Interface representing a listable entity.
 */
public interface Listable {
    /**
     * Lists the products.
     * 
     * @throws IOException if an I/O error occurs.
     */
    void listProducts() throws IOException;
}