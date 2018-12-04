package util;

import java.io.File;

import vo.StockInfo;


public interface FileHandler {

    /**
     * This func gets stock information from the given interfaces path.
     * If interfaces don't exit,or it has a illegal/malformed format, return NULL.
     * The filepath can be a relative path or a absolute path
     * @param f
     * @return the Stock information array from the interfaces,or NULL
     */
    StockInfo[] getStockInfoFromFile(File f);

    /**
     * This func gets user interesting from the given interfaces path.
     * If interfaces don't exit,or it has a illegal/malformed format, return NULL.
     * The filepath can be a relative path or a absolute path
     * @param filePath
     * @return
     */
    
    /**
     * This function need write matrix to files
     * @param matrix the matrix you calculate
     */
    void setCloseMatrix2File(double[][] matrix);

    /**
     * This function need write recommend to files
     * @param recommend the recommend you calculate
     */
    void setRecommend2File(double[][] recommend);



}
