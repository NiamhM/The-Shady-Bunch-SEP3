import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class CandidateSolution{
	private PreferenceTable P;
	private CandidateAssignment C;
	private StudentEntry S;
	private ArrayList<CandidateAssignment> candidateList = new ArrayList<CandidateAssignment>();
	private Random RAN = new Random();

	public CandidateSolution(PreferenceTable pref){
		P = pref;
	}

	public String  getAssignmentFor(String sname){
		StudentEntry student = P.getStudentEntryFor(sname);
		if(student.hasPreassignedProject()){
		return null;
	}
	return student.getRandomPreference();
	}

	public void getRandomAssignment(){
		S.getRandomPreference();
	}

	public void fillCandidateList(){
		for(int count = 0; count <  P.getAllStudentEntries().size(); count++){
			candidateList.add(new CandidateAssignment(P.getAllStudentEntries().get(count)));
		}
	}
}
