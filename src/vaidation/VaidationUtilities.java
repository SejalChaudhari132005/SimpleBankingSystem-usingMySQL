package vaidation;

import java.sql.Statement;
import java.util.ArrayList;

public class VaidationUtilities {

    private Statement statement;
    static ArrayList<String> typelist=new ArrayList<>();
    static ArrayList<String> operationlist=new ArrayList<>();

    public static void initialize(){
        typelist.add("Saving");
        typelist.add("Current");

        operationlist.add("Debit");
        operationlist.add("Credit");
    }

    public static boolean validatetype(String type){
         return typelist.contains(type);
    }

    public static boolean validateoperation(String operation){
        return operationlist.contains(operation);
    }





}
