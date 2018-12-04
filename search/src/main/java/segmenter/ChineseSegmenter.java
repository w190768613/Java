package segmenter;

import vo.StockInfo;

import java.util.List;

public interface ChineseSegmenter {

	  /**
     * this func will get chinese word from a list of stocks. You need analysis stocks' answer and get answer word.
     * And implement this interface in the class : ChineseSegmenterImpl
     * Example: �ҽ����ر��� return : �� ���� �ر� ����
     * @param stocks stocks info
     * @return chinese word
     * @see ChineseSegmenterImpl
     */
    List<String> getWordsFromInput(StockInfo[] stocks);

}
