public class CandidateAssignment {
	private String previousAssignment;
	private String currentAssignment;
	private StudentEntry studentO;
	private int energy;
	private int rank;
	

	public CandidateAssignment(StudentEntry student){
		studentO = student;
		randomiseAssignment();
	}
	
	public void randomiseAssignment(){
		previousAssignment = currentAssignment;		
		currentAssignment = studentO.getRandomPreference();
	}
	
	public void undoChange(){
		if(previousAssignment != null){
			currentAssignment = previousAssignment;
		}
	}
	
	public StudentEntry getStudentEntry(){
		return studentO;
	}
	
	public String getAssignedProject(){
		return currentAssignment ;
	}
	
	public int getEnergy(){
		rank = studentO.getRanking(getAssignedProject());
		rank = rank + 1;
		energy = rank*rank;
		return energy;
	}
}
