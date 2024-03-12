import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection)
    {
        this.connection=connection;

    }

    public void showDoctors()
    {
        try {
            String query="Select * from doctors";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet rst=preparedStatement.executeQuery();
            System.out.println("Doctor lists : ");
            System.out.println("+---------------+--------------+-----------------+----------------+");
            System.out.println("| doctorID    |  Doctor Name          | Specialization   |");
            System.out.println("+---------------+--------------------+----------------+");
            while (rst.next())
            {
                int id= rst.getInt("id");
                String name=rst.getString("name");

                String specialization=rst.getString("specialization");
                System.out.printf("| %-10s | %-20s | %-15s |\n",id,name,specialization);
                System.out.println("+---------------+------------------+----------------+");

            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public boolean getDoctorId(int id)
    {
        try
        {
            String query="select * from doctors where id =?;";
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

