
public class CandidateAssignment {
	
	private String prevAssignment = "";
	private String currentAssignment = "";
	private StudentEntry student;
	
	public CandidateAssignment(StudentEntry studentE){
		 student = studentE;
		 randomiseAssignment();
	}
	
	public void randomiseAssignment(){
		prevAssignment = currentAssignment;
		currentAssignment = student.getRandomPreference();		
	}
	
	public String getAssignedProject(){
		return currentAssignment;
	}
	public void undoChange(){
		if(prevAssignment != ""){
			currentAssignment = prevAssignment;
		}
	}
	public StudentEntry getStudentEntry(){
		return student;
	}
	
	public int getEnergy(){
		int energy = student.getRanking(currentAssignment);
		energy = (energy+1)*(energy+1);
		return energy;
	}
}

