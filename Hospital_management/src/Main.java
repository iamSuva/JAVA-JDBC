import java.sql.*;
import java.util.Scanner;

public class Main {

        private static final String url = "jdbc:mysql://localhost:3306/hospital_db";
        private static final String username = "root";
        private static final String password = "Suvadip@632";
        public static void main(String[] args) throws ClassNotFoundException, SQLException {

            Scanner scanner=new Scanner(System.in);
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
            }catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }

            try
            {
                Connection connection= DriverManager.getConnection(url,username,password);
                Patient patient=new Patient(connection,scanner);
                Doctor doctor=new Doctor(connection);

                while(true)
                {
                    System.out.println("Welcome to hospital management system ");
                    System.out.println("Enter 1: Add patient");
                    System.out.println("Enter 2 :View Patients");
                    System.out.println("Enter 3: view doctors");
                    System.out.println("Enter 4:Book appointment");
                    System.out.println("Enter 5:Exit");
                    System.out.println("****************************");
                    System.out.println("Enter your choice: ");
                    int choice=scanner.nextInt();
                    switch (choice)
                    {
                        case 1:
                            scanner.nextLine();
                            patient.addPatient();
                            break;
                        case 2:
                            scanner.nextLine();
                            patient.showPatients();
                            break;//
                        case 3:
                            scanner.nextLine();
                            doctor.showDoctors();
                            break;
                        case 4:
                            scanner.nextLine();
                            bookAppointment(scanner,connection,doctor,patient);
                            break;
                        case 5:
                            System.out.println("***************************");
                            System.out.println("Thank you for visiting us");
                            return;
                        default:
                            System.out.println("Enter valid choice");
                            break;
                    }
                }
            }catch (SQLException e)
            {
                e.printStackTrace();
            }

    }
    public  static void  bookAppointment(Scanner scanner,Connection connection,Doctor doctor,Patient patient)
    {
        System.out.println("Enter patient id ");
        int pid=scanner.nextInt();
        System.out.println("Enter doctor id ");
        int doctor_id=scanner.nextInt();
        System.out.println("Enter appointment date : ");
        String date=scanner.next();

        if(doctor.getDoctorId(doctor_id) && patient.getPatientId(pid)) //if doctor and patient both exist
        {
             if(isDoctorAvailable(doctor_id,date,connection))
             {
                 String query="Insert into appointments(doctor_id,patient_id,appointment_date) values(?,?,?);";
                 try
                 {
                     PreparedStatement preparedStatement=connection.prepareStatement(query);
                     preparedStatement.setInt(1,doctor_id);
                     preparedStatement.setInt(2,pid);
                     preparedStatement.setString(3,date);
                     int rows=preparedStatement.executeUpdate();
                     if(rows>0)
                     {
                         System.out.println("Appointment has been booked");
                     }else {
                         System.out.println("Fail to book appintment");
                     }
                 }catch (SQLException e)
                 {
                     e.printStackTrace();
                 }

             }else {
                 System.out.println("Doctor in not avilable");
             }
        }
        else {
            System.out.println("Either doctor id or patient id is invalid");
        }
    }
    public  static boolean isDoctorAvailable(int id,String date,Connection connection)
    {
        String query="Select count(*) from appointments where doctor_id=? and appointment_date=?;";
        try
        {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,date);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                int count=resultSet.getInt(1);
                if(count==0)
                {
                    return  true;
                }
                else
                    return  false;
            }
        }catch (SQLException e) {
            e.printStackTrace();

        }
        return  false;
    }
}