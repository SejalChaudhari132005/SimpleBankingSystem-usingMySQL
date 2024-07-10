package model;

public class Transaction {
    private int accountid;
    private String operation;
    private int amount;
    public Transaction(int accountid,String operation,int amount){
        this.accountid=accountid;
        this.operation=operation;
        this.amount=amount;
    }

    public int getAccountid() {
        return this.accountid;
    }

    public String getOperation() {
        return this.operation;
    }

    public int getAmount() {
        return this.amount;
    }


}
