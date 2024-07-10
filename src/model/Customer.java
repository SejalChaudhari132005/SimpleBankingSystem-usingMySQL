package model;
public class Customer {
    private String name;
    private int age;
    private String occupation;
    private String address;
    public Customer(String name,int age,String occupation,String address){
        this.name=name;
        this.age=age;
        this.occupation=occupation;
        this.address=address;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public String getAddress() {
        return this.address;
    }
}
