import java.io.*;
import java.util.*;

public class PreferenceTable {
	private File file_name; //file name as type file
	private static Vector<Vector<String>> vectorTable; //this creates a vector of vectors called vector table
	int i = 0; //i and j counters for vector
	int j = 0;
	private Hashtable studentLookUp = new Hashtable();
	private Random RND = new Random();
	private Vector<StudentEntry> entryForStudents = new Vector<StudentEntry>(); 
		
	public PreferenceTable(String file){ //creates a constructor along with the file_name and vectorTable
		file_name = new File(file); //this creates a variable file_name of type file using the string that is provided in main
		vectorTable = new Vector<Vector<String>>();
		vectorTable = LoadContentFromFile(file_name); //this means that the vector table returned from preference table is stored in global vector table
	}
	
	public PreferenceTable(){ //creates a blank constructor
		
	}
	
	public static Vector<Vector<String>>getVectorTable(){
		return vectorTable;
	}
		
	private Vector<Vector<String>> LoadContentFromFile(File file_name){ //makes the method loadContentFromFile returns the type vector of vectors.
		try{
			FileInputStream stream = new FileInputStream(file_name);
			BufferedReader input = new  BufferedReader(new InputStreamReader(stream));
			String line = ""; //these three lines break the inputed file into blocks
			
			while((line = input.readLine()) != null){ //while there is another line to read
				StringTokenizer tokens = new StringTokenizer(line , "\t"); //split the time by tab key  
				vectorTable.add(i, new Vector()); //next four lines add the file into my vector of vectors 
				while(tokens.hasMoreTokens()){ 
					String next = tokens.nextToken();
					vectorTable.get(i).add(j, next);
					j = j + 1;
				}
				j = 0; //need to reset j here to make counter reset so it goes onto the next column in my vector
				i++;
			}			
		}catch(Exception e){
			System.out.println("There was an error while reading your file!!"); //throws exception
		}
		return vectorTable; //Returns vectorTable
	}
	
	public Hashtable createStudentHashTable(){
		String studentName;
		for(Vector<String> table : vectorTable){
			studentName = table.firstElement();
			studentLookUp.put(studentName, new StudentEntry(studentName));
		}
		return studentLookUp;	
	}		
	
	public Vector<StudentEntry> getAllStudentEntries(){
		for(Vector<String> table : vectorTable){
			String studentName = table.firstElement();
			entryForStudents.add(new StudentEntry(studentName));
		}
		return entryForStudents;
	}
	
	public StudentEntry getEntryFor(String studentname){
		createStudentHashTable();
		StudentEntry student = (StudentEntry)studentLookUp.get(studentname);
		return student;
	}
	
	public StudentEntry getRandomStudent(){
		int randomNumber = RND.nextInt(vectorTable.size()) + 1;
		Vector<String> rowInTable = vectorTable.get(randomNumber); //gets a random number of a row in the table
		String randomName = rowInTable.get(0); // and then gets the name from that row
		StudentEntry randomStudent = getEntryFor(randomName);
		return randomStudent;		
	}
	
	public String getRandomPreference(){
		Vector<String> listOfProjects;
		String randomProject;
		StudentEntry randomStudent = getRandomStudent(); 
		listOfProjects = randomStudent.getOrderedPreferences();
		int randomNumber = RND.nextInt(listOfProjects.size()); //this gets a random number from the size of the preferences from a random student
		randomProject = listOfProjects.get(randomNumber);
		return randomProject;
	}
	
	public void fillPreferenceOfAll(int maxPrefs){
		int maxPreferences = maxPrefs;
		for(Vector<String> row : vectorTable){
			String nameOfStudent = row.firstElement();
			StudentEntry studentData = new StudentEntry(nameOfStudent);
			Vector<String> preferences; //need this vector<String> to get the size of each students preference
			preferences = studentData.getOrderedPreferences();
			if(preferences.size() < maxPreferences && studentData.isPreAssigned == false){ //if the project is not preassigned and the size is less than max preferences add project
				String randomProject = getRandomPreference();
				studentData.addProject(randomProject);					
			}
		}			
	}	
	
	public static void main(String[] args){
		PreferenceTable myTable = new PreferenceTable("Project allocation data.tsv"); //creates a new instance of preference table using the file provided. 
		
		CandidateSolution sol = new CandidateSolution(myTable);
		StudentEntry student = myTable.getEntryFor("Hellboy");
		CandidateAssignment cand = new CandidateAssignment(student);
		String cand2 = cand.getAssignedProject();
		System.out.println("Test for assigned project for a particular student: " + cand2);
		System.out.println("Test for get energy in cand Assignment: " + cand.getEnergy());
		
		String cand3 = sol.getRandomAssignment();
		System.out.println("Test for get random assignment in cand solution: " + cand3);		
		System.out.println("Shows the energy in cand solution: " + sol.getEnergy());
		System.out.println("Test for fitness in cand solution: " + sol.getFitness());
		
	}
}