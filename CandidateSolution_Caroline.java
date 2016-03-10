import java.util.Hashtable;
import java.util.Vector;

public class CandidateSolution {
	PreferenceTable pref;
	private Vector<CandidateAssignment> allCands = new Vector<CandidateAssignment>();
	private static final int DUPLICATE_PROJECT_PENALTY = 1000;
	public CandidateSolution(PreferenceTable p){
		pref  = p;
		Vector<Vector<String>> allPrefs = PreferenceTable.getPrefTable(); 
		for(Vector<String> line : allPrefs){ 		//create hash table in preferenceTable
			pref.addToHash(line.firstElement());	//need hash to use getAllStudentEntries
		}
		
		Vector<StudentEntry> studentEntryList = pref.getAllStuderntEntries();
		for(StudentEntry student : studentEntryList){		//creating list of candidate assignments
			CandidateAssignment cand = new CandidateAssignment(student);
			allCands.addElement(cand);
		}
	}
	
	public String getAssignmentFor(String studentName){
		Hashtable<String, StudentEntry> myhash = pref.getHashtable();
		StudentEntry student = myhash.get(studentName);
		String assignment = "";
		for(CandidateAssignment can : allCands){
			if(can.getStudentEntry().equals(student)){
				assignment = can.getAssignedProject();
			}
		}
		return assignment;
	}
	
	public String getRandomAssignment(){
		StudentEntry randStudent = pref.getRandomStudent();
		return getAssignmentFor(randStudent.getStudentName());
	}
	
	public int getEnergy(){
		int energy =0;
		int penalty =0;
		String project;
		Hashtable<StudentEntry,String> hash = new Hashtable<StudentEntry,String>();
		
		for(CandidateAssignment cand: allCands){
			energy = energy + cand.getEnergy();
			project = cand.getAssignedProject();
			if(hash.containsValue(project)){ 
				penalty = penalty + DUPLICATE_PROJECT_PENALTY;
			}
			hash.put(cand.getStudentEntry(), cand.getAssignedProject());
		}
		energy = energy + penalty;
		return energy;
	}
	
	public float getFitness(){
		float energy = getEnergy();
		float fitness = 1/energy;
		return fitness;
	}
}

