import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudReport {
	public static void main (String[] args){
		try{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Hello!");
        String s = "";
        while(true){
        	System.out.println("Do you want to produce a report of a student?" + "\n" + "Y/N");
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
        PreparedStatement query;
        SetUp wizzard = new SetUp();
       // wizzard.makeTables();
       // wizzard.populateTables();
        while(true){
        System.out.println("Enter the StudentID of a student you want to make the report for");
        

			query = wizzard.getConnection().prepareStatement("SELECT * FROM Student WHERE StudentID = ?");
			s = br.readLine();
			try{
			query.setInt(1, Integer.parseInt(s));
			}catch(NumberFormatException ex){
				System.out.println("ID must be a number");
			}
			break;
			
			
        }
        ResultSet rs = query.executeQuery();
        if(!rs.next()){
        	System.out.println("There is no student with such ID in the system");
        	wizzard.closeConnE();
        	System.exit(0);
        }
        
        Statement quQu = wizzard.getConnection().createStatement(); 
        String getTitleSql = "SELECT Titles.titlestring FROM Titles WHERE " +
        				"Titles.titleID = (SELECT Student.titleID FROM Student WHERE StudentID = " + s + ")";
        rs = quQu.executeQuery(getTitleSql);
        rs.next();
        String title = rs.getString(1);
        
        String getNames = "SELECT forename, familyname, dateofbirth FROM Student WHERE StudentID =" + s;
        rs = quQu.executeQuery(getNames);
        rs.next();
        String firstName = rs.getString(1);
        String lastName = rs.getString(2);
        String dateofbirth = rs.getDate(3).toString();
        String sid = s;
        
        String getYearOfStudy = "SELECT yearOfStudy FROM StudentRegistration WHERE studentid = " + s;
        rs = quQu.executeQuery(getYearOfStudy);
        rs.next();
        int yearOfStudy = rs.getInt(1);
        
        String getRegType = "SELECT description FROM RegistrationType WHERE registrationTypeID = (SELECT registrationtypeID from Studentregistration WHERE studentID = " + s + ")";
        rs = quQu.executeQuery(getRegType);
        rs.next();
        String regType = rs.getString(1);
        
        String getContacts = "SELECT emailaddress, postaladdress FROM StudentContact WHERE Studentid = " + s;
        rs = quQu.executeQuery(getContacts);
        rs.next();
        String email = rs.getString(1);
        String address = rs.getString(2);
        
        String getKinContacts = "SELECT name, emailaddress, postaladdress FROM nextofkincontact WHERE studentID = " + s;
        rs = quQu.executeQuery(getKinContacts);
        rs.next();
        String kinName = rs.getString(1);
        String kinEmail = rs.getString(2);
        String kinAddress = rs.getString(3);
        
        String getTutor = "SELECT forename, familyname FROM lecturer WHERE lecturerid = (SELECT LecturerID FROM Tutor WHERE StudentID = " + s + ")";
        rs = quQu.executeQuery(getTutor);
        rs.next();
        String tutName = rs.getString(1);
        String tutFam = rs.getString(2);
        
        System.out.println("Student report:" + "\n" +
        		title + " " + firstName + " " + lastName + "\n" +
        		"Born in " + dateofbirth + "\n" +
        		"Student ID " + sid + "\n" +
        		"Currently in year " + yearOfStudy + "\n" +
        		"Registration type " + regType + "\n" +
        		"Contact information email: " + email + " " + "address: " + address + "\n" +
        		"Emergency contact " + kinName + " email: " + kinEmail + " address: " + kinAddress + "\n" + 
        		"Personal tutor " + tutName + " " + tutFam + "\n");
        
        wizzard.closeConnE();
        } catch (SQLException e) {
			e.printStackTrace();
        } 
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}