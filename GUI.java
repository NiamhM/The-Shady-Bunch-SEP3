import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window;

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
import javax.swing.JEditorPane;

public class GUI {
	private JFrame frame;
	private static PreferenceTable table;
	private static TreeMap <String, Integer> orderedProjects = new TreeMap<String, Integer>();
	private JTextField userInput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

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
		
		frame = new JFrame("Fourth Year Project Tool");
		frame.getContentPane().setForeground(new Color(240, 255, 255));
		frame.getContentPane().setBackground(new Color(235, 235, 235));
		frame.setBounds(100, 100, 450, 329); 	//location and size
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnProjectBy = new JButton("PROJECT LIST \r");
		btnProjectBy.setFont(new Font("Tw Cen MT", Font.PLAIN, 13));
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

				String allProjects = "The projects avaliable are: \n"; 
				for(int j = 0; j < allProjectsList.size(); j++){
					allProjects = allProjects + allProjectsList.get(j) + "\n";
				}

				Object[] whichProject = { "Preassigned", "Not-preassigned", "All" };
				Object selectedValue = JOptionPane.showInputDialog(null, "Choose one", "Chose a list",
						JOptionPane.INFORMATION_MESSAGE, null,
						whichProject, whichProject[0]);

				if(selectedValue.toString() =="Preassigned" /*&& listType.toString() == "By Populatity"*/){
					JOptionPane.showMessageDialog(null, preassignedProjects);
				}
				else if (selectedValue.toString() == "Not-preassigned" /*&& listType.toString() == "By Populatity"*/){
					JOptionPane.showMessageDialog(null, nonPreassignedProjects);
				}
				else if(selectedValue.toString() == "All" /*&& listType.toString() == "By Populatity"*/){
					JOptionPane.showMessageDialog(null, allProjects);
				}
			}
		});
		btnProjectBy.setBounds(291, 102, 133, 29);
		frame.getContentPane().add(btnProjectBy);

		JButton btnOrdredByPopularity = new JButton("Ordered by Popularity");
		btnOrdredByPopularity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.removePreAssignedPreferences();
				orderedProjects = table.getAllProjects();
				String[] numOrderedProjects = table.numericalSort(orderedProjects);
				String output = "The list of projects by popularity is: \n";
				for (int i = 0; i< numOrderedProjects.length; i++){
					//System.out.println(numOrderedProjects[i]);
					output = output + numOrderedProjects[i] + "\n";
				}
				JOptionPane.showMessageDialog(null, output);
			}
		});
		btnOrdredByPopularity.setFont(new Font("Tw Cen MT", Font.PLAIN, 11));
		btnOrdredByPopularity.setForeground(Color.BLUE);
		btnOrdredByPopularity.setBounds(274, 182, 150, 29);
		frame.getContentPane().add(btnOrdredByPopularity);

		JButton btnListOsStudents = new JButton("List of Students");
		btnListOsStudents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<String> allStudentsList = table.listOfStudentsNames();
				Vector<String> preassignedStudentsList = table.listOfPreAssignedStudents();
				Vector<String> notPreassignedStudentsList = table.listOfNonPreAssignedStudents();

				String allStudentsString = "The students are: \n";
				String preassignedStudentsString = "The students with preassigned projects are \n";
				String notPreassignedStudentsString = "The students without a preassigned projects are \n";

				JScrollPane scrollpane = new JScrollPane();
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

				if(listType.toString() == "Preassigned Students"){
					JOptionPane.showMessageDialog(null, preassignedStudentsString);
				}else if(listType.toString() == "Not preassigned Students"){
					JOptionPane.showMessageDialog(null, notPreassignedStudentsString);
				}else if(listType.toString() == "All Students"){
					JOptionPane.showMessageDialog(null,scrollpane, allStudentsString, JOptionPane.OK_OPTION);
				}
			}
		});
		btnListOsStudents.setForeground(Color.BLUE);
		btnListOsStudents.setBounds(291, 31, 133, 29);
		frame.getContentPane().add(btnListOsStudents);

		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setForeground(Color.RED);
		btnExit.setBounds(173, 237, 79, 42);
		frame.getContentPane().add(btnExit);

		JButton btnDefault = new JButton("Default");
		btnDefault.setForeground(new Color(192, 192, 192));
		btnDefault.setBounds(10, 102, 133, 29);
		frame.getContentPane().add(btnDefault);

		//		JLabel heading = new JLabel("Fourth Year Projet Mapper:");
		//		heading.setForeground(new Color(255, 255, 255));
		//		heading.setFont(new Font("Tahoma", Font.BOLD, 11));
		//		heading.setBounds(10, 0, 103, 14);
		//		frame.getContentPane().add(heading);

		JLabel lblAllocateProjects = new JLabel("Allocate Projects:");
		lblAllocateProjects.setForeground(new Color(255, 99, 71));
		lblAllocateProjects.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAllocateProjects.setBounds(15, 77, 103, 14);
		frame.getContentPane().add(lblAllocateProjects);

		JButton btnSimulatedAnnealing = new JButton("Simulated Annealing");
		btnSimulatedAnnealing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long startTime = System.nanoTime();
				SimulatedAnnealing SA = new SimulatedAnnealing(table);
				long endTime = System.nanoTime();
				long duration = (endTime - startTime)/1000000000;
				System.out.println("DURATION: " + duration + "seconds");
			}
		});
		btnSimulatedAnnealing.setForeground(new Color(255, 140, 0));
		btnSimulatedAnnealing.setBounds(10, 145, 150, 29);
		frame.getContentPane().add(btnSimulatedAnnealing);

		JButton btnGeneticAlgorithm = new JButton("Genetic Algorithm");
		btnGeneticAlgorithm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneticAlgSolver geneticSolver = new GeneticAlgSolver();

				int[] results = geneticSolver.compileResults();
				long startTime = System.currentTimeMillis();
				String energy = Integer.toString(geneticSolver.getEnergy());
				long endTime = System.currentTimeMillis();

				System.out.println("Total execution time: " + (endTime - startTime) );
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
						//						output = output + results[i] + " students got their 3rd choice \n";
					}
					else{
						output = output + percentage + "% of students got their "+ Integer.toString(i) +"th choice \n";
						//						output = output + results[i] + " students got their "+ Integer.toString(i) +"th choice \n";

					}
					total = total + num;
				}
				output = output + "The energy is " + energy;
				//output = output + "The total % is " + Float.toString(total);
				JOptionPane.showMessageDialog(null, output);
			}
		});
		btnGeneticAlgorithm.setForeground(new Color(60, 179, 113));
		btnGeneticAlgorithm.setBounds(10, 185, 150, 29);
		frame.getContentPane().add(btnGeneticAlgorithm);

		JLabel lblFileName = new JLabel("File name:");
		lblFileName.setBounds(10, 11, 79, 14);
		frame.getContentPane().add(lblFileName);

		userInput = new JTextField();
		userInput.setBounds(20, 35, 150, 20);
		frame.getContentPane().add(userInput);
		userInput.setColumns(10);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = userInput.getText() + ".tsv";
				initialize(input);
			}
		});
		btnEnter.setBounds(173, 56, 79, 20);
		frame.getContentPane().add(btnEnter);
		
		JLabel lbltsv = new JLabel(".tsv");
		lbltsv.setBounds(173, 38, 46, 14);
		frame.getContentPane().add(lbltsv);
	}
}
