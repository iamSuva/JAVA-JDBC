import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;
    public Patient(Connection connection,Scanner scanner)
    {
        this.scanner=scanner;
        this.connection=connection;
    }
    public  void addPatient()
    {
        System.out.println("Enter patient name ");
        String pname=scanner.nextLine();
        System.out.println("Enter patient age ");
        int age=scanner.nextInt();
        System.out.println("Enter patient gender ");
        String gender=scanner.next();

        try
        {
            String query="insert into patients(name,age,gender) values(?,?,?);";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,pname);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
            int rowaffected=preparedStatement.executeUpdate();
            if(rowaffected>0)
            {
                System.out.println("Patient added successfully");
            }else {
                System.out.println("Failed to add patient");
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void showPatients()
    {
        try {
            String query="Select * from patients";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet rst=preparedStatement.executeQuery();
            System.out.println("Patients lists : ");
            System.out.println("+---------------+--------------+-----------------+----------------+");
            System.out.println("| PatientID    |   Name          |  Age       |    Gender    |");
            System.out.println("+---------------+--------------+-----------------+----------------+");
            while (rst.next())
            {
                int id= rst.getInt("id");
                String name=rst.getString("name");
                int age=rst.getInt("age");
                String gender=rst.getString("gender");
                System.out.printf("| %-10s | %-20s | %-10s | %-15s |\n",id,name,age,gender);
                System.out.println("+---------------+--------------+-----------------+----------------+");

            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public boolean getPatientId(int id)
    {
        try
        {
              String query="select * from patients where id =?;";
              PreparedStatement preparedStatement=connection.prepareStatement(query);
              preparedStatement.setInt(1,id);
              ResultSet rst=preparedStatement.executeQuery();
              if(rst.next())
              {
                  return true;
              }
              else
                  return false;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}

