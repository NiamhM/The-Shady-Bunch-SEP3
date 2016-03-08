import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class StudentEntry {

	private ArrayList<String> orderedProjectsList = new ArrayList<String>();
	private String sName;
	private int numberOfProjects;
	private String hasPreassignedProj;

	public StudentEntry(String studentName){
		sName = studentName;
	}
	
	public void setHasPreAssigned(String hasPre){
		hasPreassignedProj = hasPre;
	}
	
	public void setOrderProjects(ArrayList<String> projects){
		orderedProjectsList = (ArrayList<String>) projects.clone();
	}

	String getStudentName(){
		return sName;
	}

	ArrayList<String> getOrderedPreferences(){
		return orderedProjectsList;
	}

	Boolean preassignProject(String pname){
		if(hasPreassignedProject() == true && orderedProjectsList.contains(pname)){
			return true;
		}
		return false;
	}

	boolean hasPreassignedProject(){
		Boolean hasproject = false;
		if(hasPreassignedProj.equalsIgnoreCase("Yes")){
			hasproject = true;
		}
		return hasproject;
	}

	int getNumberOfStatedPreferences(){
		return numberOfProjects;
	}

	void addProject(String pname){
		if(orderedProjectsList.size() < 10 && hasPreassignedProject() == false){
			orderedProjectsList.add(pname.intern());
		}
	}

	public void setStatedProjNo(int number) {
		numberOfProjects = number;
	}
	
	public String getRandomPreference(){
		Random RAN = new Random();
		return orderedProjectsList.get(RAN.nextInt(orderedProjectsList.size()));
	}

	public Boolean hasPreference(String preference){
		if(orderedProjectsList.contains(preference)){
			return true;
		}
		return false;
	}
}
