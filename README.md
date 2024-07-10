# SimpleBankingSystem-usingMySQL
This is a 'Simple Banking System' that uses MySQL database using JDBC (Java Database Connectivity).

->DatabaseConnection
 The constructor DatabaseConnection takes parameters databasename, username, and password to establish a connection to a MySQL database

->DatabaseUtilities
The DatabaseUtilities class facilitates interaction with the MySQL database through the Statement object provided during initialization.
It handles table creation, data insertion, validation, and various operations related to customers, accounts, and transactions.

->Customer
The Customer class in your Java program models a customer in a banking system, storing their personal information.

->Account
The Account class represents a model for storing information about a bank account.

->Transaction
The Transaction class models a transaction in a banking system, encapsulating details such as the account ID, type of operation (debit or credit), and the amount involved in the transaction. 

->Main
The Main class manages interactions between users and a banking system stored in a MySQL database.It handles creating customers, accounts, and performing transactions.
Uses database utilities for managing database operations and validation utilities to ensure data integrity.

![image](https://github.com/SejalChaudhari132005/SimpleBankingSystem-usingMySQL/assets/172904171/e50ac9ed-c913-4767-917f-4832b4d52c97)

