package ta.example;

import ta.example.interfaces.FileHandler;

import ta.example.interfaces.StockSorter;
import ta.example.vo.StockInfo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Formatter;

public class Main {

   private static FileHandler fileHandler;
   private static StockSorter stockSorter;
    
    static{
    	//用匿名内部类初始化接口FileHandler
	  fileHandler = new FileHandler(){
			
		//读取文件
	 public StockInfo[] getStockInfoFromFile(String filePath){
		   int num=0;                                                  //用于获取数据行数
		   StockInfo[] stock;                                        //长度未定，用于最终传递数据
			StockInfo[] arrs=new StockInfo[11000];   //用于暂时存储数据
			String element[]=new String[8];                //暂存一行的数据
		  for(int i = 0;i<11000;i++){
			arrs[i]=new StockInfo();
			}
		  
			try {
				String encoding="UTF-8";
	            File file=new File(filePath);
	          if(file.isFile() && file.exists()){ //判断文件是否存在
	        	InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
	              BufferedReader bufferedReader = new BufferedReader(read);
	              String line="";
	              int i=-1;
	            while ((line=bufferedReader.readLine())!=null) { //将数据暂时存入长度为11000的StockInfo型数组arrs中
	    		i++;
	    		element=line.split("\t");
                arrs[i].ID = element[0];
                arrs[i].TITLE = element[1];
                arrs[i].AUTHOR=element[2];
                arrs[i].DATE =element[3];
                arrs[i].LASTUPDATE = element[4];
             	arrs[i].CONTENT = element[5];
             	arrs[i].ANSWERAUTHOR = element[6];
            	arrs[i].ANSWER = element[7];
            	arrs[i].LINE= arrs[i].ID + "\t" + arrs[i].TITLE + "\t" + arrs[i].AUTHOR + "\t" + arrs[i].DATE
					 + "\t" + arrs[i].LASTUPDATE + "\t" + arrs[i].CONTENT + "\t" + 
					arrs[i].ANSWERAUTHOR + "\t" + arrs[i].ANSWER +"\n";  
            	}
	            num=i+1;  //确定数据行数
	         read.close();
       } 
	          else{
	              System.out.println("找不到指定的文件");
	          }
			}
	          catch (Exception e) {
	              System.out.println("读取文件内容出错");
	              e.printStackTrace();
	          }		
		  stock=new StockInfo[num];
	            for(int j = 0;j<num;j++){       //将数据从arrs中转入长度为数据行数的数组stock中
	    			stock[j]=new StockInfo();
	    			stock[j]=arrs[j];
		}
	            return stock;                         //返回StockInfo类数组
	 }
	 
       //写入新文件
		public int setStockInfo2File(String filePath,StockInfo[] stocks){
			  int length=0;                  //用来存储数组长度
			try{
				File f = new File(filePath);
				if (f.exists()) {  
                     System.out.println("文件"+f+"存在");  
                 } else {  
                     System.out.println("创建文件"+f);  
                     f.createNewFile();    // 不存在则创建  
                 }  
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath),true));
              
                for(int i = 0;i < stocks.length;i++){
					writer.write(stocks[i].LINE);
					length++;
				}
                writer.close();
            }catch(Exception e){
                e.printStackTrace();
            }
			return length;                 //返回写入新文件的数组的长度
		}
}; 

//用匿名内部类初始化stockSorter接口
stockSorter = new StockSorter() {  
	
  //普通排序函数（默认升序）
	public StockInfo[] sort(StockInfo[] info){
	
		 for(int j=1;j<info.length;j++){   //冒泡排序
	         for(int k=0;k<info.length-j;k++){
	     	if( info[k].ANSWER.length()> info[k+1].ANSWER.length()) {
	     		StockInfo temp;
	     		temp=		info[k];
	     		info[k]=info[k+1];
	     		info[k+1]=temp;
	     		}
	     	}
	     }
		 return info;  //返回排序后的数组
	}

	//含有boolean参数的排序函数
	public StockInfo[] sort(StockInfo[] info,boolean order){
		
		if(order==false){                           //降序排列
			int index=0;
			for(int j=1;j<info.length;j++){    //直接选择排序
				for(int k=0;k<=info.length-j;k++){
		     	if( info[k].getANSWER().length()<info[index].getANSWER().length()) {
		     		index=k;
		          }
		     	}
		     StockInfo temp;
	     		temp=	info[info.length-j];
	     		info[info.length-j]=info[index];
	     		info[index]=temp;
		    }
		}
		
		else{                                               //升序排列
			int min=0;int max=0;
		for(int j=1;j<info.length/2;j++){   //二维选择排序
					 for(int k=0;k<=info.length-j;k++){
			     	if( info[k].getANSWER().length()<info[min].getANSWER().length()) {
			     		min=k;
			          }
			     	if( info[k].getANSWER().length()>info[max].getANSWER().length()) {
			     		max=k;
			          }
			     	
			     StockInfo temp;
		     		temp=info[info.length-j];    //ANSWER较长的放到数组最后
		     		info[info.length-j]=info[max];
		     		info[max]=temp;
		     		
		     		temp=info[j];          //ANSWER较短的放到数组开头
		     		info[j]=info[min];
		     		info[min]=temp;
			    }
			}
		    }
			 return info;  //返回排序后的数组
		} 
};
   }
   
    //主函数
    public static void main(String[] args) {
    	
      //使用主函数的参数，手动在运行配置里添加两个自变量
       if(args.length < 2){
          System.exit(0);
         }
       String filePath = args[0];
       String targetPath = args[1];
       
      //数据读取
       StockInfo[] stocks = fileHandler.getStockInfoFromFile(filePath);
      if(stocks != null)
            System.out.println("数据读入成功");
        
       //数据排序
        StockInfo[] sortedStocks = stockSorter.sort(stocks,true);
        System.out.println("排序成功");

        //数据写入
        int writeLenght = fileHandler.setStockInfo2File(targetPath,sortedStocks);
        Formatter formatter = new Formatter(System.out);
        if(writeLenght == sortedStocks.length)
            formatter.format("写入操作成功，共写入%d条数据",writeLenght);
        else
            formatter.format("写入失败");
   }
}
