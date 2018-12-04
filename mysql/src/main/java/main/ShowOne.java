package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;

public class ShowOne extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowOne frame = new ShowOne();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ShowOne() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1050, 520);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		table.setBackground(new Color(255, 255, 240));
		table.setShowGrid(false);
		table.setEnabled(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"University"},
				{"Country"},
				{"ProgramName"},
				{"School"},
				{"Location"},
				{"Email"},
				{"Phone"},
				{"Homepage"},
				{"Degree"},
				{"DeadlineWithAid"},
				{"DeadlineWithoutAid"},
			},
			new String[] {
				"New column"
			}
		));
		table.setBounds(20, 15, 170, 385);
		table.setRowHeight(35);
		contentPane.add(table);
	}
}
