package search.impl;

import java.io.BufferedWriter;


import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.sql.*;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;

import search.FileHandler;
import vo.Myprogram;
import vo.Program;

public class FileHandlerImpl implements FileHandler {

	@Override
	//将项目信息写入文件
	public int program2File(List<Program> programList) {
		
		int num = programList.size(); // 获取项目数
	
		Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/program",
              "root", "sos7715813");
		
        for (int i = 0; i < num; i++) {  //遍历项目
				Program one = programList.get(i); 
				
				Myprogram single=new Myprogram();
				single.set("id",one.getId());
				single.set("university", one.getUniversity());
				single.set("country", one.getCountry());
				single.set("school", one.getSchool());
				single.set("program_name", one.getProgramName());
				single.set("homepage",one.getHomepage());
				single.set("location", one.getLocation());
				single.set("email", one.getEmail());
				single.set("phone_number", one.getPhoneNumber());
				single.set("degree", one.getDegree());
				single.set("deadline_with_aid",one.getDeadlineWithAid());
				single.set("deadline_without_aid",one.getDeadlineWithoutAid());
				single.insert();   
				
			}  //将项目的各项信息按照指定的格式写入数据库
        Base.close(); 
        
		return num;  //返回写入的项目个数
	}
}
