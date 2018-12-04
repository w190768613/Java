package search.impl;

import java.io.BufferedWriter;


import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.sql.*;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;

import search.FileHandler;
import vo.Program;

public class FileHandlerImpl implements FileHandler {

	@Override
	//将项目信息写入文件
	public int program2File(List<Program> programList) {
		
		int num = programList.size(); // 获取项目数
		try {
			String filepath = "University of California--San Diego.txt";  //指定文件路径（这里是相对路径）
			File f = new File(filepath);
			if (f.exists()) {
				f.delete();
				f.createNewFile();
			} else {
				f.createNewFile();  //文件不存在则创建
			}
	
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filepath),true));
		
        for (int i = 0; i < num; i++) {  //遍历项目
   
				Program one = programList.get(i); 
				writer.write(one.getUniversity() + "\t" + one.getCountry() + "\t" + one.getProgramName() + "\t"
						+ one.getSchool() + "\t" + one.getDegree() + "\t" + one.getEmail() + "\t" + one.getPhoneNumber()
						+ "\t" + one.getLocation() + "\t" + one.getDeadlineWithAid() + "\t"
						+ one.getDeadlineWithoutAid() + "\t" + one.getHomepage() + "\n");
			
				
			}  //将项目的各项信息按照指定的格式写入文件
      
			System.out.println("项目信息已写入文件University of California--San Diego.txt");  //写入完成后在控制台提示
			writer.close();  //关闭写入流
		
      }catch(Exception e){
          e.printStackTrace();
      }
	 return num;  //返回写入的项目个数
	}
}
