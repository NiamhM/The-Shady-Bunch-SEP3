import java.io.*;
import java.util.*;

public class Main {


	public static void main(String[] args) throws IOException{

		PreferenceTable P = new PreferenceTable();
		PreferenceTable.makeVector();
//		P.fillPreferencesOfAll(10);
		StudentEntry S =  P.getRandomStudent();
//		S.setOrderProjects(projects);
//
//		System.out.println("Name: " + S.getStudentName());
//		System.out.println("Student's random project: " + S.getRandomPreference());
//		System.out.println("Completely random project: "+ P.getRandomPreference());
//		System.out.println("Has a preassigned project? " + S.hasPreassignedProject());
//		System.out.println("Number of Stated Preferences: " + S.getNumberOfStatedPreferences());
//		System.out.println("Preferences: ");
//		S.addProject("How To Train Your Dragon");
//		for(int i =0; i < S.getOrderedPreferences().size(); i++){
//			System.out.print("[" + S.getOrderedPreferences().get(i) + "], ");
//		}
		CandidateAssignment C = new CandidateAssignment(S);
		System.out.println(C.getAssignedProject());
		C.undoChange();
		System.out.println(C.getAssignedProject());
		CandidateSolution CS = new CandidateSolution(P);
		CS.fillCandidateList();
		System.out.println(CS.getAssignmentFor("Jesus Christ"));
	}

}


