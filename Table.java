public class Table {
    private int ID;
    private int capacity;
    private int state=0;
    private String employerName;
    private String waiterName;
    private int maxOrders=0;
    public Table(int ID,String emloyerName,int capacity){
        this.ID=ID;
        this.employerName=emloyerName;
        this.capacity=capacity;
    }
    public int getID() {
        return ID;
    }
    public String getEmployerName() {
        return employerName;
    }
    public String getWaiterName() {
        return waiterName;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getState() {
        return state;
    }
    public int getMaxOrders() {
        return maxOrders;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setMaxOrders() {
        this.maxOrders++;
    }
    public void setState(int state) {
        this.state = state;
    }
    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }
    public void setMaxOrders(int a) {
        this.maxOrders=a;
    }
}