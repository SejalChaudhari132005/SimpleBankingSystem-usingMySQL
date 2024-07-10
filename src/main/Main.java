package main;

import databasemanagement.DatabaseConnection;
import databasemanagement.DatabaseUtilities;
import model.Account;
import model.Customer;
import model.Transaction;
import vaidation.VaidationUtilities;

import java.util.Scanner;

import static vaidation.VaidationUtilities.validateoperation;
import static vaidation.VaidationUtilities.validatetype;

public class Main {
    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);
        System.out.print("Enter database name:");
        String databasename=sc.nextLine();
        System.out.print("Enter username:");
        String username=sc.nextLine();
        System.out.print("Enter password:");
        String password=sc.nextLine();
        DatabaseConnection database=new DatabaseConnection(databasename,username,password);

        System.out.print("Enter tablename for customers:");
        String customertable=sc.nextLine();
        System.out.print("Enter tablename for accounts:");
        String accounttable=sc.nextLine();
        System.out.print("Enter tablename for transactions:");
        String transactiontable=sc.nextLine();
        DatabaseUtilities utils=new DatabaseUtilities(database.getStatement(),customertable,accounttable,transactiontable);
        utils.CreateCustomerTable();
        utils.CreateAccounttable();
        utils.CreateTransactiontable();
        VaidationUtilities.initialize();

        System.out.println("\n----->Welcome to MyBank<-----");

        int option;
        do{
            System.out.println("\n1.Create a customer.");
            System.out.println("2.Create an account.");
            System.out.println("3.Do transaction.");
            System.out.println("4.Exit");
            System.out.print("Enter your option:");
            option=sc.nextInt();
            sc.nextLine();
            switch (option){
                case 1:
                    System.out.print("Enter name:");
                    String name=sc.nextLine();
                    System.out.print("Enter age:");
                    int age= sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter occupation:");
                    String occupation=sc.nextLine();
                    System.out.print("Enter address:");
                    String address=sc.nextLine();
                    Customer c1=new Customer(name,age,occupation,address);
                    utils.InsertCustomer(c1);
                    break;
                case 2:
                    System.out.print("Enter customer id:");
                    int id=sc.nextInt();
                    sc.nextLine();
                    if(!utils.CustomerIdPresent(id)){
                        break;
                    }
                    System.out.print("Enter type of account[Saving/Current]:");
                    String type=sc.nextLine();
                    if(!validatetype(type)){
                        break;
                    }
                    System.out.print("Enter balance:");
                    int balance= sc.nextInt();
                    Account a1=new Account(id,type,balance);
                    utils.InsertAccount(a1);
                    break;
                case 3:
                    System.out.print("Enter account id:");
                    int account_id=sc.nextInt();
                    sc.nextLine();
                    if(!utils.AccountIdPresent(account_id)){
                        break;
                    }
                    utils.Showbalance(account_id);
                    System.out.print("Enter operation:");
                    String operation=sc.nextLine();
                    if(!validateoperation(operation)){
                        break;
                    }
                    System.out.print("Enter amount:");
                    int amount=sc.nextInt();
                    Transaction t1=new Transaction(account_id,operation,amount);
                    int transid=utils.InsertTransaction(t1);

                    if(operation.equals("Debit")) {
                        if(!utils.ValidateAmount(account_id,transid)){
                            utils.FailedTransaction(transid);
                        }else {
                            utils.Debit(account_id,transid);
                        }
                    }else if (operation.equals("Credit")){
                        utils.Credit(account_id,transid);
                    }
                    utils.Showbalance(account_id);
                    break;
                default:
                    System.out.println("Exited");


            }

        }while(option<4);




    }

}




