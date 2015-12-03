import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudRegistration {
	public static void main(String[] args){
		try{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Hello!");
        String s = "";
        while(true){
        	System.out.println("Do you want to register a new student?" + "\n" + "Y/N");
        	s = br.readLine();
            if(s.toUpperCase().equals("N")){
            	System.exit(0);
            }else{
            	if(s.toUpperCase().equals("Y")){
            		break;
            	}
            }
        }
        

       
        try {
        	
        SetUp wizzard = new SetUp();
        //wizzard.makeTables();
        //wizzard.populateTables();
		PreparedStatement insertStmt = wizzard.getConnection().prepareStatement("INSERT INTO Student(studentID, titleID, foreName, familyName, dateOfBirth)" +
																		"VALUES(?, ?, ?, ? ,?)");
		//input title and check that its valid
        
        while(true){
        	System.out.println("Enter student's title (Mr/Mrs/Mss/Dr/Prof).");
        	s = br.readLine();
        	if(s.toUpperCase().equals("MR") || s.toUpperCase().equals("MRS") ||
        			s.toUpperCase().equals("MSS") || s.toUpperCase().equals("DR")
        			|| s.toUpperCase().equals("PROF")){
        		break;
        	}
        }
        
        PreparedStatement queryStmt = wizzard.getConnection().prepareStatement("SELECT Titles.titleID FROM Titles WHERE titleString = ?");
        char head = s.toUpperCase().toCharArray()[0];
        String title = head + s.substring(1, s.length()).toLowerCase();
        queryStmt.setString(1, title);
        ResultSet rs = queryStmt.executeQuery();
        rs.next();
        insertStmt.setInt(2, rs.getInt(1));
        
        //input name and surname
        queryStmt = wizzard.getConnection().prepareStatement("SELECT StudentID FROM Student WHERE forename = ? AND familyname = ? AND dateofbirth = ?");
        System.out.println("Enter student's forename"); 
        String forename = br.readLine();
        head = forename.toUpperCase().toCharArray()[0];
        forename = head + forename.substring(1, forename.length()).toLowerCase();
        System.out.println("Entry student's family name");
        String familyname = br.readLine();
        head = familyname.toUpperCase().toCharArray()[0];
        familyname = head + familyname.substring(1, familyname.length()).toLowerCase();
        queryStmt.setString(1, forename);
        queryStmt.setString(2, familyname);
        
        //input date of birth
        
    	int day = 0;
    	int month = 0;
    	int year = 0;
        
        loopy:
        while(true){
        System.out.println("Enter student's date of birth (DD/MM/YYYY)");
        s = br.readLine();
        String[] maybeDate = s.split("/");
        if(maybeDate.length == 3 && maybeDate[0].length() == 2 && maybeDate[1].length() == 2 && maybeDate[2].length() == 4){
        	try{
        		day = Integer.parseInt(maybeDate[0]);
        		month = Integer.parseInt(maybeDate[1]);
        		year = Integer.parseInt(maybeDate[2]);
        	}catch(NumberFormatException e){
        		System.out.println("Invalid date (check for alphabetical chars)");
        	}
        	if(month == 2){
        		if (year % 4 == 0 && day < 30){
        			break;
        		}else{
        			if(day < 29){
        				break;
        			}
        		}
        	}else{
        		if(year > 1900 && year < 2000){
        			switch (month) {
        			case 1: if (day > 31) {System.out.println("Invalid day!");} else break loopy; break;
        			case 3: if (day > 31) {System.out.println("Invalid day!");} else break loopy; break;
        			case 4: if (day > 30) {System.out.println("Invalid day!");} else break loopy; break;
        			case 5: if (day > 31) {System.out.println("Invalid day!");} else break loopy; break; 
        			case 6: if (day > 30) {System.out.println("Invalid day!");} else break loopy; break;
        			case 7: if (day > 31) {System.out.println("Invalid day!");} else break loopy; break;
        			case 8: if (day > 31) {System.out.println("Invalid day!");} else break loopy; break;
        			case 9: if (day > 30) {System.out.println("Invalid day!");} else break loopy; break;
        			case 10: if (day > 31) {System.out.println("Invalid day!");} else break loopy; break;
        			case 11: if (day > 30) {System.out.println("Invalid day!");} else break loopy; break;
        			case 12: if (day > 31) {System.out.println("Invalid day!");} else break loopy; break;
        			default: System.out.println("Invalid month");
        			}
        			
        		}else{
        			System.out.println("Invalid year!");
        		}
        	}
        	
        }else{
        	System.out.println("Invalid date format!");
        }
        }
    	
    	
    	
        queryStmt.setDate(3, java.sql.Date.valueOf(year + "-" + month + "-" + day));
        ResultSet rs2 = queryStmt.executeQuery();
     	

        if(rs2.next()){
        	System.out.println("Student with the same personal data is already in the system, do you still want to add this student? Y/N");
        	s = br.readLine();
        	if(s.toUpperCase().equals("N")){
        		wizzard.closeConnE();
        		System.exit(0);
        	}
        }
        
        insertStmt.setString(3, forename);
        insertStmt.setString(4, familyname);
        insertStmt.setDate(5, java.sql.Date.valueOf(year + "-" + month + "-" + day));
        
        rs =  wizzard.getConnection().createStatement().executeQuery("SELECT MAX(StudentID) FROM Student");
        rs.next();
        insertStmt.setInt(1, rs.getInt(1) + 1);
        insertStmt.executeUpdate();
        wizzard.closeConnE();
        System.out.println("Student successfully added to the system!");
        } catch (SQLException e) {
			e.printStackTrace();
        }
        
        }catch(IOException e){
            e.printStackTrace();
        }
	}
}
 