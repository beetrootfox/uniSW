import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

import javax.sql.*;


// TODO: Auto-generated Javadoc
/**
 * The Class SetUp.
 */

public class SetUp {
	
	/** The dbname. */
	private final String dbname = "jdbc:postgresql://dbteach2/";
	
	/** The login. */
	private final String login = "dxi459"; 
	
	/** The password. */
	private final String password = "jaclutre";
	
	/** The driver. */
	private final String driver = "org.postgresql.Driver";
	
	/** The tables. */
	private ArrayList<String> tables = new ArrayList<String>();
	
	/** The table names. */
	private ArrayList<String> tableNames = new ArrayList<String>();
	
	/** The conn. */
	private Connection conn = null;
	
	/** The stmt make. */
	private Statement stmtMake = null;
	
	/** The stmt pop. */
	private PreparedStatement stmtPop = null;
	
	
	
	/**
	 * Instantiates a new sets the up.
	 */
	public SetUp(){
		try 
		{
			Class.forName(driver);
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver not found");
		}

		try 
		{
			conn = DriverManager.getConnection(dbname, login, password);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		if (conn != null) 
		{

		} else {
			System.out.println("Failed to make connection");
		}
	}
	
	/**
	 * Close conn e.
	 */
	public void closeConnE(){

		try {
			//this.deleteTables();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public Connection getConnection(){
		return conn;
	}
	
	/**
	 * Make.
	 */
	private void make(){
		try
		{
			for(int i = 0; i < tables.size(); i++){
				stmtMake.executeUpdate(tables.get(i));
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
			
	}
	
	/**
	 * Delete.
	 */
	public void deleteTables(){
		try
		{
			System.out.println("DROP TABLE IF EXISTS " +  " CASCADE");
			for(int i = 0; i < tableNames.size(); i++){
				
				stmtMake.executeUpdate("DROP TABLE IF EXISTS " + tableNames.get(i) + " CASCADE");
				System.out.println("DROP TABLE IF EXISTS " + tableNames.get(i) + " CASCADE");
			}
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Make tables.
	 */
	public void makeTables(){
		try{
			stmtMake = conn.createStatement();
			
			String student = "CREATE TABLE Student " +
							"(studentID INTEGER PRIMARY KEY," +
							"titleID INTEGER," +
							"forename VARCHAR(225) NOT NULL," +
							"familyName VARCHAR(225)," +
							"dateOfBirth DATE CHECK (dateOfBirth < '2000-01-01' AND dateOfBirth > '1900-01-01'))";
			tables.add(student);
			tableNames.add("Student");
			
			String lecturer = "CREATE TABLE Lecturer " +
							"(lecturerID INTEGER PRIMARY KEY," +
							"titleID INTEGER," +
							"foreName VARCHAR(225) NOT NULL," +
							"familyName VARCHAR(225))";
			tables.add(lecturer);
			tableNames.add("Lecturer");
			
			String studreg = "CREATE TABLE StudentRegistration " +
							"(studentID INTEGER," +
							"yearOfStudy INTEGER NOT NULL CHECK (yearOfStudy = ANY('{1, 2, 3, 4, 5}'::int[]))," +
							"registrationTypeID INTEGER)";
			tables.add(studreg);
			tableNames.add("StudentRegistration");
			
			String studcon = "CREATE TABLE StudentContact " +
							"(studentID INTEGER," +
							"eMailAddress VARCHAR(225) CHECK (eMailAddress LIKE '%@%.%')," +
							"postalAddress VARCHAR(225))";
			tables.add(studcon);
			tableNames.add("StudentContact");
			
			String nextofkin = "CREATE TABLE NextOfKinContact " +
							"(studentID INTEGER," +
							"name VARCHAR(225)," +
							"eMailAddress VARCHAR(225) CHECK (eMailAddress LIKE '%@%.%')," +
							"postalAddress VARCHAR(225))";
			tables.add(nextofkin);
			tableNames.add("NextOfKinContact");
			
			String lectcon = "CREATE TABLE LecturerContact " +
							"(lecturerID INTEGER, " +
							"office INTEGER," +
							"eMailAddress VARCHAR(225) CHECK (eMailAddress LIKE '%@%.%'))";
			tables.add(lectcon);
			tableNames.add("LecturerContact");
			
			String tutor = "CREATE TABLE Tutor " +
						"(studentID INTEGER," +
						"lecturerID INTEGER)";
			tables.add(tutor);
			tableNames.add("Tutor");
			
			String titles = "CREATE TABLE Titles " +
							"(titleID INTEGER PRIMARY KEY, " +
							"titleString VARCHAR(225) NOT NULL UNIQUE)";
			tables.add(titles);
			tableNames.add("Titles");
			
			String regtype = "CREATE TABLE RegistrationType " +
							"(registrationTypeID INTEGER PRIMARY KEY, " +
							"description VARCHAR(225) NOT NULL UNIQUE)";
			tables.add(regtype);
			tableNames.add("RegistrationType");
			
			String alterStudent = "ALTER TABLE STUDENT " +
								"ADD FOREIGN KEY (titleID) REFERENCES Titles(titleID) ON DELETE CASCADE";
			tables.add(alterStudent);
			
			String alterLecturer = "ALTER TABLE Lecturer " +
								"ADD FOREIGN KEY (titleID) REFERENCES Titles(titleID) ON DELETE CASCADE";
			tables.add(alterLecturer);
			
			String alterStudreg = "ALTER TABLE StudentRegistration " +
								"ADD FOREIGN KEY (studentID) REFERENCES Student(studentID) ON DELETE CASCADE," +
								"ADD FOREIGN KEY (registrationTypeID) REFERENCES RegistrationType(registrationTypeID) ON DELETE CASCADE";
			tables.add(alterStudreg);
			
			String alterStudcon = "ALTER TABLE StudentContact " +
								"ADD FOREIGN KEY (studentID) REFERENCES Student(studentID) ON DELETE CASCADE";
			tables.add(alterStudcon);
			
			String alterNextOfKin = "ALTER TABLE NextOfKinContact " +
								"ADD FOREIGN KEY (studentID) REFERENCES Student(studentID) ON DELETE CASCADE";
			tables.add(alterNextOfKin);
			
			String alterLeccon = "ALTER TABLE LecturerContact " +
								"ADD FOREIGN KEY (lecturerID) REFERENCES Lecturer(lecturerID)";
			tables.add(alterLeccon);
			
			String alterTutor = "ALTER TABLE Tutor " +
								"ADD FOREIGN KEY (studentID) REFERENCES Student(studentID) ON DELETE CASCADE," +
								"ADD FOREIGN KEY (lecturerID) REFERENCES Lecturer(lecturerID) ON DELETE CASCADE";
			tables.add(alterTutor);
			
					
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Populate tables.
	 */
	public void populateTables(){
		try{
			make();
			//Populate Titles table
			stmtPop = conn.prepareStatement("INSERT INTO Titles(titleID, titleString) " +
										"VALUES(?, ?)");
			String[] titles = {"Mr", "Mrs", "Ms", "Dr", "Prof"};
			for(int i = 0; i < titles.length; i++){
				stmtPop.setInt(1, i);
				stmtPop.setString(2, titles[i]);
				stmtPop.executeUpdate();
			}
			
			//Populate RegistrationType table
			stmtPop = conn.prepareStatement("INSERT INTO RegistrationType(registrationTypeID, description)" +
										"VALUES(?, ?)");
			String[] registrations = {"Normal", "Repeat", "External"};
			for(int i = 0; i < registrations.length; i++){
				stmtPop.setInt(1, i);
				stmtPop.setString(2, registrations[i]);
				stmtPop.executeUpdate();
			}
			
			//Populate Student table
			stmtPop = conn.prepareStatement("INSERT INTO Student(studentID, titleID, foreName, familyName, dateOfBirth)" +
									"VALUES(?, ?, ?, ?, ?)");
			//create student Joseph
			stmtPop.setInt(1, 0);
			stmtPop.setInt(2, 0);
			stmtPop.setString(3, "Joseph");
			stmtPop.setString(4, "Green");
			stmtPop.setDate(5, new Date(13, 5, 1994));
			stmtPop.executeUpdate();
			
			//create student Sarah
			stmtPop.setInt(1, 1);
			stmtPop.setInt(2, 2);
			stmtPop.setString(3, "Sarah");
			stmtPop.setString(4, "Brown");
			stmtPop.setDate(5, new Date(01, 4, 1996));
			stmtPop.executeUpdate();
			
			//create sudent Sam
			stmtPop.setInt(1, 2);
			stmtPop.setInt(2, 0);
			stmtPop.setString(3, "Sam");
			stmtPop.setString(4, "Genji");
			stmtPop.setDate(5, new Date(23, 8, 1991));
			stmtPop.executeUpdate();
			
			//create student Gandalf
			stmtPop.setInt(1, 3);
			stmtPop.setInt(2, 0);
			stmtPop.setString(3, "Gandalf");
			stmtPop.setString(4, "White");
			stmtPop.setDate(5, new Date(10, 6, 1900));
			stmtPop.executeUpdate();
			
			//create student Jane
			stmtPop.setInt(1, 4);
			stmtPop.setInt(2, 1);
			stmtPop.setString(3, "Jane");
			stmtPop.setString(4, "Grey");
			stmtPop.setDate(5, new Date(6, 6, 1999));
			stmtPop.executeUpdate();
			
			//create some random students
			Random rand = new Random();
			for (int i = 5; i < 100; i++){
				stmtPop.setInt(1, i);
				stmtPop.setInt(2, rand.nextInt(5));
				stmtPop.setString(3, "Forename" + i);
				stmtPop.setString(4, "Familyname" + i);
				stmtPop.setDate(5, new Date(rand.nextInt(28) + 1, rand.nextInt(12) + 1, rand.nextInt(20) + 1986));
				stmtPop.executeUpdate();
			}
			
			//Populate Lecturer table
			stmtPop = conn.prepareStatement("INSERT INTO Lecturer(lecturerID, titleID, foreName, familyName)" +
										"VALUES(?, ?, ?, ?)");
			stmtPop.setInt(1, 0);
			stmtPop.setInt(2, 0);
			stmtPop.setString(3, "Mark");
			stmtPop.setString(4, "Lee");
			stmtPop.executeUpdate();
			
			for(int i = 1; i < 5; i++){
				stmtPop.setInt(1, i);
				stmtPop.setInt(2, rand.nextInt(5));
				stmtPop.setString(3, "Name" + i);
				stmtPop.setString(4, "Familyname" + 1);
				stmtPop.executeUpdate();
			}
			
			//Populate StudentRegistration table
			stmtPop = conn.prepareStatement("INSERT INTO StudentRegistration(studentID, yearOfStudy, registrationTypeID)" + 
										"VALUES(?, ?, ?)");
			
			for(int i = 0; i < 100; i++){
				stmtPop.setInt(1, i);
				stmtPop.setInt(2, 1 + rand.nextInt(5));
				stmtPop.setInt(3, rand.nextInt(3));
				stmtPop.executeUpdate();
			}
			
			//Populate StudentContact table
			stmtPop = conn.prepareStatement("INSERT INTO StudentContact(studentID, eMailAddress, postalAddress)" + 
										"VALUES(?, ?, ?)");
			
			for(int i = 0; i < 100; i++){
				stmtPop.setInt(1, i);
				stmtPop.setString(2, "email" + i + "@" + "gmail.com");
				stmtPop.setString(3, "house " + rand.nextInt(30) + ", " + "Street " + i + ", City" + i + ", Postcode" + i);
				stmtPop.executeUpdate();
			}
			
			//Populate NextOfKinContact table
			stmtPop = conn.prepareStatement("INSERT INTO NextOfKinContact(studentID, name, eMailAddress, postalAddress)" +
										"VALUES(?, ?, ?, ?)");
			
			for(int i = 0; i < 100; i++){
				stmtPop.setInt(1, i);
				stmtPop.setString(2, "name" + i);
				stmtPop.setString(3, "email" + i + "@" + "gmail.com");
				stmtPop.setString(4, "differenthouse " + rand.nextInt(40) + ", DiffStreet" + i + ", City(different one)" + i + ", Postcode" + i +i*2);
				stmtPop.executeUpdate();
			}
			
			//Populate LecturerContact table
			stmtPop = conn.prepareStatement("INSERT INTO LecturerContact(lecturerID, office, eMailAddress)" +
										"VALUES(?, ?, ?)");
			for(int i = 0; i < 5; i++){
				stmtPop.setInt(1, i);
				stmtPop.setInt(2, 1 + rand.nextInt(30));
				stmtPop.setString(3, "email" + i + i + "@" + "gmail.com");
				stmtPop.executeUpdate();
			}
						
			//Populate Tutor table
			stmtPop = conn.prepareStatement("INSERT INTO Tutor(studentID, lecturerID)" +
										"VALUES(?, ?)");
			stmtPop.setInt(1, 0);
			stmtPop.setInt(2, 0);
			stmtPop.executeUpdate();
			
			stmtPop.setInt(1, 1);
			stmtPop.setInt(2, 0);
			stmtPop.executeUpdate();
			
			stmtPop.setInt(1, 2);
			stmtPop.setInt(2, 0);
			stmtPop.executeUpdate();
			
			stmtPop.setInt(1, 3);
			stmtPop.setInt(2, 0);
			stmtPop.executeUpdate();
			
			stmtPop.setInt(1, 4);
			stmtPop.setInt(2, 0);
			stmtPop.executeUpdate();
			
			for(int i = 5; i < 100; i++){
				stmtPop.setInt(1, i);
				stmtPop.setInt(2, rand.nextInt(4) + 1);
				stmtPop.executeUpdate();
			}
			
			
		}catch (SQLException e){
			e.printStackTrace();
		}

	}
	public static void main(String[] args){
		SetUp set = new SetUp();
		//set.makeTables();
		//set.populateTables();
		set.deleteTables(); //uncomment this to scrap the ready database
	}
	
}
