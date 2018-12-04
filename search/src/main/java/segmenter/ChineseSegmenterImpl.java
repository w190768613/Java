package segmenter;

import vo.StockInfo;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.ToAnalysis;

public class ChineseSegmenterImpl implements ChineseSegmenter {

	 /**
     * this func will get chinese word from a list of stocks. You need analysis stocks' answer and get answer word.
     * And implement this interface in the class : ChineseSegmenterImpl
     * Example: �ҽ����ر��� return : �� ���� �ر� ����
     *
     * @param stocks stocks info
     * @return chinese word
     * @see ChineseSegmenterImpl
     */
    @Override
    //�˷���������answer�ִ�
	public List<String> getWordsFromInput(StockInfo[] stocks) {

		Set<String> expectedNature = new HashSet<String>() {
			{
				add("n");
				add("vn");
				add("vf");
				add("vx");
				add("vi");
				add("vl");
				add("vg");
				add("userDefine");
				add("wh");
				add("ad");
				add("an");
				add("al");
				add("nt");
				add("nz");
				add("nw");
				add("nr");
				add("nrf");
				add("nsf");
				add("nl");
				add("ns");
			}
		}; // ���Թ���

		List<String> segwords = new ArrayList<String>(); // ����segwords�洢�ִʽ��
		for (int j = 0; j < stocks.length; j++) {
			Result term = ToAnalysis.parse(stocks[j].ANSWER); // ��Resultװ�طִʵĽ��
			for (int i = 0; i < term.size(); i++) {
				String natureStr = term.get(i).getNatureStr(); // ��ȡ����
				if (expectedNature.contains(natureStr)) { // ������Է���
					String words = term.get(i).getName(); // ����תΪ�ַ���
					segwords.add(words); // ���ʼ���segwords��
				}
			}
		}
		return segwords; // segwords�洢������answer�ķִʽ��
	}


    
	//�˷����Ը�����һ�����ŷִ�
	public List<String> getWordsFromStockInfo(String str) {

		Set<String> expectedNature = new HashSet<String>() {
			{
				add("n");
				add("vn");
				add("vf");
				add("vx");
				add("vi");
				add("vl");
				add("vg");
				add("nt");
				add("nz");
				add("nw");
				add("nr");
				add("nrf");
				add("nsf");
				add("nl");
				add("ns");
				add("userDefine");
				add("wh");
				add("ad");
				add("an");
				add("al");
			}
		};

		List<String> segwords = new ArrayList<String>(); // ����segwords�洢�ִʽ��
	
		Result term = ToAnalysis.parse(str); // ��Resultװ�طִʵĽ��
		for (int i = 0; i < term.size(); i++) {
			String natureStr = term.get(i).getNatureStr(); // ��ȡ����
			if (expectedNature.contains(natureStr)) { // ������Է���
				String words = term.get(i).getName(); // ����תΪ�ַ���
				segwords.add(words); // ���ʼ���segwords��
			}
		}
		return segwords; // segwords�洢��ĳһ���ض����ŵ�content�ķִʽ��
	}
}
