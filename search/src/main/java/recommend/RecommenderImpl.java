package recommend;

import java.util.List;
import java.util.Map;

import tf_idf.TF_IDFImpl;
import util.StockSorterImpl;
import segmenter.ChineseSegmenterImpl;
import vo.StockInfo;


public class RecommenderImpl implements Recommender {

    /**
     * this function need to calculate stocks' content similarity,and return the similarity matrix
     *
     * @param stocks stock info
     * @return similarity matrix
     */
	TF_IDFImpl TF = new TF_IDFImpl();

	
	//
	public double[][] calculateMatrix(StockInfo[] stocks) {
		double[][] matrix = new double[stocks.length][stocks.length];
		for (int i = 0; i < stocks.length; i++)
			matrix[i][i] = 1;
		for (int j = 0; j < stocks.length; j++) {
			for (int k = stocks.length - 1; k > j; k--) {
				matrix[j][k] = calculateSimilar(stocks[j], stocks[k], stocks);//
				matrix[k][j] = matrix[j][k];
			}
		}
		return matrix;
	}

	// 
	 public double calculateSimilar(StockInfo stocks2, StockInfo stocks3, StockInfo[] stocks) {

		ChineseSegmenterImpl segmenter = new ChineseSegmenterImpl();
     String str1=stocks2.CONTENT;
     String str2=stocks3.CONTENT;
		List<String> seglist1 = segmenter.getWordsFromStockInfo(str1); //
		List<String> seglist2 = segmenter.getWordsFromStockInfo(str2);

	List<String> list1 = TF.getResult2(seglist1, stocks); // 
	List<String> list2 = TF.getResult2(seglist2, stocks);

		list1.removeAll(list2);
		list2.addAll(list1); // 

		int[] vector1 = new int[list2.size()]; // 
		int[] vector2 = new int[list2.size()];

		vector1 = calculateVector(vector1, seglist1, list2); //
		vector2 = calculateVector(vector2, seglist2, list2);

		return Cos(vector1, vector2);
	}

	// 
	private int[] calculateVector(int[] vector1, List<String> seglist1, List<String> list2) {

		for (int i = 0; i < list2.size(); i++) {
			for (int j = 0; j < seglist1.size(); j++) {
				if (list2.get(i) == seglist1.get(j))
					vector1[i]++;
			}
		}
		return vector1;
	}

	// 
	private double Cos(int[] vector1, int[] vector2) {

		double a = 0;
		int b = 0;
		int c = 0;
		for (int i = 0; i < vector1.length; i++) {
			a = a + vector1[i] * vector2[i];
			b = b + vector1[i] * vector1[i];
			c = c + vector2[i] * vector2[i];
		}
		return a / (Math.sqrt(b) * Math.sqrt(c));
	}
	

    
    
    
    //处理搜索框中的字符串，得出每条新闻的得分，作为展示的依据
    public int[] calculate(String string, Map<String, List<String>> keyMap){
    	int[] score=new int[keyMap.size()];
    	for(int i=0;i<score.length;i++) score[i]=0;
    	 String key;
		   for(int i=1;i<keyMap.size()+1;i++){
             key=i+"";
			   for(int j=0;j<keyMap.get(key).size();j++){
			   if(string.contains(keyMap.get(key).get(j))){
				   score[i-1]++;
				
			   }
			   }
		   }
		 
		   return StockSorterImpl.intsort(score);
    }
}
