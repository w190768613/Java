package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.javalite.activejdbc.Base;

import vo.Myprogram;
import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Jframe extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	Searcher searcher=new Searcher();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Jframe frame = new Jframe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}); 
	}
	
	
	/**
	 * Create the frame.
	 * @return 
	 */
	public  Jframe() {
		setTitle("Search");
		setResizable(false);
		setBackground(new Color(255, 255, 240));
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	   
		final String[] columnname={"University","Country","ProgramName","School","Location","Email","Phone","HomePage","Degree","DDLwithAid","DDLwithoutAid"};  //列名数组
	    final List<String> idlist = new ArrayList<>();  //存储项目的id
	    final String[] dictionary={"加州大学圣地亚哥分校 University of California--San Diego University of California San Diego  California University SanDiego California University San Diego california university san diego",
	    		"斯坦福StanfordUniversity stanford STANFORD","美国 US us USA Usa usa UNITED STATES United States UnitedStates America america",
	    		"计算机科学与技术Computer Science and Engineering cs CS computer COMPUTER Computer Engineering" ,};
	     final String[][] words={new String[]{"University of California--San Diego"},new String[]{"Stanford"},new String[]{"US","United States","America"},
new String[]{"Computer Science"," Computer Engineering" }};
 
	     final JButton cloudButton = new JButton("连接云端");  //提前声明"连接云端"按钮
         final JButton localButton = new JButton("连接本地");   //提前声明 "连接本地"按钮
		 
	     //下拉选择框
	     final JComboBox comboBox = new JComboBox();
	     comboBox.setBorder(null);
	     comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboBox.setBackground(new Color(255, 255, 240));
		comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"University", "Country", "Program"}));
		comboBox.setToolTipText("");
		comboBox.setBounds(93, 42, 150, 30);
		contentPane.add(comboBox); 
		
		// 表格
		final JTable table;
		DefaultTableModel tableModel = new DefaultTableModel();
		table = new JTable(tableModel) {
			public boolean isCellEditable(int row, int column) {
				return false;// 表格不允许被编辑
			}
		};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setShowGrid(false);
		table.setFillsViewportHeight(true);
		// 表格的鼠标点击事件
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				final int row = table.getSelectedRow();
				if(row!=-1){
				ShowOne frame = new ShowOne(); // 创建新窗体
				frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 设置关闭方式为DISPOSE
				
				frame.setTitle((String) table.getValueAt(row, 2));
				final JTable table2 = new JTable(); // 创建表格展示信息
				table2.setBackground(new Color(255, 255, 240));
				table2.setForeground(new java.awt.Color(255, 0, 0)); // 设置字体颜色
				table2.setShowGrid(false);
				table2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				table2.setModel(new DefaultTableModel(new Object[][] { { null }, { null }, { null }, { null }, { null },
						{ null }, { null }, { null }, { null }, { null }, { null }, }, new String[] { "New column" }));
				table2.setBounds(200, 15, 900, 385);
				table2.setRowHeight(35);
				frame.getContentPane().add(table2);
				table2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
				for (int i = 0; i < 11; i++)
					table2.setValueAt(table.getValueAt(row, i), i, 0); // 给表格table2添加内容
                
				//"Save"按钮
				JButton btnNewButton_1 = new JButton("Save");
				btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnNewButton_1.setBorder(null);
				if(localButton.isEnabled()) {  //连接云端数据库时
					 btnNewButton_1.setEnabled(false);  //禁用"Save"按钮
					 btnNewButton_1.setVisible(false);   //按钮不可见
					 table2.setEnabled(false);   //表格不可编辑
				}
				btnNewButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Myprogram changepro = Myprogram.findFirst("id= ?", idlist.get(row));
						for (int i = 0; i < table2.getRowCount(); i++)  table.setValueAt(table2.getValueAt(i, 0), row, i);
						
						changepro.set("university", table2.getValueAt(0, 0));
						changepro.set("country", table2.getValueAt(1, 0));
						changepro.set("program_name", table2.getValueAt(2, 0));
						changepro.set("school", table2.getValueAt(3, 0));
						changepro.set("location", table2.getValueAt(4, 0));
						changepro.set("email", table2.getValueAt(5, 0));
						changepro.set("phone_number", table2.getValueAt(6, 0));
						changepro.set("homepage", table2.getValueAt(7, 0));
						changepro.set("degree", table2.getValueAt(8, 0));
						changepro.set("deadline_with_aid", table2.getValueAt(9, 0));
						changepro.set("deadline_without_aid", table2.getValueAt(10, 0));
						changepro.saveIt();
						JOptionPane.showMessageDialog(table2, "纪录修改成功！", "保存成功", JOptionPane.PLAIN_MESSAGE);
					}
				});
				btnNewButton_1.setBackground(new Color(255, 255, 255));
				btnNewButton_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
				btnNewButton_1.setBounds(450, 420, 120, 40);
				frame.getContentPane().add(btnNewButton_1);
				frame.setVisible(true);
				}
			}
		});

		table.setCellSelectionEnabled(true);
		table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		table.setBackground(new Color(255, 255, 240));
		table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "University", "Country", "Program",
				"School", "Location", "Email", "Phone", "HomePage", "Degree", "DDLwithAid", "DDLwithoutAid" }));
		table.setBounds(0, 166, 882, 330);
		table.setRowHeight(35);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBackground(new Color(255, 255, 255));
		scrollPane.setBounds(0, 139, 1044, 373);
		contentPane.add(scrollPane);

		textField = new JTextField();
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		textField.setBounds(257, 42, 548, 30);
		contentPane.add(textField);
		textField.setColumns(10);

		
		// "Search"按钮
		JButton searchButton = new JButton("Search");
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchButton.setBorderPainted(false);
		searchButton.setBorder(null);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 清空表格
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(model.getRowCount() - 1);
				}
				idlist.removeAll(idlist);
				List<Myprogram> pros = null;
				String text=textField.getText();
				int mark=-1;
				for (int i = 0; i < dictionary.length; i++) {
					if (dictionary[i].contains(text)) {
						mark = i;
						i = dictionary.length;
					}
				}
				switch (comboBox.getSelectedIndex()) {
				case 0:   //下拉框选了university
					if (mark == -1)
						pros = Myprogram.where("university  like ? ", "%"+text+"%");
					else {
						for (int j = 0; j < words[mark].length; j++) {

							if (j == 0)
								pros = Myprogram.where("university  like ? ","%"+ words[mark][j]+"%");
							else {
								List<Myprogram> temp = Myprogram.where("university  like ? ", "%"+words[mark][j]+"%");
								pros.addAll(temp);
							}
						}
					}
					break;

				case 1:   //下拉框选了country
					if (mark == -1)
						pros = Myprogram.where("country like ? ", "%"+text+"%");
					else {
						for (int j = 0; j < words[mark].length; j++) {

							if (j == 0)
								pros = Myprogram.where("country  like ? ","%"+ words[mark][j]+"%");
							else {
								List<Myprogram> temp = Myprogram.where("country  like ? ", "%"+words[mark][j]+"%");
								pros.addAll(temp);
							}
						}
					}
					break;
				
				case 2:   //下拉框选了program
				if (mark == -1)
						pros = Myprogram.where("program_name  like? ", "%"+text+"%");
					else {
						for (int j = 0; j < words[mark].length; j++) {
							if (j == 0)
								pros = Myprogram.where("program_name  like ? ", "%"+words[mark][j]+"%");
							else {
								List<Myprogram> temp = Myprogram.where("program_name like ? ","%"+ words[mark][j]+"%");
								pros.addAll(temp);
							}
						}
					}
					break;
				}
				if (pros.size() == 0)
					JOptionPane.showMessageDialog(contentPane, "没有找到相关内容，换个关键词试试吧", "搜索失败", JOptionPane.ERROR_MESSAGE);
				else {
					for (int i = 0; i < pros.size(); i++) {
				
					DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				    tableModel.addRow(columnname);
					idlist.add((String) pros.get(i).get("id"));
					
					table.setValueAt(pros.get(i).get("university"), i, 0);
					table.setValueAt(pros.get(i).get("country"), i, 1);
					table.setValueAt(pros.get(i).get("program_name"), i, 2);
					table.setValueAt(pros.get(i).get("school"), i, 3);
					table.setValueAt(pros.get(i).get("location"), i, 4);
					table.setValueAt(pros.get(i).get("email"), i, 5);
					table.setValueAt(pros.get(i).get("phone_number"), i, 6);
					table.setValueAt(pros.get(i).get("homepage"), i, 7);
					table.setValueAt(pros.get(i).get("degree"), i, 8);
					table.setValueAt(pros.get(i).get("deadline_with_aid"), i, 9);
					table.setValueAt(pros.get(i).get("deadline_without_aid"), i, 10);
			  }
					table.changeSelection(0, 0, false, false);
		   }
			}
		});
		searchButton.setBackground(new Color(255, 255, 240));
		searchButton.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		searchButton.setBounds(812, 42, 150, 30);
		contentPane.add(searchButton);
		
		
		//"Add"按钮
		final JButton addButton = new JButton("Add");
		addButton.setBorderPainted(false);
		addButton.setBorder(null);
		addButton.setEnabled(false);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				 final int mark=idlist.size();
		    	 ShowOne frame = new ShowOne();   // 创建新窗体
				   frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 设置关闭方式为DISPOSE
			
				  frame.setTitle("添加新记录");
				  final JTable table2 = new JTable();  //创建表格展示信息
				  table2.setBackground(new Color(255, 255, 240));
				  table2.setForeground(new java.awt.Color(255, 0, 0));  //设置字体颜色
					table2.setShowGrid(false);
					table2.setModel(new DefaultTableModel(
						new Object[][] {
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
							{null},
						},
						new String[] {
							"New column"
						}
					));
					table2.setBounds(200, 15, 900, 385);
					table2.setRowHeight(35);
					frame.getContentPane().add(table2);
					table2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
					table2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				for(int i=0;i<11;i++) table2.setValueAt( columnname[i], i, 0);  //给表格添加内容
				//"确认添加"按钮
				final JButton btnNewButton_1 = new JButton("确认添加");
				btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnNewButton_1.setBorder(null);
				btnNewButton_1.addActionListener(new ActionListener() {  //点击按钮
					public void actionPerformed(ActionEvent arg0) {
					        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
					        tableModel.addRow(columnname);
							table.changeSelection(table.getRowCount()-1, 0, false, false);
							int row=table.getRowCount()-1;
					for(int i=0;i<table2.getColumnCount();i++) table.setValueAt(table2.getValueAt(i, 0), row, i);
							
							String uuid = UUID.randomUUID().toString().trim().replaceAll("-", ""); // 生成32位随机字母数字
							Myprogram addpro = new Myprogram();
							addpro.set("id", uuid);
							addpro.set("university", table2.getValueAt(0, 0));
							addpro.set("country", table2.getValueAt(1, 0));
							addpro.set("program_name", table2.getValueAt(2, 0));
							addpro.set("school", table2.getValueAt(3, 0));
							addpro.set("location", table2.getValueAt(4, 0));
							addpro.set("email", table2.getValueAt(5, 0));
							addpro.set("phone_number", table2.getValueAt(6, 0));
							addpro.set("homepage", table2.getValueAt(7, 0));
							addpro.set("degree", table2.getValueAt(8, 0));
							addpro.set("deadline_with_aid", table2.getValueAt(9, 0));
							addpro.set("deadline_without_aid", table2.getValueAt(10, 0));
							addpro.insert();
							idlist.add(uuid);
							btnNewButton_1.setText("已添加");
							btnNewButton_1.setEnabled(false);
							JOptionPane.showMessageDialog(table2, "记录添加成功！", "添加成功", JOptionPane.PLAIN_MESSAGE);
						
					}
				});  
				btnNewButton_1.setBackground(new Color(255, 255, 255));
				btnNewButton_1.setFont(new Font("楷体", Font.BOLD, 24));
				btnNewButton_1.setBounds(450, 420, 120, 40);
				frame.getContentPane().add(btnNewButton_1);  
			    frame.setVisible(true);
				}
		    });
		addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		addButton.setBackground(new Color(255, 255, 240));
		addButton.setBounds(300, 525, 120, 30);
		contentPane.add(addButton);
		
		
		// "Delete"按钮
		final JButton deleteButton = new JButton("Delete");
		deleteButton.setBorderPainted(false);
		deleteButton.setBorder(null);
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int rownum = table.getSelectedRow();
				model.removeRow(rownum);
				Myprogram delpro = Myprogram.findFirst("id= ?", idlist.get(rownum));
				delpro.delete();
			}
		});
		deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		deleteButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		deleteButton.setBackground(new Color(255, 255, 240));
		deleteButton.setBounds(600, 525, 120, 30);
		contentPane.add(deleteButton);
		
		
		// "写入云端"按钮
		final JButton writeButton = new JButton("写入云端");
		writeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		writeButton.setEnabled(false);
		writeButton.setBorder(null);
		writeButton.setBackground(new Color(255, 255, 240));
		writeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Base.hasConnection())
					Base.close();
				Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://119.27.166.115:2017/java_exp", "whu_iss_2017",
						"iss_java_2017");
				for (int i = 0; i < table.getRowCount(); i++) { // 遍历项目,将项目写入云端数据库
					Myprogram single = new Myprogram();
					single.set("id", idlist.get(i));
					single.set("university", table.getValueAt(i, 0));
					single.set("country", table.getValueAt(i, 1));
					single.set("school", table.getValueAt(i, 3));
					single.set("program_name", table.getValueAt(i, 2));
					single.set("homepage", table.getValueAt(i, 7));
					single.set("location", table.getValueAt(i, 4));
					single.set("email", table.getValueAt(i, 5));
					single.set("phone_number", table.getValueAt(i, 6));
					single.set("degree", table.getValueAt(i, 8));
					single.set("deadline_with_aid", table.getValueAt(i, 9));
					single.set("deadline_without_aid", table.getValueAt(i, 10));
					single.insert();

				} // 将项目的各项信息按照指定的格式写入数据库
				Base.close();
				JOptionPane.showMessageDialog(contentPane, "成功写入云端数据库！", "写入成功", JOptionPane.PLAIN_MESSAGE);
				writeButton.setEnabled(false);
			}
		});
		writeButton.setFont(new Font("楷体", Font.BOLD, 18));
		writeButton.setBounds(400, 80, 120, 30);
		contentPane.add(writeButton);
		
		
				// "启动爬虫"按钮
				final JButton spiderButton = new JButton("启动爬虫");
				spiderButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				spiderButton.setBorder(null);
				spiderButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						searcher.runspider();
						JOptionPane.showMessageDialog(contentPane, "信息爬取完成。信息已存入本地数据库，快开始搜索吧", "爬取完成",
								JOptionPane.PLAIN_MESSAGE);
						
						spiderButton.setEnabled(false);
					}
				});
				spiderButton.setBackground(new Color(255, 255, 240));
				spiderButton.setFont(new Font("楷体", Font.BOLD, 18));
				spiderButton.setBounds(260, 80, 120, 30);
				contentPane.add(spiderButton);
    
				
				
				localButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				localButton.setBorder(null);
				localButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (Base.hasConnection())
							Base.close();
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						while (model.getRowCount() > 0) {
							model.removeRow(model.getRowCount() - 1);
						}
						Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/program", "root", "sos7715813");

						List<Myprogram> pros = Myprogram.where("university = ? ", "University of California--San Diego");
						idlist.removeAll(idlist);
						for (int i = 0; i < pros.size(); i++) {
							DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

							tableModel.addRow(columnname);
							idlist.add((String) pros.get(i).get("id"));
							textField.setText("University of California--San Diego");
							table.setValueAt(pros.get(i).get("university"), i, 0);
							table.setValueAt(pros.get(i).get("country"), i, 1);
							table.setValueAt(pros.get(i).get("program_name"), i, 2);
							table.setValueAt(pros.get(i).get("school"), i, 3);
							table.setValueAt(pros.get(i).get("location"), i, 4);
							table.setValueAt(pros.get(i).get("email"), i, 5);
							table.setValueAt(pros.get(i).get("phone_number"), i, 6);
							table.setValueAt(pros.get(i).get("homepage"), i, 7);
							table.setValueAt(pros.get(i).get("degree"), i, 8);
							table.setValueAt(pros.get(i).get("deadline_with_aid"), i, 9);
							table.setValueAt(pros.get(i).get("deadline_without_aid"), i, 10);
						}
						writeButton.setEnabled(true);
						addButton.setEnabled(true);
						deleteButton.setVisible(true);
						deleteButton.setEnabled(true);
						cloudButton.setEnabled(true);
						 localButton.setEnabled(false);
						JOptionPane.showMessageDialog(contentPane, "连接到本地数据库！", "连接成功", JOptionPane.PLAIN_MESSAGE);
					}
				});
				localButton.setBackground(new Color(255, 255, 240));
				localButton.setFont(new Font("楷体", Font.BOLD, 18));
				localButton.setBounds(540, 80, 120, 30);
				contentPane.add(localButton);
				
				
		// "连接云端"按钮
		cloudButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cloudButton.setBorder(null);
		cloudButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Base.hasConnection())
					Base.close();
				Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://119.27.166.115:2017/java_exp", "whu_iss_2017",
						"iss_java_2017");
				textField.setText("");
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(model.getRowCount() - 1);
				}
				addButton.setEnabled(true);
				deleteButton.setEnabled(false);
				deleteButton.setVisible(false);
				writeButton.setEnabled(false);
				localButton.setEnabled(true);
				cloudButton.setEnabled(false);
				JOptionPane.showMessageDialog(contentPane, "连接到云端数据库！", "连接成功", JOptionPane.PLAIN_MESSAGE);
			}
		});
		cloudButton.setBackground(new Color(255, 255, 240));
		cloudButton.setFont(new Font("楷体", Font.BOLD, 18));
		cloudButton.setBounds(680, 80, 120, 30);
		contentPane.add(cloudButton);

	}
}
