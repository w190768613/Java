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
     * Example: 我今天特别开心 return : 我 今天 特别 开心
     *
     * @param stocks stocks info
     * @return chinese word
     * @see ChineseSegmenterImpl
     */
    @Override
    //此方法对所有answer分词
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
		}; // 词性过滤

		List<String> segwords = new ArrayList<String>(); // 创建segwords存储分词结果
		for (int j = 0; j < stocks.length; j++) {
			Result term = ToAnalysis.parse(stocks[j].ANSWER); // 用Result装载分词的结果
			for (int i = 0; i < term.size(); i++) {
				String natureStr = term.get(i).getNatureStr(); // 获取词性
				if (expectedNature.contains(natureStr)) { // 如果词性符合
					String words = term.get(i).getName(); // 将词转为字符串
					segwords.add(words); // 将词加入segwords中
				}
			}
		}
		return segwords; // segwords存储了所有answer的分词结果
	}


    
	//此方法对给定的一条新闻分词
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

		List<String> segwords = new ArrayList<String>(); // 创建segwords存储分词结果
	
		Result term = ToAnalysis.parse(str); // 用Result装载分词的结果
		for (int i = 0; i < term.size(); i++) {
			String natureStr = term.get(i).getNatureStr(); // 获取词性
			if (expectedNature.contains(natureStr)) { // 如果词性符合
				String words = term.get(i).getName(); // 将词转为字符串
				segwords.add(words); // 将词加入segwords中
			}
		}
		return segwords; // segwords存储了某一条特定新闻的content的分词结果
	}
}
