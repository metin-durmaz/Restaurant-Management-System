public class Waiter{
    private String name;
    private int salary;
    private int maxTableServices=0;
    private int settedTables=0;
    public Waiter(String name,int salary){
        this.name=name;
        this.salary=salary;
    }
    public String getName() {
        return name;
    }
    public int getSalary() {
        return salary;
    }
    public int getMaxTableServices() {
        return maxTableServices;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSalary(int salary) {
        this.salary = salary;
    }
    public void setMaxTableServices() {
        this.maxTableServices++;
    }
    public void setMaxTableServices2(){
        this.maxTableServices--;
    }
    public void setSettedTables() {
        this.settedTables ++;
    }
    public int getSettedTables() {
        return settedTables;
    }
}