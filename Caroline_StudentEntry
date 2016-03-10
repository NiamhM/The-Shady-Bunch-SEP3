import java.util.Random;
import java.util.Vector;

public class StudentEntry {
	private String name;
	private boolean pre_arranged;
	private int numOfPref;
	private Vector<String> studentProjects = new Vector<String>();
	Vector<Vector<String>> myvector =  PreferenceTable.getPrefTable();
	private Random RND = new Random();
	
	public StudentEntry(String sname){
		name = sname;
		for(Vector<String> row: myvector){
			if(row.firstElement() == name){
				for(int i=0; i <row.size()-2; i++){ // - 2 to stop falling off the edge of the vector
					if(!(studentProjects.contains(row.elementAt(i+2)))){ //ensuring that we don't add duplicate projects
						studentProjects.add(row.elementAt(i+2).intern()); //i+2 to ignore the student number and preassigned project 
					}
				}
				if(row.elementAt(1).equalsIgnoreCase("Yes")){
					pre_arranged = true;
				}
				else{
					pre_arranged = false;
				}
			}	
		}
		numOfPref = studentProjects.size();
	}
	
	public String getStudentName(){
		return name;
	}
	
	public Vector<String> getOrderedPreference(){
		return studentProjects;
	}
	
	public boolean preassignProject(String pname){ //only has one preference and records it 
		if(pre_arranged && (studentProjects.size() == 1) && (studentProjects.firstElement().equalsIgnoreCase(pname))){
			return pre_arranged;
		}
		else return false;
	}
	
	public boolean hasPreassignedProject(){
		return pre_arranged;	
	}
	
	public int getNumberOfStatedPreferences(){
		return numOfPref; 
	}
	
	public void addPoject(String pname){
		for(int i= 0; i< studentProjects.size(); i++){
			String project = studentProjects.elementAt(i);
			if((!project.equalsIgnoreCase(pname)) && (studentProjects.size() < 10) ){ // wont add project if already in list or already has 10 projects
				studentProjects.add(pname.intern());
				break;
			}
		}
	}
	
	public String getRandomPreference(){
		int num = RND.nextInt(studentProjects.size()); //get random number between 0 and number of projects
		String project = studentProjects.get(num);
		return project;
	}
	
	public boolean hasPreference(String pref){
		if(studentProjects.contains(pref)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public int getRanking(String Project){
		int rank =0;
		if(studentProjects.contains(Project)){
			rank = studentProjects.indexOf(Project);
		}else{
			rank = -1;
		}
		return rank;
	}
	
}
