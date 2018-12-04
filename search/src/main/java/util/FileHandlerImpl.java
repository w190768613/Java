package util;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import vo.StockInfo;



public class FileHandlerImpl implements FileHandler {

    /**
     * This func gets stock information from the given interfaces path.
     * If interfaces don't exit,or it has a illegal/malformed format, return NULL.
     * The filepath can be a relative path or a absolute path
     *
     * @param filePath
     * @return the Stock information array from the interfaces,or NULL
     */
    @Override
	// 
	public StockInfo[] getStockInfoFromFile(File file) {
    	
		StockInfo[] stock; // ����δ�����������մ�������
		StockInfo[] arrs = new StockInfo[61]; // ������ʱ�洢����
		String element[] = new String[8]; // �ݴ�һ�е�����
		for (int i = 0; i < 61; i++) arrs[i] = new StockInfo(); // ��ʼ��StockInfo������arrs
 

		try {
			String encoding = "UTF-8";
		
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// ���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);
				String line = "";
				int i = -1;
				while ((line = bufferedReader.readLine()) != null) {
					i++;
					element = line.split("\t");
					arrs[i].ID = element[0];
					arrs[i].TITLE = element[1];
					arrs[i].AUTHOR = element[2];
					arrs[i].DATE = element[3];
					arrs[i].LASTUPDATE = element[4];
					arrs[i].CONTENT = element[5];
					arrs[i].ANSWERAUTHOR = element[6];
					arrs[i].ANSWER = element[7];
					arrs[i].LINE = arrs[i].ID + "\t" + arrs[i].TITLE + "\t" + arrs[i].AUTHOR + "\t" + arrs[i].DATE
							+ "\t" + arrs[i].LASTUPDATE + "\t" + arrs[i].CONTENT + "\t" + arrs[i].ANSWERAUTHOR + "\t"
							+ arrs[i].ANSWER + "\n";
				}
		
				read.close();
			} else {
				System.out.println("�Ҳ���ָ�����ļ�");
				return null;
			}
		} catch (Exception e) {
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
			return null;
		}

		stock = new StockInfo[60];
		for (int j = 0; j < 60; j++) { // �����ݴ�arrs��ת�볤��Ϊ��������������stock��
			stock[j] = new StockInfo();
			stock[j] = arrs[j + 1];
		}
		return stock; // ����StockInfo������
	}

		

	
   

    /**
     * This function need write matrix to files
     *
     * @param matrix the matrix you calculate
     */
    @Override
  //�����ƶȾ���д���ļ� similar.txt
  	public void setCloseMatrix2File(double[][] matrix) {
  		try {
  			String filePath = "D:\\java program\\homework_2\\src\\main\\output\\similar.txt";
  			File f = new File(filePath);
  			if (f.exists()) {
  				System.out.println("�ļ�" + f + "����");
  			} else {
  				System.out.println("�����ļ�" + f);
  				f.createNewFile(); // �������򴴽�
  			}

  			FileWriter writer = new FileWriter(f, true);
  			String sss;
  			writer.write("���ƶȾ���"+"\r\n");
  			for (int i = 0; i < 60; i++) {
  				for (int j = 0; j < 60; j++) {
  					 sss = matrix[i][j] + "\t"; // ��double����תΪString��
  					writer.write(sss); // д���ļ�
  				}
  				writer.write("\r\n"); // ����
  			}
  			writer.close();
  			System.out.println("���ƶȾ�����д���ļ�" + f);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  	}

    /**
     * This function need write recommend to files
     *
     * @param recommend the recommend you calculate
     */
    @Override
    //���Ƽ�����д���ļ� rencommence.txt
  	public void setRecommend2File(double[][] recommend) {
  		try {
  			String filePath = "D:\\java program\\homework_2\\src\\main\\output\\recommence.txt";
  			File f = new File(filePath);
  			if (f.exists()) {
  				System.out.println("�ļ�" + f + "����");
  			} else {
  				System.out.println("�����ļ�" + f);
  				f.createNewFile(); // �������򴴽�
  			}

  			FileWriter writer = new FileWriter(f, true);
  			int ss;
  			String sss;
  			writer.write("�Ƽ�����"+"\r\n");
  			for (int i = 0; i < recommend.length; i++) {
  				for (int j = 0; j <10; j++) {
  					 ss = (int) recommend[i][j]; // ��double��תΪint��
  					 sss = ss + "\t"; // ��int��תΪString��
  					writer.write(sss); // д���ļ�
  				}
  				writer.write("\r\n"); // ����
  			}
  			writer.close();
  			System.out.println("�Ƽ�������д���ļ�" + f);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  	}
}
