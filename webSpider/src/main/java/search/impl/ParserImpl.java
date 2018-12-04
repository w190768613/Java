package search.impl;

import java.util.UUID;

import org.javalite.activejdbc.Base;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import search.Parser;
import vo.Program;

public class ParserImpl implements  Parser{

	@Override
	public Program parseHtml(String html) {
		
		Program one = new Program();
		String[] infos=new String[9];  //暂时存储各项信息
		String university="University of California--San Diego";  //大学名称
		String country="United States La Jolla, CA";  //国家和地区
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-","");  //生成32位随机字母数字
		
		Document doc = Jsoup.parse(html);  //使用Jsoup解析html
		Elements tr1=doc.select("div > div > div.col-sm-8 > table:nth-child(2)").select("tr");  //选择存储部分项目信息的table
		for(Element tr:tr1){  //因为每个项目的table的行数不一
		switch(tr.select("td.col-sm-4 > b").text()){  //需要遍历每行(tr),通过第一个单元格(td)的内容寻找对应的信息
			case "Major": infos[0]=tr.select(" td:nth-child(2)").text();break;
			case "Department": infos[1]=tr.select(" td:nth-child(2)").text();break;
			case "Degree": infos[2]=tr.select(" td:nth-child(2)").text();break;
			case "Application Deadlines": 
			infos[3]=tr.select(" td:nth-child(2)").text();infos[4]=tr.select(" td:nth-child(2)").text();break;
			}
		}
		Elements tr2=doc.select("div > div > div.col-sm-4 > table").select("tr");
		for(Element tr:tr2){
			switch(tr.select("td > b").text()){
			case "Website": infos[5]=tr.select("td > a").text();break;
			case "Email": infos[6]=tr.select("td > a").text();break;
			case "Phone": tr.select("td > b").remove();infos[7]=tr.text();break;
			case "Mailing Address": tr.select("td > b").remove();infos[8]=tr.text();break;
			
			}
		}
		
		for(int j=0;j<9;j++){
		  if(infos[j]==null) infos[j]="NULL";  //如果某项信息不存在，则置为"NULL"
		  }
		//将每个项目的各项信息存入Program型变量one中
	    one.setId(uuid);
		one.setUniversity(university);
		one.setCountry(country);
		one.setProgramName(infos[0]);
		one.setSchool(infos[1]);
		one.setDegree(infos[2]);
		one.setDeadlineWithAid(infos[3]);
		one.setDeadlineWithoutAid(infos[4]);
		one.setHomepage(infos[5]);
		one.setEmail(infos[6]);
		one.setPhoneNumber(infos[7]);
		one.setLocation(infos[8]);
	
		return one;
	}

}
