import java.util.Hashtable;
import java.util.Vector;

public class CandidateSolution {
	private PreferenceTable myTable;
	private Vector<CandidateAssignment> candidateVector = new Vector<CandidateAssignment>();
	private Vector<StudentEntry> studentVector = new Vector<StudentEntry>();
	private int energy = 0;	
	
	public CandidateSolution(PreferenceTable tableName){
		myTable = tableName;
		studentVector = myTable.getAllStudentEntries();
		for(StudentEntry row : studentVector){
			CandidateAssignment name = new CandidateAssignment(row);
			candidateVector.add(name);
		}
	}
	
	public String getAssingmentFor(String studentName){
		StudentEntry student = new StudentEntry(studentName);
		String assignment = "";
			
		for(CandidateAssignment cand : candidateVector){
			if(cand.getStudentEntry().getStudentName().equals(student.getStudentName())){
				assignment = cand.getAssignedProject();
			}
		}
		return assignment;
	}
	
	public String getRandomAssignment(){
		StudentEntry student = myTable.getRandomStudent();
		String assignment = "";
		for(CandidateAssignment cand : candidateVector){
			if(cand.getStudentEntry().getStudentName().equals(student.getStudentName())){
				assignment = cand.getAssignedProject();
			}
		}			
		return assignment;
	}
	
	public int getEnergy(){
		Hashtable checkAssignments = new Hashtable();
		int penalty = 1000;
		for(CandidateAssignment cand : candidateVector){
			checkAssignments.put(cand.getAssignedProject(), cand.getStudentEntry() );
			energy = energy + cand.getEnergy();
		}
	
		int numberOfPenos = candidateVector.size() - checkAssignments.size(); //to calculate the energy including penalties take the size of the candidate vector minus the size of the check assignments
		//we can do this as we know the definite size of the candidate vector and as for check assignments that will overwrite each entry if a duplicate project is picked
		int totalPenalties = penalty*numberOfPenos;
		energy = energy + totalPenalties;
		return energy;
	}
	
	public float getFitness(){
		float energy = getEnergy();
		float fitness;
		fitness = 1/energy;
		return fitness;
	}
}
