package recommend;

import vo.StockInfo;
import vo.UserInterest;

public interface Recommender {

    /**
     * this function need to calculate stocks' content similarity,and return the similarity matrix
     * @param stocks stock info
     * @return similarity matrix
     */
    double[][] calculateMatrix(StockInfo[] stocks);

    /**
     * this function need to recommend the most possibility stock number
 
     * @param matrix similarity matrix
     * @param userInterest user interest
     * @return commend stock number
     */
    double[][] recommend(double[][] matrix, UserInterest[] userInterest);

}
