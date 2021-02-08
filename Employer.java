public class Employer{
    private String name;
    private int salary;
    private int allowedMaxTables=0;
    public Employer(String name,int salary){
        this.name=name;
        this.salary=salary;
    }
    public String getName() {
        return name;
    }
    public double getSalary() {
        return salary;
    }
    public int getAllowedMaxTables() {
        return allowedMaxTables;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSalary(int salary) {
        this.salary = salary;
    }
    public void setAllowedMaxTables() {
        this.allowedMaxTables++;
    }
}