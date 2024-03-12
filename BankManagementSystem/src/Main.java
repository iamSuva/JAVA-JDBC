import java.sql.SQLException;
import java.sql.*;
import  java.util.*;
public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/java_banking_system";
    private static final String username = "root";
    private static final String password = "Suvadip@632";
    public static void main(String[] args) throws ClassNotFoundException,SQLException{

        System.out.println("Hello world!");
        try{
             Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner scanner =  new Scanner(System.in);
            User user = new User(connection, scanner);
            Account accounts = new Account(connection, scanner);
            AccountManager accountManager = new AccountManager(connection, scanner);

            String email;
            long account_number;

            while(true)
            {
                System.out.println("+++++++++++++\\\\\\******************/////+++++++++++++++");
                System.out.println("******** WELCOME TO BANKING SYSTEM ***");
                System.out.println("+++++++++++++\\\\\\******************/////+++++++++++++++");
                System.out.println("Enter 1. Register");
                System.out.println("Enter 2. Login");
                System.out.println("Enter 3. Exit");
                System.out.println("Enter your choice: ");
                int choice1 = scanner.nextInt();
                switch (choice1)
                {
                    case 1:
                        user.registeruser();
                        break;
                    case 2:
                        email=user.loginuser();
                        if(email==null)
                        {
                            System.out.println("Incorrect email or password");
                        }else {
                            System.out.println("************************************");
                            System.out.println("successful login user");
                            if(!accounts.accountNo_exist(email))
                            {
                                System.out.println("*******************");
                                System.out.println("Enter 1: Open account");
                                System.out.println("Enter 2 : Exit ");
                                int n=scanner.nextInt();
                                if(n == 1) {
                                    account_number = accounts.open_account(email);
                                    System.out.println("Account Created Successfully");
                                    System.out.println("Your Account Number is: " + account_number);
                                }else{
                                    break;
                                }
                            }
                            //already user account exist
                            account_number=accounts.get_accountno(email);
                            int choice2=0;
                           while(choice2!=5)
                           {
                               System.out.println();
                               System.out.println("1. Debit Money");
                               System.out.println("2. Credit Money");
                               System.out.println("3. Transfer Money");
                               System.out.println("4. Check Balance");
                               System.out.println("5. Log Out");
                               System.out.println("Enter your choice: ");
                               choice2 = scanner.nextInt();
                               switch (choice2) {
                                   case 1:
                                       accountManager.debit_money(account_number);
                                       break;
                                   case 2:
                                       accountManager.credit_balance(account_number);
                                       break;
                                   case 3:
                                       accountManager.transfer_money(account_number);
                                       break;
                                   case 4:
                                       accountManager.get_balance(account_number);
                                       break;
                                   case 5:
                                       break;
                                   default:
                                       System.out.println("Enter Valid Choice!");
                                       break;
                               }
                           }
                        }
                    case 3:
                        System.out.println("**************++++++++++++*************");
                        System.out.println("Thank you for using Banking system");
                        return;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}