import java.util.Vector;
import java.util.Random;


public class StudentEntry {
	private String sName;
	private Vector<String> projects = new Vector<String>(); //original vector which get the projects of students
	private Vector<String> alteredProjects = new Vector<String>(); //this is a new vector which i will add projects to so i dont change the int value in numbe rof stated preferences method
	private Vector<Vector<String>> studentVector = new Vector<Vector<String>>();
	boolean isPreAssigned; //boolean to see if a project is already preassigned
	private Random RND = new Random();
	boolean hasPreference; //boolean to determine is a student has a particular preference
	boolean hasPreAssigned;
	private int rank;
	
	public StudentEntry(String nameofStudent){ //student constructor 
		sName = nameofStudent;	
		studentVector = PreferenceTable.getVectorTable(); 
		getOrderedPreferences();
	}
	
	public String getStudentName(){	
		return sName;		//simply returns the student name
	}
	
	public Vector<String> getOrderedPreferences(){
		//System.out.println(studentVector);
		for(Vector<String> row : studentVector){
			if(row.firstElement() == sName){
				for(int i = 0; i < row.size() - 2; i++){
					projects.add(i,row.elementAt(i+2)); //this puts all the project titles that have been put down for a student
				}
		}
	}
		return projects;
} //returns the original preferences put down by a student
	
	public boolean hasPreAssignedProject(){
		for(Vector<String> row : studentVector){
			if(row.firstElement() == sName){
				if(row.elementAt(1).equalsIgnoreCase("Yes")){ // checks to see if the project is preAssigned
					isPreAssigned = true;
				}
				else{
					isPreAssigned = false;
				}
			}
		}
		return isPreAssigned;
	}
	
	public boolean preAssignedProject(String preAssignedProject){
		if(isPreAssigned ==  true && (projects.size() == 1) && (projects.firstElement().equalsIgnoreCase(preAssignedProject))){ //checks if a particular project title is pre-assigned or not
			hasPreAssigned = true;
		}
		
		else{
			hasPreAssigned = false;
		}
		
		return hasPreAssigned;
	}

	
	public int getNumberOfStatedPreferences(){
		System.out.println("The original number of preferences given by this student was: ");
		int original_size = projects.size();
		return original_size; //method that gets the original number of preferences by a student
	}
	
	public Vector<String> addProject(String projectName){ //adds a project to a student object if the dont have 10 prefernces or a preassigned project
		alteredProjects = getOrderedPreferences();
		
		if(alteredProjects.size() < 10 && isPreAssigned == false){
			if(alteredProjects.contains(projectName)){
				//if project is already in the students preferences do nothing
			}
			
			else{
				projectName.intern();
				alteredProjects.add(projectName);
			}
		}
		return alteredProjects;
	}
	
	public String getRandomPreference(){
		String randomProject;
//		System.out.println(sName);
//		System.out.println("project size " + projects.size());
		int randomNumber = RND.nextInt(projects.size());
		randomProject = projects.get(randomNumber);
		//System.out.println(randomProject);
		return randomProject;
	}
	
	public boolean hasPreference(String preference){
		String Preference = preference;
		if(projects.contains(Preference)){
			hasPreference = true;
		}
		
		else{
			hasPreference = false;
		}
		
		return hasPreference;
	}
	
	public int getRanking(String projectName){
		boolean hasPreAssigned;
		boolean isPreAssigned;
		hasPreAssigned = hasPreAssignedProject();
		
		if(hasPreAssigned == true){
			isPreAssigned = preAssignedProject(projectName);
			if(isPreAssigned = true){
				rank = 0;
			}
		}
		
		else if(hasPreAssigned == false){
			if(projects.contains(projectName)){
				rank = projects.indexOf(projectName);
			}
		}
		
		else{
			rank = -1;
		}
		
		return rank;
	}
	
}
