public class CandidateAssignment {
	private String originalAssignment;
	private String newAssignment = "";
	private StudentEntry student;

	CandidateAssignment(StudentEntry s){
		student = s;
		randomizeAssignment();
	}

	public void randomizeAssignment(){
		originalAssignment = newAssignment;
		newAssignment = student.getRandomPreference();
	}

	public void undoChange(){
		if(originalAssignment != null){
			newAssignment = originalAssignment;
		}
	}

	StudentEntry getStudentEntry(){
		return student;
	}

	String getAssignedProject(){
		return newAssignment;
	}

}
