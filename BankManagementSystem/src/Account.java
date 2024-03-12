import java.sql.*;
import java.util.Scanner;

public class Account {
    private Connection connection;
    private Scanner scanner;
    public Account(Connection connection,Scanner scanner)
    {
        this.scanner=scanner;
        this.connection=connection;
    }
    public long open_account(String email)
    {
        if(!accountNo_exist(email)) {
            String open_account_query = "INSERT INTO Accounts(acc_no, fullname, email, balance, mpin) VALUES(?, ?, ?, ?, ?)";
            scanner.nextLine();
            System.out.print("Enter Full Name: ");
            String full_name = scanner.nextLine();
            System.out.print("Enter Initial Amount: ");
            double balance = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Enter Security Pin: ");
            String security_pin = scanner.nextLine();
            try {
                long account_number = generate_new_accno();
                PreparedStatement preparedStatement = connection.prepareStatement(open_account_query);
                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, full_name);
                preparedStatement.setString(3, email);
                preparedStatement.setDouble(4, balance);
                preparedStatement.setString(5, security_pin);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return account_number;
                } else {
                    throw new RuntimeException("Account Creation failed!!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       throw new RuntimeException("Account Already Exist");
    }
    public long get_accountno(String email)
    {
        try{
            String query="select acc_no from Accounts where email=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("acc_no");
            }
            else {
                return -1;
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }
     public  long generate_new_accno()
     {
         String query="select acc_no from Accounts order by acc_no desc limit 1;";

         try{
             Statement statement=connection.createStatement();
             ResultSet rst=statement.executeQuery(query);
             if(rst.next())
             {
                 long last_accno=rst.getLong("acc_no");
                 long new_accno=last_accno+1;
                 return new_accno;
             }else {
                 return 2111981200; //ist accno
             }


         }catch (SQLException e)
         {
             e.printStackTrace();
         }
         return 2111981200;
     }
    public boolean accountNo_exist(String email)
    {
        String query="select acc_no from Accounts where email=?;";
        try
        {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            ResultSet rst=preparedStatement.executeQuery();
            if(rst.next())
            {
                return true;

            }else{
                return false;
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
