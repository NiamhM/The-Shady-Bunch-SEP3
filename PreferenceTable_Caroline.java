import java.io.*;
import java.util.*;
public class PreferenceTable {
	
	private static Vector<Vector<String>> myVector; 
	private static Hashtable<String, StudentEntry> studentLookUp = new Hashtable<String, StudentEntry>();
	private Random RND = new Random();
	public PreferenceTable() { 

	}

	public PreferenceTable(String fileName) /*throws IOException*/ {
	myVector = loadContentFromFlie(fileName);
	}
	
	public static Vector<Vector<String>> getPrefTable(){
		return myVector;	
	}

	private Vector<Vector<String>> loadContentFromFlie(String fileName) /*0throws IOException*/ {
		Vector<Vector<String>> myVector = new Vector<Vector<String>>(); //declaring a 2D vector
		String nextLine, nextToken;
		int i=0;

		try{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader buffReader = new BufferedReader(fileReader); 
			nextLine = buffReader.readLine(); //// added to remove fist line of file
			while((nextLine = buffReader.readLine()) != null){ //reads in each line in the file
				StringTokenizer tokens = new StringTokenizer(nextLine, "\t"); //breaks the line into tokens seperated by tabs
				myVector.add(i,new Vector<String>()); //crates 2D vector, adds a vector to each element in the vector

				int numOfTokens = tokens.countTokens();
				for(int j = 0; j  < numOfTokens; j++){
					nextToken = tokens.nextToken().intern();   	//.intern to make strings pointer equvilant 
					myVector.get(i).add(j,nextToken);	//adds next token to column 
				}
				i++;
			}

		} catch (FileNotFoundException e) {
			System.err.println("File Not Found");
			e.printStackTrace(); //prints where error has occurred 
		}catch (IOException e){
			System.err.println("No next line");
			e.printStackTrace();
		}
		return myVector;
	}
		
	public Vector<StudentEntry> getAllStuderntEntries(){ //returns hash table
		Vector<StudentEntry> allStudents = new Vector<StudentEntry>();
		for(Vector<String> row: myVector){
			String name = row.firstElement();
			StudentEntry student = (StudentEntry) studentLookUp.get(name);
			allStudents.add(student);
		}
		return allStudents;
	}
	
	public StudentEntry getEntryFor(String sname){
		try{
		StudentEntry student = (StudentEntry) studentLookUp.get(sname); //gets StudentEntry from the hash table, reads it into a variale and returns
		return student;
		}
		catch(NullPointerException e){
			return null;
		}
	}
	
	public StudentEntry getRandomStudent(){
		Vector<StudentEntry> allStudents = getAllStuderntEntries();
		int num = RND.nextInt(allStudents.size());
		StudentEntry randomStudent = allStudents.get(num); 
		return randomStudent;
	}
	
	public String getRandomPreference(){
		StudentEntry student = getRandomStudent();
		while(student.hasPreassignedProject()){  
			student = getRandomStudent();
		}
		String project = student.getRandomPreference();
		return project;
	}
	
	public void fillPreferencesOfAll(int maxPrefs){
		Vector<StudentEntry> allStudents = getAllStuderntEntries();
		for(StudentEntry student: allStudents){
			Vector<String> prefs = student.getOrderedPreference();
			if(student.hasPreassignedProject() == false){ //if they dont have a preassigned project
				while(prefs.size() < maxPrefs){
					student.addPoject(getRandomPreference());
				}
			}	
		}
	}
	
	public void addToHash(String name){
		studentLookUp.put(name, new StudentEntry(name));
	}
	
	public Hashtable<String, StudentEntry> getHashtable(){
		return studentLookUp;
	}
	
	public static void main(String[] args) throws IOException {
		PreferenceTable table = new PreferenceTable("Project allocation Data.TSV");
		CandidateSolution solution = new CandidateSolution(table);
		table.fillPreferencesOfAll(10);		
		StudentEntry student = new StudentEntry("Hellboy");
		CandidateAssignment cand = new CandidateAssignment(student);	
		
		System.out.println(student.getOrderedPreference());
		
		System.out.println("\nStudent name: "+ student.getStudentName());
		System.out.println("Ranking for project 'Recommending Movies Using Curated IMDb Lists' is: "+student.getRanking("Recommending Movies Using Curated IMDb Lists"));
		System.out.println("Ranking for project 'Spotting Plagiarised Essays Economically' is: "+student.getRanking("Spotting Plagiarised Essays Economically"));
		System.out.println("Ranking for project 'Learning to play Whist by Bayesian Network' is: "+student.getRanking("Learning to play Whist by Bayesian Network  "));		
		System.out.println("Ranking for project 'Script Buddy' is: " + student.getRanking("Script Buddy") + "\n");
		
		System.out.println("CandidateAssignment name" + cand.getStudentEntry().getStudentName());
		System.out.println("Energy for assigned project " + cand.getAssignedProject() + " is: " + cand.getEnergy());
		System.out.println("Assigning new random project");
		cand.randomiseAssignment();
		System.out.println("\nEnergy for assigned project " + cand.getAssignedProject() + " is: " + cand.getEnergy());
		
		System.out.println("Energy of the Candidate Solution is: " + solution.getEnergy());
		System.out.println("Fitness of the Candidate Solution is: " + solution.getFitness());
	}
}
