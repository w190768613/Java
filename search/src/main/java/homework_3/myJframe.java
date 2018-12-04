package homework_3;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import recommend.Recommender;
import recommend.RecommenderImpl;
import segmenter.ChineseSegmenterImpl;
import tf_idf.TF_IDFImpl;
import util.FileHandler;
import util.FileHandlerImpl;
import vo.StockInfo;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;

public class myJframe extends JFrame {

	private JPanel contentPane;
    private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnNewButton;
	private JTextField textField;
	private DefaultTableModel tablemodel;
	ChineseSegmenterImpl segmenter = new ChineseSegmenterImpl();
	TF_IDFImpl TF = new TF_IDFImpl();
	FileHandler fileHandler = new FileHandlerImpl();
	Recommender recommender = new RecommenderImpl();
	ShowCloud showcloud = new ShowCloud();
	
	//创建HashMap存储每个新闻对应的关键词集合
	static Map<String, List<String>> keyMap = new HashMap<>();
	//创建数组存储将要展示的10条新闻的ID
	int newsid[] = new int[10];
	//创建StockInfo类数组存储读取的60条新闻
	StockInfo[] stocks = new StockInfo[60];
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					myJframe frame = new myJframe();
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
	public myJframe() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 600);
		contentPane = new JPanel();
		contentPane.setMaximumSize(new Dimension(700, 600));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// Import按钮
		btnNewButton = new JButton("Import");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// 美化文件选择器
				if (UIManager.getLookAndFeel().isSupportedLookAndFeel()) {
					final String platform = UIManager.getSystemLookAndFeelClassName();
					if (!UIManager.getLookAndFeel().getName().equals(platform)) {
						try {
							UIManager.setLookAndFeel(platform);
						} catch (Exception exception) {
							exception.printStackTrace();
						}
					}
				}

				// 文件选择器
				JFileChooser fd = new JFileChooser();
				fd.showOpenDialog(null);
				File f = fd.getSelectedFile();
				if (f != null) {
					String str;
					StockInfo[] stockInfos = fileHandler.getStockInfoFromFile(f);
					for (int i = 0; i < 60; i++) {
						stocks[i] = new StockInfo();
						stocks[i] = stockInfos[i];
					}

					for (int i = 0; i < stocks.length; i++) {
						str = stocks[i].TITLE + stocks[i].CONTENT + stocks[i].ANSWER;
						List<String> seglist = segmenter.getWordsFromStockInfo(str); // 对每条新闻的title,content,answer进行分词
						List<String> list = TF.getResult2(seglist, stocks); // 得到每条新闻的关键词数组
						keyMap.put(stocks[i].ID, list); // 将每条新闻的ID和关键词数组存入keyMap中
					}
				}
			}
		});
		btnNewButton.setFont(new Font("Dialog", Font.BOLD, 25));
		btnNewButton.setBounds(5, 5, 140, 40);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		contentPane.setLayout(null);
		btnNewButton.setMinimumSize(new Dimension(50, 35));
		btnNewButton.setMaximumSize(new Dimension(50, 35));
		buttonGroup.add(btnNewButton);
		contentPane.add(btnNewButton);

		// 搜索框
		textField = new JTextField();
		textField.setBounds(40, 70, 480, 35);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		tablemodel = new DefaultTableModel(11, 4);
		final JTable table = new JTable(tablemodel);
		table.setShowGrid(false);
		table.addMouseListener(new MouseAdapter() {
			@Override 
			// 点击数据的某一行
			public void mouseClicked(MouseEvent e) {
				// 获取点击的行数
				int selectedrow = table.getSelectedRow();
				String id=newsid[selectedrow-2]+"";
				JFrame frame = new JFrame("cloud"); // 创建新窗体
				frame.getContentPane().setLayout(null);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 设置关闭方式为DISPOSE
				
				ShowCloud.Showcloud(keyMap.get(id)); //得到词云
				final File image = new File("data.png");
			
				BufferedImage img = null;
				try {
						img = ImageIO.read(image);
					} catch (IOException e2) {
						// TODO 自动生成的 catch 块
						e2.printStackTrace();
					}
				
				
				final JLabel backlabel = new JLabel(); // 用JLabel显示图片
				backlabel.setBounds(50, 30, 400, 400);
                ImageIcon icon = new ImageIcon(img);
               // ImageIcon icon = new ImageIcon();
				backlabel.setIcon(icon);
                
				//“导出图片”按钮
				JButton btnNewButton_2 = new JButton("导出图片");
				btnNewButton_2.setFont(new Font("Dialog", Font.BOLD, 25));
				btnNewButton_2.setBounds(180, 500, 140, 40);

				// 点击"导出图片"
				btnNewButton_2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						JFileChooser jf = new JFileChooser();
						jf.setFileSelectionMode(JFileChooser.SAVE_DIALOG | JFileChooser.DIRECTORIES_ONLY);
						jf.showDialog(null, null);
					//	File image = new File("data.png");
						File fi = jf.getSelectedFile();
						String f = fi.getAbsolutePath() + "data.png";
						File myfile = new File(f);
						try {
							BufferedImage img = ImageIO.read(image);
							ImageIO.write(img, "png", myfile);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});

				frame.getContentPane().add(backlabel);
				frame.getContentPane().add(btnNewButton_2);
				frame.setLocation(700, 600);
				frame.setSize(500, 600);
				frame.setVisible(true);
			}
		});
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setBounds(40, 140, 620, 400);
		table.setRowHeight(35);
		contentPane.add(table);

		// Search按钮
		JButton btnNewButton_1 = new JButton("Search");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
              //用表格显示搜索得到的10条新闻的关键信息
				
				newsid = recommender.calculate(textField.getText(), keyMap);

				table.setValueAt("ID", 0, 0);
				table.setValueAt("TITLE", 0, 1);
				table.setValueAt("CONTENT", 0, 2);
				table.setValueAt("ANSWER", 0, 3);
				for (int i = 0; i < 10; i++) {
					table.setValueAt(stocks[newsid[i]].ID, i + 1, 0);
					table.setValueAt(stocks[newsid[i]].TITLE, i + 1, 1);
					table.setValueAt(stocks[newsid[i]].CONTENT, i + 1, 2);
					table.setValueAt(stocks[newsid[i]].ANSWER, i + 1, 3);
				}
			}
		});
		btnNewButton_1.setFont(new Font("Dialog", Font.BOLD, 25));
		btnNewButton_1.setBounds(540, 70, 120, 35);
		contentPane.add(btnNewButton_1);
	}
}



	