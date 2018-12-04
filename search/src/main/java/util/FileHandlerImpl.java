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
    	
		StockInfo[] stock; // 长度未定，用于最终传递数据
		StockInfo[] arrs = new StockInfo[61]; // 用于暂时存储数据
		String element[] = new String[8]; // 暂存一行的数据
		for (int i = 0; i < 61; i++) arrs[i] = new StockInfo(); // 初始化StockInfo类数组arrs
 

		try {
			String encoding = "UTF-8";
		
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
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
				System.out.println("找不到指定的文件");
				return null;
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			return null;
		}

		stock = new StockInfo[60];
		for (int j = 0; j < 60; j++) { // 将数据从arrs中转入长度为数据行数的数组stock中
			stock[j] = new StockInfo();
			stock[j] = arrs[j + 1];
		}
		return stock; // 返回StockInfo类数组
	}

		

	
   

    /**
     * This function need write matrix to files
     *
     * @param matrix the matrix you calculate
     */
    @Override
  //将相似度矩阵写入文件 similar.txt
  	public void setCloseMatrix2File(double[][] matrix) {
  		try {
  			String filePath = "D:\\java program\\homework_2\\src\\main\\output\\similar.txt";
  			File f = new File(filePath);
  			if (f.exists()) {
  				System.out.println("文件" + f + "存在");
  			} else {
  				System.out.println("创建文件" + f);
  				f.createNewFile(); // 不存在则创建
  			}

  			FileWriter writer = new FileWriter(f, true);
  			String sss;
  			writer.write("相似度矩阵："+"\r\n");
  			for (int i = 0; i < 60; i++) {
  				for (int j = 0; j < 60; j++) {
  					 sss = matrix[i][j] + "\t"; // 将double类型转为String类
  					writer.write(sss); // 写入文件
  				}
  				writer.write("\r\n"); // 换行
  			}
  			writer.close();
  			System.out.println("相似度矩阵已写入文件" + f);
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
    //将推荐矩阵写入文件 rencommence.txt
  	public void setRecommend2File(double[][] recommend) {
  		try {
  			String filePath = "D:\\java program\\homework_2\\src\\main\\output\\recommence.txt";
  			File f = new File(filePath);
  			if (f.exists()) {
  				System.out.println("文件" + f + "存在");
  			} else {
  				System.out.println("创建文件" + f);
  				f.createNewFile(); // 不存在则创建
  			}

  			FileWriter writer = new FileWriter(f, true);
  			int ss;
  			String sss;
  			writer.write("推荐矩阵："+"\r\n");
  			for (int i = 0; i < recommend.length; i++) {
  				for (int j = 0; j <10; j++) {
  					 ss = (int) recommend[i][j]; // 将double类转为int类
  					 sss = ss + "\t"; // 将int类转为String类
  					writer.write(sss); // 写入文件
  				}
  				writer.write("\r\n"); // 换行
  			}
  			writer.close();
  			System.out.println("推荐矩阵已写入文件" + f);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  	}
}
