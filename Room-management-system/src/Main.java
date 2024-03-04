import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Reservation{
  public void reserveRoom(Connection conn,Scanner scanner,Statement statement)
  {
       try{


           System.out.print("Enter room number: ");
           int roomNumber = scanner.nextInt();
           scanner.nextLine();

           System.out.print("Enter guest name: ");
           String guestName = scanner.nextLine();
           System.out.print("Enter contact number: ");
           String contactNumber = scanner.next();

           String insertquery = "INSERT INTO reservation (guest_name, room_no, contact_no) " +
                   "VALUES ('" + guestName + "', " + roomNumber + ", '" + contactNumber + "')";
          int rowaffected=statement.executeUpdate(insertquery);

          if(rowaffected>0)   {
              System.out.println("Reservation successfull for "+guestName);
          }
            else {
              System.out.println("Reservation failed.");
          }



       }catch (SQLException e)
       {

           System.out.println(e.getMessage());


       }


  }
    public void getRoomNo(Connection conn,Scanner scanner,Statement statement)
    {
            try {
                System.out.print("Enter reservation ID: ");
                int reservationId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter guest name: ");
                String guestName = scanner.nextLine();

                String sql = "SELECT room_no FROM reservation " +
                        "WHERE rid = " + reservationId +
                        " AND guest_name = '" + guestName + "'";
                ResultSet resultSet = statement.executeQuery(sql);

                    if (resultSet.next()) {
                        int roomNumber = resultSet.getInt("room_no");
                        System.out.println("Room number for Reservation ID " + reservationId +
                                " and Guest " + guestName + " is: " + roomNumber);
                    } else {
                        System.out.println("Reservation not found for the given ID and guest name.");
                    }


            }catch (SQLException e)
            {
                e.printStackTrace();
            }
    }
    public void viewReservations(Connection conn,Scanner sc,Statement statement)
    {
          try{
              String getquery="select * from reservation;";
              ResultSet res=statement.executeQuery(getquery);
              System.out.println("All reservations");
              System.out.println("+----------------------+-------------------------+--------------------------+---------------------");
              System.out.println("| reservation id |    guest name    |   room no    |    contact no   |    date of reservation    | ");
              System.out.println("+-------------------+--------------------+--------------------+----------------------+------------------+");
              while (res.next())
              {
                  int id=res.getInt("rid");
                  String guest_name=res.getString("guest_name");
                  int room_no=res.getInt("room_no");
                  String contact=res.getString("contact_no");
                  String date=res.getTimestamp("reservation_date").toString();
                  System.out.printf("| %-15d | %-20s| %-20d | %-20s |\n ",id,guest_name,room_no,contact,date);
              }
              System.out.println("+----------------+--------------------------------+----------------------+-------------------------+");



          }catch (SQLException e)
          {
              System.out.println(e.getMessage());
          }
    }
    public void updateReservation(Connection conn,Scanner scanner,Statement statement)
    {
          try{
              System.out.println("Enter reservation id");
              int rid=scanner.nextInt();
              scanner.nextLine();
              if(!isRidPresent(statement,rid))
              {
                  System.out.println("Reservation not found for the given ID.");
                  return;
              }
              System.out.print("Enter new guest name: ");
              String newGuestName = scanner.nextLine();

              System.out.print("Enter new room number: ");

              int newRoomNumber = scanner.nextInt();
              System.out.print("Enter new contact number: ");
              String newContactNumber = scanner.next();
              String sql = "UPDATE reservation SET guest_name = '" + newGuestName + "', " +
                      "room_no = " + newRoomNumber + ", " +
                      "contact_no = '" + newContactNumber + "' " +
                      "WHERE rid = " + rid;
              int rows=statement.executeUpdate(sql);
              if (rows > 0) {
                  System.out.println();
                  System.out.println("********************************");
                  System.out.println("Reservation updated successfully!");
                  System.out.println("*****************************");
              } else {
                  System.out.println("Reservation update failed.");
              }
          }catch (SQLException e){
              e.printStackTrace();
          }
    }
    public void deleteReservation(Connection conn,Scanner scanner,Statement statement)
    {
        try{
            System.out.println("Enter reservation id");
            int rid=scanner.nextInt();

            if(!isRidPresent(statement,rid))
            {
                System.out.println("Reservation not found for the given ID.Can not delete");
                return;
            }

            String sql = "Delete from reservation where rid= "+rid;
            int rows=statement.executeUpdate(sql);
            if (rows > 0) {
                System.out.println("*********************************");
                System.out.println("+++++++++Reservation deleted successfully!+++++++++++");
                System.out.println("**********************");
            } else {
                System.out.println("Reservation deletion failed.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public  void exitFun(){
         int i=0;
        System.out.println();
        System.out.println("************************");
        System.out.println("Thank u for using room managaement system.");
        System.out.println("************************");
    }
    public boolean isRidPresent(Statement statement,int rid)
    {
        try {
            String query="select rid from reservation where rid="+rid;
             ResultSet rst=statement.executeQuery(query);
             return rst.next();//return true if it has value or false;
        }catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}



public class Main {
   private static final String url="jdbc:mysql://localhost:3306/hotel_db";
   private static final String username="root";
   private static final String password="Suvadip@632";


    public static void main(String[] args) throws ClassNotFoundException,SQLException  {

        try {
            //connect to driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        //connecting to db
        try {

            Connection conn=DriverManager.getConnection(url,username,password);
            Statement statement=conn.createStatement();
            Scanner sc=new Scanner(System.in);
            System.out.println("connected to db");
            Reservation myobj=new Reservation();
            while(true)
            {
                System.out.println("************welcome to room management system******************");
                System.out.println("Enter 0: Exit system");

                System.out.println("Enter 1: Reserve a room ");
                System.out.println("Enter 2: view reservations ");
                System.out.println("Enter 3:  get  room no: ");
                System.out.println("Enter 4: update reservations ");
                System.out.println("Enter 5: delete reservation ");
                System.out.println("**********************");
                System.out.print("Enter your choice : ");
                int choice=sc.nextInt();
                switch (choice){
                    case 1:
                        myobj.reserveRoom(conn,sc,statement);
                        break;
                    case 2:
                        myobj.viewReservations(conn,sc,statement);
                        break;
                    case 3:
                        myobj.getRoomNo(conn,sc,statement);
                        break;
                    case 4:
                        myobj.updateReservation(conn,sc,statement);
                        break;
                    case 5:
                        myobj.deleteReservation(conn,sc,statement);
                        break;
                    case 0:
                        myobj.exitFun();
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid input please try again");
                }

            }


        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }
}