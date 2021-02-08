import java.util.ArrayList;
public class Order {
    private int maxItems;
    private ArrayList<Item> items=new ArrayList<Item>();
    private int ID;
    public int getMaxItems() {
        return maxItems;
    }
    public void setMaxItems() {
        this.maxItems++;
    }
    public ArrayList<Item> getItems() {
        return items;
    }
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
}