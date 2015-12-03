import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LecturerReport {
	public static void main(String[] str){
		try{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Hello!");
        String s = "";
        while(true){
        	System.out.println("Do you want to produce a report of a lecturer?" + "\n" + "Y/N");
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
        System.out.println("Enter the LecturerID of a lecturer you want to make the report for");
        

			
				query = wizzard.getConnection().prepareStatement("SELECT * FROM Lecturer WHERE LecturerID = ?");

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
        	System.out.println("There is no lecturer with such ID in the system");
        	wizzard.closeConnE();
        	System.exit(0);
        }
		for(int i = 1; i < 6; i++){
			Statement quQu = wizzard.getConnection().createStatement();
			String getSids = "SELECT StudentID FROM Tutor WHERE LecturerID = " + s + " AND StudentID = (SELECT StudentID FROM StudentRegistration WHERE Tutor.StudentID = StudentRegistration.StudentID AND yearOfStudy = " + i + ")";
			rs = quQu.executeQuery(getSids);
			String titles = "";
			String forenames = "";
			String familynames = "";
			String dobs =  "";
			String regTypes = "";
			String emails = "";
			String addresses = "";
			String emailsK = "";
			String addressesK = "";
				while(rs.next()){
					int sid = rs.getInt(1);
					String getTitle = "SELECT titleString FROM Titles WHERE TITLEID = (SELECT TitleID FROM STudent WHERE StudentID = " + sid + ")";
					ResultSet titleRs = wizzard.getConnection().createStatement().executeQuery(getTitle); 
					titleRs.next();
					titles = titleRs.getString(1);
					String getNames = "SELECT forename, familyname, dateofbirth FROM Student WHERE StudentID = " + sid;
					ResultSet names = wizzard.getConnection().createStatement().executeQuery(getNames);
					names.next();
					forenames = names.getString(1);
					familynames = names.getString(2);
					dobs = names.getDate(3).toString();
					String getReg = "SELECT description FROM RegistrationTYpe WHERE RegistrationTypeID = (SELECT RegistrationTypeID FROM StudentREgistration WHERE StudentID = " + sid + ")";
					ResultSet regType = wizzard.getConnection().createStatement().executeQuery(getReg);
					regType.next();
					regTypes = regType.getString(1);
					String getContacts = "SELECT emailaddress, postaladdress FROM StudentContact WHERE StudentID = " + s;
					ResultSet contacts = wizzard.getConnection().createStatement().executeQuery(getContacts);
					contacts.next();
					emails = contacts.getString(1);
					addresses = contacts.getString(2);
					String getKins = "SELECT emailaddress, postaladdress FROM nextOfKinContact WHERE studentID = " + s;
					ResultSet kins = wizzard.getConnection().createStatement().executeQuery(getKins);
					kins.next();
					emailsK = kins.getString(1);
					addressesK = kins.getString(2);
					
					System.out.println(titles + " " + forenames + " " + familynames + "\n" +
					"SID: " + sid  + "\n" +
					"Registration: " + regTypes + "\n" +
					"DOB: " + dobs + "\n" +
					"Contacts: " + emails + " " + addresses + "\n" +
					"Emergency contacts: " + emailsK + " " + addressesK);
					
				}
		}
        
		wizzard.closeConnE();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
