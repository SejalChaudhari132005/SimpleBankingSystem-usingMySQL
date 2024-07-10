package databasemanagement;

import com.mysql.cj.protocol.Resultset;
import model.Account;
import model.Customer;
import model.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseUtilities {

    private Statement statement;
    private String customertable;
    private String accounttable;
    private String transactiontable;

    public DatabaseUtilities(Statement statement, String customertable, String accounttable, String transactiontable) {
        this.statement = statement;
        this.customertable = customertable;
        this.accounttable = accounttable;
        this.transactiontable = transactiontable;
    }

    //CREATING TABLES
    public void CreateCustomerTable() {
        try {
            this.statement.executeUpdate("create table " + this.customertable + "(cus_id int not null auto_increment,name varchar(400),age int not null,occupation varchar(400),address varchar(1000),primary key(cus_id));");
            System.out.println("Customer table created");
        } catch (SQLException e) {
            System.out.println("Customer table already exists");
        }
    }

    public void CreateAccounttable() {
        try {
            this.statement.executeUpdate("create table " + this.accounttable + "(account_id int not null auto_increment,type varchar(500),balance int not null,cus_id int,primary key(account_id),foreign key (cus_id) references " + this.customertable + "(cus_id));");
            System.out.println("Account table created");
        } catch (SQLException e) {
            System.out.println("Account table already exists");
        }
    }

    public void CreateTransactiontable() {
        try {
            this.statement.executeUpdate("create table " + this.transactiontable + "(transaction_id int not null auto_increment primary key,operation varchar(300),amount int not null,account_id int,status varchar(200),foreign key (account_id) references " + this.accounttable + "(account_id));");
            System.out.println("Transaction table created.");
        } catch (SQLException e) {
            System.out.println("Transaction table already exist.");
        }
    }

    //INSERTING DATA INTO TABLE
    public void InsertCustomer(Customer c) {
        try {
            this.statement.executeUpdate("insert into " + this.customertable + "(name,age,occupation,address) values('" + c.getName() + "','" + c.getAge() + "','" + c.getOccupation() + "','" + c.getAddress() +"');");
            System.out.println("Customer details entered successfully");
            ResultSet rs = this.statement.executeQuery("select last_insert_id();");
            int CustomerId = -1;
            if (rs.next()) {
                CustomerId = rs.getInt(1);
            }
            System.out.println("Your Customer id: " + CustomerId);
        } catch (SQLException e) {
            System.out.println("Error");
            throw new RuntimeException(e);

        }
    }

    public void InsertAccount(Account a) {
        try {
            this.statement.executeUpdate("alter table " + this.accounttable + " auto_increment=1001;");
            this.statement.executeUpdate("insert into " + this.accounttable + "(type,balance,cus_id) values('" + a.getAccounttype() + "','" + a.getBalance() + "','" + a.getCusid() + "');");
            ResultSet rs = this.statement.executeQuery("select last_insert_id();");
            System.out.println("Account created succesfully");
            int AccountId = -1;
            if (rs.next()) {
                AccountId = rs.getInt(1);
            }
            System.out.println("Your Account id: " + AccountId);

        } catch (SQLException e) {
            System.out.println("Error");
        }
    }

    public int InsertTransaction(Transaction t) {
        int TransactionId;
        try {
            this.statement.executeUpdate("alter table " + this.transactiontable + " auto_increment=101;");
            this.statement.executeUpdate("insert into " + this.transactiontable + "(operation,amount,account_id,status) values('" + t.getOperation() + "','" + t.getAmount() + "','" + t.getAccountid() + "','successful');");
            System.out.println("Done");
            ResultSet rs = this.statement.executeQuery("select last_insert_id();");
            TransactionId = -1;
            if (rs.next()) {
                TransactionId = rs.getInt(1);
            }
            System.out.println("Your Transaction id: " + TransactionId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return TransactionId;
    }

    public boolean CustomerIdPresent(int id) {
        try {
            ResultSet rs = this.statement.executeQuery("SELECT 1 FROM " + this.customertable + " WHERE cus_id=" + id + ";");
            if (!rs.next()) {
                System.out.println("Invalid Customer id");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean AccountIdPresent(int account_id) {
        try {
            ResultSet rs = this.statement.executeQuery("select 1 from " + this.accounttable + " where account_id=" + account_id + ";");
            if (!rs.next()) {
                System.out.println("Invalid Account id");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    //OPERATIONS
    public void Debit(int account_id,int transid) {
        try {
            this.statement.executeUpdate("update " + this.accounttable + " set balance= balance-(select amount from " + this.transactiontable + " where transaction_id=" + transid +" and account_id="+account_id+" limit 1) where account_id=" + account_id + ";");
        } catch (SQLException e) {
            FailedTransaction(transid);
            throw new RuntimeException(e);
        }
    }

    public void Credit(int account_id,int transid) {
        try {

            this.statement.executeUpdate("update " + this.accounttable + " set balance= balance+(select amount from " + this.transactiontable + " where transaction_id=" + transid +" and account_id="+account_id+" limit 1) where account_id=" + account_id + ";");
        } catch (SQLException e) {
            FailedTransaction(transid);
            throw new RuntimeException(e);
        }
    }
    public void FailedTransaction(int transid) {
        try {
            this.statement.executeUpdate("update " + this.transactiontable + " set status ='failed' where transaction_id=" + transid + ";");
            System.out.println("Your transaction has been failed!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean ValidateAmount(int account_id,int transid){
        boolean flag=true;
        try {
            ResultSet rs=this.statement.executeQuery("select balance from "+this.accounttable+" where account_id="+account_id+";");
            if(rs.next()) {
                int balance = rs.getInt("balance");
                ResultSet rs1=this.statement.executeQuery("select amount from "+this.transactiontable+" where transaction_id="+transid+";");
                if(rs1.next()){
                    int amount= rs1.getInt("amount");
                    if(amount>balance){
                        flag=false;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    public void Showbalance(int account_id){
        try {
            System.out.println("-------------------------------------------------");
            ResultSet rs=this.statement.executeQuery("select name from "+this.customertable+" where cus_id in(select cus_id from "+this.accounttable+" where account_id="+account_id+");");
            if(rs.next()){
                String name=rs.getString("name");
                System.out.println("Name:"+name);
                System.out.println("Account id:"+account_id);
                ResultSet rs1=this.statement.executeQuery("select balance from "+this.accounttable+" where account_id="+account_id+";");
                if (rs1.next()) {
                    int balance = rs1.getInt("balance");
                    System.out.println("Balance: " + balance);
                }
                System.out.println("-------------------------------------------------");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}

