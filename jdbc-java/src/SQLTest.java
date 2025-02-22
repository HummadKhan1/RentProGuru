import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class SQLTest {

    public static void print (ResultSet x, int num) throws Exception {
        while(x.next()) {
            String string = "";
            for (int i = 1; i <= num; i++)
                string += x.getString(i) + "	";
            System.out.println(string);
        }
    }
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub

        //Database variables
        String url = "jdbc:mysql://localhost:3306/phase4";
        String user = "root";
        String password = "Darkblue123#";
        String query1 = "Select * from Renter";
        String query2 = "Select itemID,description,location,itemStatus from Item";
        String query3 = "Select r.name, Sum(f.amount) as total from Renter r, FINED_FOR f Where r.renterID = f.renterID Group By name";
        String query4 = "Select * from Employee";

        //Database connection
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stm = con.createStatement();

            Scanner scn = new Scanner(System.in);
            Scanner scn1= new Scanner(System.in);
            int action = -1;
            System.out.println("Welcome to RentProGuru.");
            while(action != 4) {
                System.out.println("What do you want to do? Enter the number to select.");
                System.out.println("1: Manage Renters.");
                System.out.println("2: Manage Employees.");
                System.out.println("3: Manage Items.");
                System.out.println("4: Quit.");
                System.out.print(" > ");
                action = scn.nextInt();
                switch(action) {
                    case 4://quitting
                        System.out.println("Quitting...");
                        break;
                    case 1://Manage Renters
                        //TODO
                        while(action != 0) {
                            ResultSet rs = stm.executeQuery(query1);
                            print(rs,4);
                            System.out.println("\n0: Go Back.");
                            System.out.println("1: Update Phone Number.");
                            System.out.println("2: Insert Renter.");
                            System.out.println("3: Remove Renter.");
                            System.out.print(" > ");
                            action = scn.nextInt();
                            switch(action) {
                                case 1:
                                    PreparedStatement stm2 = con.prepareStatement("Update Renter Set phoneNumber = ? Where renterID = ?");
                                    System.out.println("\nEnter ID:");
                                    String id = scn1.next();
                                    System.out.println("Enter new phone number:");
                                    String phoneNum = scn1.next();
                                    stm2.setString(1, phoneNum);
                                    stm2.setString(2, id);
                                    stm2.executeUpdate();
                                    System.out.println("\nPhone Number Updated.\n");
                                    stm2.close();
                                    break;
                                case 2:
                                    PreparedStatement stm3 = con.prepareStatement("Insert into Renter(renterID,name,email,phoneNumber) values(?,?,?,?)");
                                    System.out.println("\nEnter ID:");
                                    String id1 = scn1.next();
                                    scn1.nextLine();
                                    System.out.println("Enter name:");
                                    String name = scn1.nextLine();
                                    System.out.println("Enter email:");
                                    String email = scn1.next();
                                    System.out.println("Enter phone number:");
                                    String phoneNum1 = scn1.next();
                                    scn1.nextLine();
                                    stm3.setString(1, id1);
                                    stm3.setString(2, name);
                                    stm3.setString(3, email);
                                    stm3.setString(4, phoneNum1);
                                    stm3.executeUpdate();
                                    System.out.println("\nRenter Inserted.\n");
                                    stm3.close();
                                    break;
                                case 3:
                                    PreparedStatement stm4 = con.prepareStatement("Delete from Renter Where name = ?");
                                    System.out.println("Remove renter: ");
                                    String name1 = scn1.nextLine();
                                    stm4.setString(1, name1);
                                    stm4.executeUpdate();
                                    System.out.println("\nRenter Removed.\n");
                                    stm4.close();
                                    break;
                                case 0:
                                    break;
                                default:
                                    System.out.println("Command not shown on the options.");
                            }
                        }
                        break;
                    case 2://Look Up Employees
                        //TODO
                        while(action != 0) {
                            System.out.println("\n0: Go Back.");
                            System.out.println("1: Employee List.");
                            System.out.println("2: Search by number of items maintained");
                            System.out.print(" > ");
                            action = scn.nextInt();
                            switch(action) {
                                case 1:
                                    ResultSet rs3 = stm.executeQuery(query4);
                                    print(rs3,6);
                                    break;
                                case 2:
                                    System.out.println("Number of items maintained: ");
                                    int num = scn1.nextInt();
                                    ResultSet rs4 = stm.executeQuery("Select e.ssn, Count(i.employee) From Employee e, Item i where e.ssn = i.employee Group by ssn Having Count(employee) >= " + num);
                                    print(rs4,2);
                                    break;
                                case 0:
                                    break;
                            }

                        }
                        break;
                    case 3://Look Up Items
                        while(action != 0) {
                            System.out.println("\n0: Go Back.");
                            System.out.println("1: See items");
                            System.out.println("2: Total amount fined by renters:.");
                            System.out.println("3: Change item status");
                            System.out.print(" > ");
                            action = scn.nextInt();
                            switch(action) {
                                case 1:
                                    ResultSet rs1 = stm.executeQuery(query2);
                                    print(rs1,4);
                                    break;
                                case 2:
                                    ResultSet rs2 = stm.executeQuery(query3);
                                    print(rs2,2);
                                    break;
                                case 3:
                                    PreparedStatement stm2 = con.prepareStatement("Update Item Set itemStatus = ? Where itemID = ?");
                                    System.out.println("\nEnter itemID:");
                                    String id = scn1.next();
                                    System.out.println("Status of item:");
                                    String itemStatus = scn1.next();
                                    stm2.setString(1, itemStatus);
                                    stm2.setString(2, id);
                                    stm2.executeUpdate();
                                    System.out.println("\nItem Updated.\n");
                                    stm2.close();
                                    break;
                                case 0:
                                    break;
                            }
                        }
                        break;
                    default:
                        System.out.println("Command not shown on the options.");
                }
            }
            System.out.println("Quit successful.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}