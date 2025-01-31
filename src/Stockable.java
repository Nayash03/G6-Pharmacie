import java.io.IOException;

/**
 * Interface representing a stockable item.
 */
public interface Stockable {
    /**
     * Adds a product to the stock.
     * 
     * @throws IOException if an I/O error occurs
     */
    void addProduct() throws IOException;
}