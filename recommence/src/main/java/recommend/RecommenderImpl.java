package recommend;

import java.util.List;
import tf_idf.TF_IDFImpl;
import util.StockSorterImpl;
import segmenter.ChineseSegmenterImpl;
import vo.StockInfo;
import vo.UserInterest;

public class RecommenderImpl implements Recommender {

    /**
     * this function need to calculate stocks' content similarity,and return the similarity matrix
     *
     * @param stocks stock info
     * @return similarity matrix
     */
	TF_IDFImpl TF = new TF_IDFImpl();

	@Override
	//计算相似度矩阵
	public double[][] calculateMatrix(StockInfo[] stocks) {
		double[][] matrix = new double[stocks.length][stocks.length];
		for (int i = 0; i < stocks.length; i++)
			matrix[i][i] = 1;
		for (int j = 0; j < stocks.length; j++) {
			for (int k = stocks.length - 1; k > j; k--) {
				matrix[j][k] = calculateSimilar(stocks[j], stocks[k], stocks);//调用calculateSimilar方法，计算两个content的相似度
				matrix[k][j] = matrix[j][k];
			}
		}
		return matrix;
	}

	// 创建calculateSimilar方法，计算两个content的相似度
	public double calculateSimilar(StockInfo stocks2, StockInfo stocks3, StockInfo[] stocks) {

		ChineseSegmenterImpl segmenter = new ChineseSegmenterImpl();

		List<String> seglist1 = segmenter.getWordsFromStockInfo(stocks2); // 获取stock2的content分词后的词汇list
		List<String> seglist2 = segmenter.getWordsFromStockInfo(stocks3);

		List<String> list1 = TF.getResult2(seglist1, stocks); // 获取每个content的关键词数组
		List<String> list2 = TF.getResult2(seglist2, stocks);

		list1.removeAll(list2);
		list2.addAll(list1); // 取两个list的无重复并集，得到总关键词集合

		int[] vector1 = new int[list2.size()]; // 创建vector数组储存词频向量
		int[] vector2 = new int[list2.size()];

		vector1 = calculateVector(vector1, seglist1, list2); // 通过calculateVector方法获取向量数组
		vector2 = calculateVector(vector2, seglist2, list2);

		return Cos(vector1, vector2);
	}

	// 获取向量数组
	private int[] calculateVector(int[] vector1, List<String> seglist1, List<String> list2) {

		for (int i = 0; i < list2.size(); i++) {
			for (int j = 0; j < seglist1.size(); j++) {
				if (list2.get(i) == seglist1.get(j))
					vector1[i]++;
			}
		}
		return vector1;
	}

	// 计算余弦相似度
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
	
	
	/**
     * this function need to recommend the most possibility stock number
     *
     * @param matrix       similarity matrix
     * @param userInterest user interest
     * @return commend stock number
     */
    @Override
    //计算推荐矩阵
	public double[][] recommend(double[][] matrix, UserInterest[] userInterest) {
		double[][] scores = new double[userInterest.length][matrix.length];

		for (int i = 0; i < userInterest.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				String term = userInterest[i].mark[j];
				String num = "1";
				if (term.equals(num) == true) // 如果已阅读，scores赋值为-1
					scores[i][j] = -1;

				else {
					for (int k = 0; k < matrix.length; k++) { // 如果没有阅读
						String term2 = userInterest[i].mark[k];
						if (term2.equals(num) == true) // 通过累加得到scores
							scores[i][j] += matrix[j][k];
					}
				}
			}
		}

		double[][] newsid = new double[scores.length][10];
		// 得到排序后的推荐矩阵
		newsid = StockSorterImpl.scoreSort(scores);
		// 每个用户筛选前十篇文章作为最终推荐矩阵
		double[][] recommendMatrix = new double[newsid.length][10];
		for (int i = 0; i < newsid.length; i++) {
			for (int j = 0; j < 10; j++) {
				recommendMatrix[i][j] = newsid[i][j];
			}
		}
		return recommendMatrix;
	}
}
