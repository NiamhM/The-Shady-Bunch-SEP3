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

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JMenu;

public class GUI {
	private JFrame frame;
	private static PreferenceTable table = new PreferenceTable("Project allocation Data.TSV");
	private static TreeMap <String, Integer> orderedProjects = new TreeMap<String, Integer>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		CandidateSolution solution = new CandidateSolution(table);
		Vector<StudentEntry> allStudents = table.getAllStuderntEntries();
		orderedProjects = table.getAllProjects();
		table.fillPreferencesOfAll(10, orderedProjects);
		table.getAllProjects();
		table.getAllStuderntEntries();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
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
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(240, 255, 255));
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 450, 329);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnProjectBy = new JButton("Preassigned \r");
		btnProjectBy.setFont(new Font("Tw Cen MT", Font.PLAIN, 11));
		btnProjectBy.setForeground(Color.BLUE);
		btnProjectBy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> list = new ArrayList<>();
				list = table.listProjects("Preassigned");

				for(int i = 0; i < list.size(); i++){
					System.out.println(list.get(i));
				}
			}
		});
		btnProjectBy.setBounds(291, 102, 133, 29);
		frame.getContentPane().add(btnProjectBy);

		JLabel lblListsOfProjects = new JLabel("Lists of Projects:");
		lblListsOfProjects.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblListsOfProjects.setForeground(new Color(255, 99, 71));
		lblListsOfProjects.setBounds(291, 77, 103, 14);
		frame.getContentPane().add(lblListsOfProjects);

		JButton btnNonpreassignedProjects = new JButton("Non-preassigned ");
		btnNonpreassignedProjects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> list = new ArrayList<>();
				list = table.listProjects("Non-Preassigned");

				for(int i = 0; i < list.size(); i++){
					System.out.println(list.get(i));
				}
			}
		});
		btnNonpreassignedProjects.setForeground(Color.BLUE);
		btnNonpreassignedProjects.setFont(new Font("Tw Cen MT", Font.PLAIN, 11));
		btnNonpreassignedProjects.setBounds(291, 142, 133, 29);
		frame.getContentPane().add(btnNonpreassignedProjects);

		JButton btnAll = new JButton("All");
		btnAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> list = new ArrayList<>();
				list = table.listProjects("All");

				for(int i = 0; i < list.size(); i++){
					System.out.println(list.get(i));
				}
			}
		});
		btnAll.setForeground(Color.BLUE);
		btnAll.setFont(new Font("Tw Cen MT", Font.PLAIN, 11));
		btnAll.setBounds(291, 222, 133, 29);
		frame.getContentPane().add(btnAll);

		JButton btnOrdredByPopularity = new JButton("Ordred by Popularity");
		btnOrdredByPopularity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				orderedProjects = table.getAllProjects();
				String[] numOrderedProjects = table.numericalSort(orderedProjects);
				for (int i = 0; i< numOrderedProjects.length; i++){
					System.out.println(numOrderedProjects[i]);
				}
			}
		});
		btnOrdredByPopularity.setFont(new Font("Tw Cen MT", Font.PLAIN, 11));
		btnOrdredByPopularity.setForeground(Color.BLUE);
		btnOrdredByPopularity.setBounds(291, 182, 133, 29);
		frame.getContentPane().add(btnOrdredByPopularity);

		JButton btnListOsStudents = new JButton("List of Students");
		btnListOsStudents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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

		JButton btnReset = new JButton("RESET");
		btnReset.setForeground(Color.PINK);
		btnReset.setBounds(10, 250, 63, 29);
		frame.getContentPane().add(btnReset);

		JButton btnDefault = new JButton("Default");
		btnDefault.setForeground(new Color(192, 192, 192));
		btnDefault.setBounds(10, 102, 133, 29);
		frame.getContentPane().add(btnDefault);

		JLabel lblAllocateProjects = new JLabel("Allocate Projects:");
		lblAllocateProjects.setForeground(new Color(255, 99, 71));
		lblAllocateProjects.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAllocateProjects.setBounds(10, 77, 103, 14);
		frame.getContentPane().add(lblAllocateProjects);

		JButton btnEnterNewFile = new JButton("Enter New File");
		btnEnterNewFile.setForeground(new Color(138, 43, 226));
		btnEnterNewFile.setBounds(10, 31, 133, 29);
		frame.getContentPane().add(btnEnterNewFile);

		JLabel label = new JLabel("");
		label.setBounds(54, 38, 46, 14);
		frame.getContentPane().add(label);
		
		JButton btnSimulatedAnnealing = new JButton("Simulated Annealing");
		btnSimulatedAnnealing.setForeground(new Color(255, 140, 0));
		btnSimulatedAnnealing.setBounds(10, 145, 133, 29);
		frame.getContentPane().add(btnSimulatedAnnealing);
		
		JButton btnGeneticAlgorithm = new JButton("Genetic Algorithm");
		btnGeneticAlgorithm.setForeground(new Color(60, 179, 113));
		btnGeneticAlgorithm.setBounds(10, 185, 133, 29);
		frame.getContentPane().add(btnGeneticAlgorithm);
	}
}
