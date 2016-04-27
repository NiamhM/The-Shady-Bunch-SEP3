import java.awt.EventQueue;

import javax.annotation.processing.FilerException;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Window;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JMenu;
import javax.swing.JSeparator;

public class GUI {
	private JFrame frame;
	private static PreferenceTable table = new PreferenceTable("Project allocation Data.TSV");
	private static TreeMap <String, Integer> orderedProjects = new TreeMap<String, Integer>();
	private JTextField userInput;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		CandidateSolution solution = new CandidateSolution(table);
//		Vector<StudentEntry> allStudents = table.getAllStuderntEntries();
//		//table.removePreAssignedPreferences();
//		orderedProjects = table.getAllProjects();
//		table.fillPreferencesOfAll(10, orderedProjects);
//		table.getAllProjects();
//		table.getAllStuderntEntries();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI("Project allocation data.tsv");
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI(String file) { 
		initialize(file);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String file) {
		String fileName = file;
		table = new PreferenceTable(fileName);
		CandidateSolution solution = new CandidateSolution(table);
		Vector<StudentEntry> allStudents = table.getAllStuderntEntries();
		//table.removePreAssignedPreferences();
		orderedProjects = table.getAllProjects();
		table.fillPreferencesOfAll(10, orderedProjects);
		table.getAllProjects();
		table.getAllStuderntEntries();
		
		frame = new JFrame("Fourth Year Project Allocator");
		frame.getContentPane().setForeground(new Color(240, 255, 255));
		frame.getContentPane().setBackground(new Color(235, 235, 235));
		frame.setBounds(100, 100, 452, 392); 	//location and size
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnProjectBy = new JButton("Project Lists");
		//btnProjectBy.setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
		btnProjectBy.setForeground(Color.BLACK);
		btnProjectBy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<String> preassignedProjectList = table.listProjects("Preassigned");
				Vector<String> nonPreassignedProjectList = table.listProjects("Non-preassigned");
				Vector<String> allProjectsList  = table.listProjects("All");
				
				String preassignedProjects = "The preassigned projects are: \n";
				for(int i = 0; i < preassignedProjectList.size(); i++){
					preassignedProjects = preassignedProjects + preassignedProjectList.get(i) + "\n";
				}
				
				String nonPreassignedProjects = "The Non-preassigned projects are: \n";
				for(int j = 0; j < nonPreassignedProjectList.size(); j++){
					nonPreassignedProjects = nonPreassignedProjects + nonPreassignedProjectList.get(j) + "\n";
				}
				
				String allProjects = "The projects available are: \n"; 
				for(int j = 0; j < allProjectsList.size(); j++){
					allProjects = allProjects + allProjectsList.get(j) + "\n";
				}
				
				 Object[] whichProject = { "Preassigned", "Not-preassigned", "All" };
				 Object selectedValue = JOptionPane.showInputDialog(null, "Choose one", "Chose a list",
				             JOptionPane.INFORMATION_MESSAGE, null,
				             whichProject, whichProject[0]);
				 
				if(selectedValue.toString() =="Preassigned"){
					JOptionPane.showMessageDialog(null, preassignedProjects);
				}
				else if (selectedValue.toString() == "Not-preassigned"){
					JOptionPane.showMessageDialog(null, nonPreassignedProjects);
				}
				else if(selectedValue.toString() == "All" ){
					JOptionPane.showMessageDialog(null, allProjects);
				}
			}
		});
		btnProjectBy.setBounds(28, 182, 161, 30);
		frame.getContentPane().add(btnProjectBy);
		
		JButton btnOrdredByPopularity = new JButton("Projects by Popularity");
		btnOrdredByPopularity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.removePreAssignedPreferences();
				orderedProjects = table.getAllProjects();
				String[] numOrderedProjects = table.numericalSort(orderedProjects);
				String output = "The list of projects by popularity is: \n";
				for (int i = 0; i< numOrderedProjects.length; i++){
					output = output + numOrderedProjects[i] + "\n";
				}
				JOptionPane.showMessageDialog(null, output);
			}
		});
		//btnOrdredByPopularity.setFont(new Font("Tw Cen MT", Font.PLAIN, 11));
		btnOrdredByPopularity.setForeground(Color.BLACK);
		btnOrdredByPopularity.setBounds(28, 240, 161, 30);
		frame.getContentPane().add(btnOrdredByPopularity);

		JButton btnListOsStudents = new JButton("Student Lists");
		btnListOsStudents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<String> allStudentsList = table.listOfStudentsNames();
				Vector<String> preassignedStudentsList = table.listOfPreAssignedStudents();
				Vector<String> notPreassignedStudentsList = table.listOfNonPreAssignedStudents();
				
				String allStudentsString = "The students are: \n";
				String preassignedStudentsString = "The students with preassigned projects are \n";
				String notPreassignedStudentsString = "The students without a preassigned projects are \n";
				
				//JScrollPane scrollpane = new JScrollPane();
				for(int i = 0; i < allStudentsList.size(); i++){
					allStudentsString = allStudentsString + allStudentsList.get(i) + "\n";
				}
				for(int j = 0; j < preassignedStudentsList.size(); j++){
					preassignedStudentsString = preassignedStudentsString + preassignedStudentsList.get(j) + "\n";
				}
				for(int k = 0; k < notPreassignedStudentsList.size(); k++){
					notPreassignedStudentsString = notPreassignedStudentsString + notPreassignedStudentsList.get(k) + "\n";
				}
				
				Object[] whichType = { "Preassigned Students" , "Not preassigned Students", "All Students"};
				Object listType = JOptionPane.showInputDialog(null,"Choose one", "How would you like that list Sorted?",
			             JOptionPane.INFORMATION_MESSAGE, null,
			             whichType, whichType[0]);
				//Component com = allStudentsString;
				if(listType.toString() == "Preassigned Students"){
					JOptionPane.showMessageDialog(null, preassignedStudentsString);
				}else if(listType.toString() == "Not preassigned Students"){
					JOptionPane.showMessageDialog(null, notPreassignedStudentsString);
				}else if(listType.toString() == "All Students"){
					JScrollPane pane = new JScrollPane();
					JOptionPane.showMessageDialog(null, allStudentsString);//, JOptionPane.OK_OPTION);
				}else{
					//JOptionPane.c
				}
			}
		});
		btnListOsStudents.setForeground(Color.BLACK);
		btnListOsStudents.setBounds(28, 127, 161, 30);
		frame.getContentPane().add(btnListOsStudents);

		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setForeground(Color.RED);
		btnExit.setBounds(318, 300, 79, 42);
		frame.getContentPane().add(btnExit);

		JButton btnDefault = new JButton("Allocate Projects");
		btnDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runSimAnn();
			}
		});
		btnDefault.setForeground(Color.RED);
		btnDefault.setBounds(290, 38, 136, 49);
		//btnDefault.setFont(new Font("Tahoma", Font.PLAIN, 13));
		frame.getContentPane().add(btnDefault);

		JLabel label = new JLabel("");
		label.setBounds(54, 38, 46, 14);
		frame.getContentPane().add(label);
		
		JButton btnSimulatedAnnealing = new JButton("Simulated Annealing");
		btnSimulatedAnnealing.createToolTip();
		btnSimulatedAnnealing.setToolTipText("Takes around 1:40 - 2 minutes");
		btnSimulatedAnnealing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runSimAnn();
			}
		});
		btnSimulatedAnnealing.setForeground(Color.BLACK);
		btnSimulatedAnnealing.setBounds(247, 156, 150, 30);
		frame.getContentPane().add(btnSimulatedAnnealing);
		
		JButton btnGeneticAlgorithm = new JButton("Genetic Algorithm");
		btnGeneticAlgorithm.createToolTip();
		btnGeneticAlgorithm.setToolTipText("Takes around 1:50 - 2:10 minutes");
		btnGeneticAlgorithm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneticAlgSolver geneticSolver = new GeneticAlgSolver(table);
				CandidateSolution solution = geneticSolver.run();
				int[] results = compileResults(solution);
				//final long startTime = System.currentTimeMillis();
				String energy = Integer.toString(geneticSolver.getEnergy());
				//final long endTime = System.currentTimeMillis();
				//System.out.println("Total execution time: " + (endTime - startTime) );
				printResult(results,energy);
			}
			});
		btnGeneticAlgorithm.setForeground(Color.BLACK);
		btnGeneticAlgorithm.setBounds(247, 211, 150, 30);
		frame.getContentPane().add(btnGeneticAlgorithm);
		
		
		
		JLabel lblFileName = new JLabel("File name:");
		lblFileName.setForeground(Color.BLUE);
		lblFileName.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFileName.setBounds(10, 13, 79, 14);
		frame.getContentPane().add(lblFileName);

		userInput = new JTextField();
		userInput.setBounds(10, 32, 150, 20);
		frame.getContentPane().add(userInput);
		userInput.setColumns(10);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.setForeground(Color.GRAY);
		btnEnter.setFont(new Font("Tahoma", Font.ITALIC, 7));
		btnEnter.createToolTip();
		btnEnter.setToolTipText("NOTE: Can only be pressed once!");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = userInput.getText() + ".tsv";
				initialize(input);
				btnEnter.setEnabled(false);
			}
		});
		btnEnter.setBounds(162, 56, 59, 14);
		frame.getContentPane().add(btnEnter);
		
		JLabel lbltsv = new JLabel(".tsv");
		lbltsv.setBounds(162, 38, 46, 14);
		frame.getContentPane().add(lbltsv);
		
		JLabel lblDefaultFileProject = new JLabel("Default file: Project Allocation Data");
		lblDefaultFileProject.setFont(new Font("Tahoma", Font.ITALIC, 8));
		lblDefaultFileProject.setBounds(10, 56, 179, 14);
		frame.getContentPane().add(lblDefaultFileProject);
		
		JLabel lblChooseAnAlgorithm = new JLabel("Choose an algorithm to run:");
		lblChooseAnAlgorithm.setBounds(247, 131, 179, 14);
		frame.getContentPane().add(lblChooseAnAlgorithm);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 97, 416, 5);
		frame.getContentPane().add(separator);
	}
	private void printResult(int[] results, String energy){
		String percentage;
		String output = "The results are: \n";
		float total = 0;
		
		for(int i = 1; i < results.length; i++){ //start at one to ignore num of students
			float num = (float) results[i]*100/results[0];
			 
			percentage = String.format("%.01f", num);//String.valueOf(num); //Integer.toString(results[i]/results[0]);
			if(i == 1){
				output = output + percentage + "% of students got their 1st choice \n";
				//output = output + results[i] + "  students got their 1st choice \n";
			}
			else if(i == 2){
				output = output + percentage + "% of students got their 2nd choice \n";
				//output = output + results[i] + " students got their 2nd choice \n";
			}
			else if(i == 3){
				output = output + percentage + "% of students got their 3rd choice \n";
//				output = output + results[i] + " students got their 3rd choice \n";
			}
			else{
				output = output + percentage + "% of students got their "+ Integer.toString(i) +"th choice \n";
//				output = output + results[i] + " students got their "+ Integer.toString(i) +"th choice \n";
			}
			total = total + num;
		}
		output = output + "The energy is " + energy;
		//output = output + "The total % is " + Float.toString(total);
		JOptionPane.showMessageDialog(null, output);
	}	
	
	public int[] compileResults(CandidateSolution bestSolution){
		//size 11 make first slot num of students then result/num of student = %
		int[] results = new int[11];
		Vector<CandidateAssignment> assignments = bestSolution.getAllCandiates();
		results[0] = assignments.size(); //get the number of students
		for(CandidateAssignment candidate: assignments){
			switch(candidate.getEnergy()){
			case 1: 
				results[1]++;
				break;
			case 4:
				results[2]++;
				break;
			case 9:
				results[3]++;
				break;
			case 16:
				results[4]++;
				break;
			case 25:
				results[5]++;
				break;
			case 36:
				results[6]++;
				break;
			case 49:
				results[7]++;
				break;
			case 64:
				results[8]++;
				break;
			case 81:
				results[9]++;
				break;
			case 100:
				results[10]++;
				break;
			}
		}
		return results;
	}
	
	private void runSimAnn(){
			SimulatedAnnealing sa = new SimulatedAnnealing(table);
			CandidateSolution solution = sa.saSolution();
			int[] results = compileResults(solution);
			String energy = Integer.toString(solution.getEnergy());
			printResult(results,energy);
		}
}
