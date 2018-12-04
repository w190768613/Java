package tf_idf;

import javafx.util.Pair;
import util.StockSorter;
import vo.StockInfo;

import java.util.List;

public interface TF_IDF {

	/**
     * ���ݸ����ʻ����飬��������ʻ�tf-idf��Ƶ�����մ�Ƶ�Ӹߵ��׽�������Ȼ������Pair��������
     * @param words the word after segment
     * @param stockInfos data
     * @return a sorted words,String is the word, and Integer is the frequency ,like : fin,123
     * @see StockSorter,Pair
     */
    Pair<String, Double>[] getResult(List<String> words, StockInfo[] stockInfos);
}
