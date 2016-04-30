import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.util.TreeMap;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;

public class GUI {
	private JFrame frame;
	private static PreferenceTable table = new PreferenceTable("Project allocation Data.TSV");
	private static TreeMap <String, Integer> orderedProjects = new TreeMap<String, Integer>();
	private JTextField userInputField;

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

	public GUI(String file) { 
		initialize(file);
	}

	private void initialize(String file) {
		String fileName = file;
		table = new PreferenceTable(fileName);
		CandidateSolution solution = new CandidateSolution(table);
		orderedProjects = table.getAllProjects();
		table.fillPreferencesOfAll(10, orderedProjects);
		table.getAllProjects();
		table.getAllStuderntEntries();

		frame = new JFrame("Fourth Year Project Allocator");
		frame.getContentPane().setForeground(new Color(240, 255, 255));
		frame.getContentPane().setBackground(new Color(235, 235, 235));
		frame.setBounds(100, 100, 452, 392); 	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnProjectLists = new JButton("Project Lists");
		btnProjectLists.setForeground(Color.BLACK);
		btnProjectLists.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<String> preassignedProjectList = table.listProjects("Preassigned");
				Vector<String> nonPreassignedProjectList = table.listProjects("Non-preassigned");
				Vector<String> allProjectsList  = table.listProjects("All");

				String preassignedProjects = "";
				for(int i = 0; i < preassignedProjectList.size(); i++){
					preassignedProjects = preassignedProjects + preassignedProjectList.get(i) + "\n";
				}

				String nonPreassignedProjects = "";
				for(int j = 0; j < nonPreassignedProjectList.size(); j++){
					nonPreassignedProjects = nonPreassignedProjects + nonPreassignedProjectList.get(j) + "\n";
				}

				String allProjects = ""; 
				for(int j = 0; j < allProjectsList.size(); j++){
					allProjects = allProjects + allProjectsList.get(j) + "\n";
				}
				try{
					Object[] whichProject = { "Preassigned", "Not-preassigned", "All" };
					Object selectedValue = JOptionPane.showInputDialog(null, "Choose one", "Chose a list",
							JOptionPane.INFORMATION_MESSAGE, null,
							whichProject, whichProject[0]);
					JTextArea textArea = new JTextArea(15,30);
					if(selectedValue.toString() =="Preassigned"){
						textArea.setText(preassignedProjects);
						textArea.setEditable(false);
						JScrollPane scrollPane = new JScrollPane(textArea);
						scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						JOptionPane.showMessageDialog(null, scrollPane, "preassigned Projects",  JOptionPane.INFORMATION_MESSAGE);
					}
					else if (selectedValue.toString() == "Not-preassigned"){
						textArea.setText(nonPreassignedProjects);
						textArea.setEditable(false);
						JScrollPane scrollPane = new JScrollPane(textArea);
						scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						JOptionPane.showMessageDialog(null, scrollPane, "Not-preassigned Projects",  JOptionPane.INFORMATION_MESSAGE);
					}
					else if(selectedValue.toString() == "All" ){
						textArea.setText(allProjects);
						textArea.setEditable(false);
						JScrollPane scrollPane = new JScrollPane(textArea);
						scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						JOptionPane.showMessageDialog(null, scrollPane, "All projects",  JOptionPane.INFORMATION_MESSAGE);
					}
				}catch(NullPointerException ex){
				}
			}
		});
		btnProjectLists.setBounds(28, 182, 161, 30);
		frame.getContentPane().add(btnProjectLists);

		JButton btnProjectsByPopularity = new JButton("Projects by Popularity");
		btnProjectsByPopularity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.removePreAssignedPreferences();
				orderedProjects = table.getAllProjects();
				String[] numOrderedProjects = table.numericalSort(orderedProjects);
				String output = "";
				for (int i = numOrderedProjects.length-1; i >=0; i--){
					output = output + numOrderedProjects[i] + "\n";
				}
				JTextArea textArea = new JTextArea(35, 55);
				textArea.setText(output);
				textArea.setEditable(false);
				JScrollPane scrollPane = new JScrollPane(textArea);
				scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				JOptionPane.showMessageDialog(null, scrollPane, "Projects by populatity",  JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnProjectsByPopularity.setForeground(Color.BLACK);
		btnProjectsByPopularity.setBounds(28, 240, 161, 30);
		frame.getContentPane().add(btnProjectsByPopularity);

		JButton btnStudentLists = new JButton("Student Lists");
		btnStudentLists.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<String> allStudentsList = table.listOfStudentsNames();
				Vector<String> preassignedStudentsList = table.listOfPreAssignedStudents();
				Vector<String> notPreassignedStudentsList = table.listOfNonPreAssignedStudents();

				String allStudentsString = "";
				String preassignedStudentsString = "";
				String notPreassignedStudentsString = "";

				for(int i = 0; i < allStudentsList.size(); i++){
					allStudentsString = allStudentsString + allStudentsList.get(i) + "\n";
				}
				for(int j = 0; j < preassignedStudentsList.size(); j++){
					preassignedStudentsString = preassignedStudentsString + preassignedStudentsList.get(j) + "\n";
				}
				for(int k = 0; k < notPreassignedStudentsList.size(); k++){
					notPreassignedStudentsString = notPreassignedStudentsString + notPreassignedStudentsList.get(k) + "\n";
				}
				try{
					Object[] whichType = { "Preassigned Students" , "Not preassigned Students", "All Students"};
					Object listType = JOptionPane.showInputDialog(null,"Choose one", "How would you like that list Sorted?",
							JOptionPane.INFORMATION_MESSAGE, null,
							whichType, whichType[0]);
					JTextArea textArea = new JTextArea(30, 15);
					if(listType.toString() == "Preassigned Students"){
						textArea = new JTextArea(15, 15);
						textArea.setText(preassignedStudentsString);
						textArea.setEditable(false);
						JScrollPane scrollPane = new JScrollPane(textArea);
						scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						JOptionPane.showMessageDialog(null, scrollPane, "Students with Preassigned Projects",  JOptionPane.INFORMATION_MESSAGE);

					}else if(listType.toString() == "Not preassigned Students"){
						textArea.setText(notPreassignedStudentsString);
						textArea.setEditable(false);
						JScrollPane scrollPane = new JScrollPane(textArea);
						scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						JOptionPane.showMessageDialog(null, scrollPane, "Students without Preassigned Projects",  JOptionPane.INFORMATION_MESSAGE);

					}else if(listType.toString() == "All Students"){
						textArea.setText(allStudentsString);
						textArea.setEditable(false);
						JScrollPane scrollPane = new JScrollPane(textArea);
						scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						JOptionPane.showMessageDialog(null, scrollPane, "All Students",  JOptionPane.INFORMATION_MESSAGE);
					}
				}catch(NullPointerException exc){

				}
			}
		});
		btnStudentLists.setForeground(Color.BLACK);
		btnStudentLists.setBounds(28, 127, 161, 30);
		frame.getContentPane().add(btnStudentLists);

		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setForeground(Color.RED);
		btnExit.setBounds(179, 300, 79, 42);
		frame.getContentPane().add(btnExit);

		JButton btnAllocateProjects = new JButton("Allocate Projects");
		btnAllocateProjects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runSimAnn();
			}
		});
		btnAllocateProjects.setForeground(Color.RED);
		btnAllocateProjects.setBounds(263, 30, 141, 42);
		frame.getContentPane().add(btnAllocateProjects);

		JLabel label = new JLabel("");
		label.setBounds(54, 38, 46, 14);
		frame.getContentPane().add(label);

		JButton btnSimulatedAnnealing = new JButton("Simulated Annealing");
		btnSimulatedAnnealing.createToolTip();
		btnSimulatedAnnealing.setToolTipText("Takes around 30-70 seconds");
		btnSimulatedAnnealing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runSimAnn();
			}
		});
		btnSimulatedAnnealing.setForeground(Color.BLACK);
		btnSimulatedAnnealing.setBounds(247, 127, 150, 30);
		frame.getContentPane().add(btnSimulatedAnnealing);

		JButton btnGeneticAlgorithm = new JButton("Genetic Algorithm");
		btnGeneticAlgorithm.createToolTip();
		btnGeneticAlgorithm.setToolTipText("Takes around 1:50 - 2:10 minutes");
		btnGeneticAlgorithm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneticAlgSolver geneticSolver = new GeneticAlgSolver(table);

				int[] results = compileResults(solution);
				CandidateSolution solution = geneticSolver.run();
				String energy = Integer.toString(geneticSolver.getEnergy());
				printResult(results,energy,solution);
			}
		});
		btnGeneticAlgorithm.setForeground(Color.BLACK);
		btnGeneticAlgorithm.setBounds(247, 182, 150, 30);
		frame.getContentPane().add(btnGeneticAlgorithm);

		userInputField = new JTextField();
		userInputField.setBounds(247, 250, 150, 20);
		frame.getContentPane().add(userInputField);
		userInputField.setColumns(10);

		JButton searchBtn = new JButton("Search");
		searchBtn.setForeground(Color.BLACK);
		searchBtn.setFont(new Font("Tahoma", Font.ITALIC, 7));
		searchBtn.createToolTip();
		searchBtn.setToolTipText("Search who wanted what");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String projectName = userInputField.getText();
				Vector<String> students = table.listStudentsByProject(projectName);
				String listOfStudents = "";

				for(int i = 0; i < students.size(); i++){
					listOfStudents = listOfStudents + students.get(i) + "\n";
				}
				JOptionPane.showMessageDialog(null, listOfStudents, "Students that chose:" + projectName, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		searchBtn.setBounds(336, 270, 59, 14);
		frame.getContentPane().add(searchBtn);

		JLabel lblChooseAnAlgorithm = new JLabel("Choose an algorithm to run:");
		lblChooseAnAlgorithm.setBounds(247, 109, 179, 14);
		frame.getContentPane().add(lblChooseAnAlgorithm);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 93, 416, 5);
		frame.getContentPane().add(separator);

		JButton btnFileSelection = new JButton("Choose New File");
		btnFileSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser openFile = new JFileChooser();
				openFile.showOpenDialog(null);

				try{
					File file = openFile.getSelectedFile();
					String fileName = file.getName();
					int index = fileName.lastIndexOf('.');
					String fileExtension = fileName.substring(index + 1);

					if(fileExtension.equals("tsv")){
						initialize(fileName);
					}
					else{
						JOptionPane.showMessageDialog(openFile, "Invalid choice, must be .tsv :(\nTry again or use the default:\nProject allocation data.tsv");
					}
				}catch(NullPointerException exception){
				}
			}
		});
		
		btnFileSelection.setBounds(10, 36, 150, 30);
		frame.getContentPane().add(btnFileSelection);

		JLabel lblDefaultFileProject = new JLabel("Default file: ");
		lblDefaultFileProject.setBounds(10, 11, 150, 14);
		frame.getContentPane().add(lblDefaultFileProject);

		JLabel lblProjectAllocationDatatsv = new JLabel("Project allocation data.tsv");
		lblProjectAllocationDatatsv.setBounds(10, 24, 150, 14);
		frame.getContentPane().add(lblProjectAllocationDatatsv);

		JLabel lblSearchWhichStudent = new JLabel("Search which students ");
		lblSearchWhichStudent.setBounds(247, 225, 179, 14);
		frame.getContentPane().add(lblSearchWhichStudent);

		JLabel lblChoseASpecific = new JLabel("chose a specific project:");
		lblChoseASpecific.setBounds(247, 235, 150, 17);
		frame.getContentPane().add(lblChoseASpecific);
	}
	private void printResult(int[] results, String energy,CandidateSolution solution){
		String percentage;
		String output = "The results are: \n";
		float total = 0;

		for(int i = 1; i < results.length; i++){ //start at one to ignore num of students
			float num = (float) results[i]*100/results[0];

			percentage = String.format("%.01f", num);//String.valueOf(num); //Integer.toString(results[i]/results[0]);
			if(i == 1){
				output = output + percentage + "% of students got their 1st choice \n";
			}
			else if(i == 2){
				output = output + percentage + "% of students got their 2nd choice \n";
			}
			else if(i == 3){
				output = output + percentage + "% of students got their 3rd choice \n";
			}
			else{
				output = output + percentage + "% of students got their "+ Integer.toString(i) +"th choice \n";
			}
			total = total + num;
		}
		output = output + "The energy is " + energy +"\n";
		output = output + "Would you like to save Results to a file ";
		try{
			Object[] whichType = {"Save" , "Discard"};
			Object listType = JOptionPane.showInputDialog(null,output, "Results",
					JOptionPane.QUESTION_MESSAGE, null,
					whichType, whichType[0]);
			if(listType =="Save"){
				save(solution);
			}
		}catch(NullPointerException exception){
		}
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
			CandidateSolution bestSolution = sa.saSolution();
			CandidateSolution solution;
			int bestEnergy = bestSolution.getEnergy();
			int currentEnergy;
			for(int i = 0; i < 9; i++){
				solution = sa.saSolution();
				currentEnergy = solution.getEnergy();

				if(currentEnergy < bestEnergy){
					bestSolution = solution;
					bestEnergy = currentEnergy;
				}
			}
				int[] results = compileResults(bestSolution);
				String energy = Integer.toString(bestEnergy);
				printResult(results,energy,bestSolution);
	}

	private void save(CandidateSolution solution){
		Vector<CandidateAssignment> assignments = solution.getAllCandiates();
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("Project Allocations.txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for(CandidateAssignment assignment : assignments){
			writer.println(assignment.toString());
		}
		JOptionPane.showMessageDialog(userInputField, "Results saved to:\nProject Allocations.txt");
		writer.close();
	}
}
