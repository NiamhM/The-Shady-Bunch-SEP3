/* Niamh Mc Eniff 13343846
 * Software Engineering Project 3 - Assignment1*/

import java.io.*;
import java.util.*;

public class PreferenceTable{

	private static Vector<Vector<String>> myVector=new Vector<Vector<String>>(); //Creates a vector that can take a vector. Global just to print :(
	private static int numberOfStatedProjects = 0;
	private static Hashtable<String, StudentEntry> studentHash = new Hashtable<String, StudentEntry>();
	private Random RAN = new Random();

	public PreferenceTable(String file){
		loadContentFromFile(file); 
	}
	
	public PreferenceTable(){}
	
	public static Vector<Vector<String>> getVector(){
		return myVector;
	}

	public static int getNumberOfStatedProjects(){
		return numberOfStatedProjects;
	}



	private static Vector<Vector<String>> loadContentFromFile(String file){
		FileReader fileName;
		BufferedReader bRead;

		try {
			fileName = new FileReader(file);
			bRead = new BufferedReader(fileName);
			String line;
			line = bRead.readLine();
			int column, row = 0;

			while(line != null){
				StringTokenizer tokens = new StringTokenizer(line, "\t");
				int	numTokens = tokens.countTokens();
				myVector.add(row, new Vector<String>()); //Adding the second vector to our vector

				for(column = 0; column <= numTokens && tokens.hasMoreTokens(); column++){
					String tokenColumn = tokens.nextToken();
					myVector.get(row).add(column, tokenColumn); //Adding content
				}
				line = bRead.readLine(); //Next line
				row++;
			}
		} 

		catch (FileNotFoundException e){ //If the file can't be found catch it
			System.err.println("ERROR: Couldn't read file :(");
			e.printStackTrace(); 
		}
		catch(IOException i){
			System.err.println("ERROR:IO Exception :(");
			i.printStackTrace(); 
		}
		return myVector;
	}

	ArrayList<StudentEntry> getAllStudentEntries(){
		ArrayList<StudentEntry> studentEntries = new ArrayList<>();
		for(Map.Entry<String, StudentEntry> entry: studentHash.entrySet()){
			studentEntries.add(entry.getValue());
		}
		return studentEntries;
	}

	StudentEntry getStudentEntryFor(String name){
		try{
			StudentEntry student = (StudentEntry)studentHash.get(name);
			return student;
		}

		catch(NullPointerException n){
			return null;
		}
	}

	static void makeVector() throws IOException{
		new PreferenceTable("Project allocation data.tsv");
		boolean isFirst = true;
		int counter = 0;
		Vector<Vector<String>> newVector = getVector();
		StudentEntry SE;
		for(Vector<String> row : newVector){
			String studentName = row.firstElement();

			if(isFirst == false){ //We don't want to take in the first row as it is just headings
				SE = new StudentEntry(studentName);
				String haspreassigned = row.elementAt(1);
				SE.setHasPreAssigned(haspreassigned);
				ArrayList<String> projects = new ArrayList<String>();
				int duplicateCounter = 0;

				while(counter < row.size() - 2){
					if(!(projects.contains(row.elementAt(counter + 2)))){ //Make sure no one, like Agent Phil, is being cheeky and adding duplicates
						projects.add(row.elementAt(counter + 2));
					}
					else{
						duplicateCounter++;
					}
					counter++;
				}

				SE.setStatedProjNo(counter - duplicateCounter);
				counter = 0;
				SE.setOrderProjects(projects);
				studentHash.put(studentName, SE);
			}
			isFirst = false;
		}
	}

	public StudentEntry getRandomStudent(){
		ArrayList<StudentEntry> E = getAllStudentEntries();
		int randNum = RAN.nextInt(myVector.size() - 1);
		return E.get(randNum);	
	}

	public String getRandomPreference(){
		StudentEntry randomStudent = getRandomStudent();
		while(getRandomStudent().hasPreassignedProject()){
			randomStudent = getRandomStudent();
		}
		return randomStudent.getRandomPreference();
	}

	public void fillPreferencesOfAll(int maxPrefs) {
		ArrayList<StudentEntry> E = getAllStudentEntries();
		for(int i = 0; i < myVector.size() - 1; i++){
			int prefNo = E.get(i).getNumberOfStatedPreferences();
			while(prefNo <= maxPrefs){
				String newProj = getRandomPreference();
				if(!(E.get(i).hasPreference(newProj))){
					E.get(i).addProject(newProj);
					prefNo++;
				}

			}
		}
	}
}
