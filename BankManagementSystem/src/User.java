import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
 private Scanner scanner;
 private Connection connection;

 public User(Connection connection,Scanner scanner)
 {
     this.connection=connection;
     this.scanner=scanner;
 }

public  void registeruser()
{
    scanner.nextLine();
    System.out.println("Enter Full name: ");
    String fullname=scanner.nextLine();
    System.out.println("Enter Email name: ");
    String email=scanner.nextLine();

    System.out.println("Enter password: ");
    String password=scanner.nextLine();
    if(userExist(email))
    {
        System.out.println("User already exist with this email");
        return;
    }
    String registerquery="insert into user(fullname,email,password) values(?,?,?);";
    try {
        PreparedStatement preparedStatement=connection.prepareStatement(registerquery);
        preparedStatement.setString(1,fullname);
        preparedStatement.setString(2,email);
        preparedStatement.setString(3,password);

        int rowsaffected=preparedStatement.executeUpdate();
         if(rowsaffected>0)
         {
             System.out.println("Registration successfull");
         }else {
             System.out.println("Registration failed");
         }

    }catch (SQLException e)
    {
        e.printStackTrace();
    }

}
public String loginuser()
{
    scanner.nextLine();
    System.out.println("Enter Email name: ");
    String email=scanner.nextLine();

    System.out.println("Enter password: ");
    String password=scanner.nextLine();
    String login_query="select * from user where email=? and password=?;";
    try{
        PreparedStatement preparedStatement=connection.prepareStatement(login_query);
        preparedStatement.setString(1,email);
        preparedStatement.setString(2,password);
        ResultSet rst=preparedStatement.executeQuery();
        if(rst.next())
        {
            return email;
        }
        else {
            return null;
        }
    }catch (SQLException e)
    {
        e.printStackTrace();
    }
    return null;
}
public boolean userExist(String email)
{
    String query="select * from user where email=?;";
    try {
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setString(1,email);
        ResultSet rst=preparedStatement.executeQuery();
        if(rst.next())
        {
            return true;
        }
        else {
            return false;
        }

    }catch (SQLException e)
    {
      e.printStackTrace();

    }
return false;
}
}
