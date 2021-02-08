public class Item{
    private String name;
    private double cost;
    private int amount;
    private int ID;
    private int adet=1;
    public Item(String name,double cost,int amount){
        this.name=name;
        this.cost=cost;
        this.amount=amount;
    }
    public String getName() {
        return name;
    }
    public double getCost() {
        return cost;
    }
    public int getAmount() {
        return amount;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public void setAmount() {
        this.amount--;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public int getAdet() {
        return adet;
    }
}