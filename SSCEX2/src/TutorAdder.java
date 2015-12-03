import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TutorAdder {
	public static void main(String[] str){
		try{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Hello!");
        String sid = "";
        String lid = "";
        while(true){
        	System.out.println("Do you want to add a tutor to a student?" + "\n" + "Y/N");
        	sid = br.readLine();
            if(sid.toUpperCase().equals("N")){
            	System.exit(0);
            }else{
            	if(sid.toUpperCase().equals("Y")){
            		break;
            	}
            }
        }
        try {
        PreparedStatement query;
        SetUp wizzard = new SetUp();
        //wizzard.makeTables();
        //wizzard.populateTables();
        while(true){
        System.out.println("Enter the LecturerID of a lecturer you want to add as a tutor");
        

			
				query = wizzard.getConnection().prepareStatement("SELECT * FROM Lecturer WHERE LecturerID = ?");

				lid = br.readLine();
			try{
			query.setInt(1, Integer.parseInt(lid));
			}catch(NumberFormatException ex){
				System.out.println("ID must be a number");
			}
			break;	
        }
        ResultSet rs = query.executeQuery();
        if(!rs.next()){
        	System.out.println("There is no lecturer with such ID in the system");
        	wizzard.closeConnE();
        	System.exit(0);
        }
        
        while(true){
        	System.out.println("Enter the StudentIN of a student you want to make a connection with");
        	
        	query = wizzard.getConnection().prepareStatement("SELECT * FROM Student WHERE StudentID = ?");
        	
        	sid = br.readLine();
        	try{
        		query.setInt(1, Integer.parseInt(sid));
        	}catch (NumberFormatException ex){
        		System.out.println("ID must be a number");
        	}
        	break;
        }
        rs = query.executeQuery();
        if(!rs.next()){
        	System.out.println("There is no student with such ID in the system");
        	wizzard.closeConnE();
        	System.exit(0);
        }
        
        Statement isTutor = wizzard.getConnection().createStatement();
        String getRelationship = "SELECT * FROM Tutor WHERE StudentID = " + sid + "AND LecturerID = " + lid;
        rs = isTutor.executeQuery(getRelationship);
        
        if(rs.next()){
        	System.out.println("This student already has this tutor assigned to him");
        	wizzard.closeConnE();
        	System.exit(0);
        }
        
        Statement add = wizzard.getConnection().createStatement();
        String addTutor = "INSERT INTO Tutor(StudentID, LecturerID) VALUES(" + sid + ", " + lid + ")";
        add.executeUpdate(addTutor);
        
        System.out.println("Tutor-student relationship successfully added");
        wizzard.closeConnE();
        }catch (SQLException e){
        	e.printStackTrace();
        }
		}catch (IOException ex){
			ex.printStackTrace();
		}
	}
}
