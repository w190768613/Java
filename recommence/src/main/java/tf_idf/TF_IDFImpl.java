package tf_idf;

import javafx.util.Pair;

import util.StockSorter;
import util.StockSorterImpl;
import vo.StockInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TF_IDFImpl implements TF_IDF {
    /**
     * this func you need to calculate words frequency , and sort by frequency.
     * you maybe need to use the sorter written by yourself in example 1
     *
     * @param words the word after segment
     * @return a sorted words
     * @see StockSorter
     */
    @Override
 //此方法计算所有answer分词后得到的词的tf-idf值
	public Pair<String, Double>[] getResult(List<String> words, StockInfo[] stockInfos) {
		// 创建HashMap类变量tfMap存储词对应的tf值
		HashMap<String, Double> tfMap = new HashMap<String, Double>();

		// 取词算出TF值
		for (int i = 0; i < words.size(); i++) {
			int times = 0; // times存储词出现的次数
			for (int j = 0; j < words.size(); j++) {
				if (words.get(i).equals(words.get(j)))
					times++; // 出现一次，times加1
			}
			double frequency = (double) times / words.size(); // 计算词频TF
			tfMap.put(words.get(i), frequency); // 得到每个词的词频和词本身放入tfMap
		}

		// 创建HashMap类变量idfMap存储词对应idf值
		HashMap<String, Double> idfMap = new HashMap<String, Double>();

		// 取词算出IDF值
		for (int i = 0; i < words.size(); i++) {
			int times = 0; // times存储含此词的文章数
			for (int j = 0; j < stockInfos.length; j++) {
				if (stockInfos[j].ANSWER.contains(words.get(i)) == true) 
					times++; // 每检测到一篇文章含有这个词，times加1
				}
			double frequency = (double) Math.log(stockInfos.length) / Math.log(times + 1);
			idfMap.put(words.get(i), frequency);// 得到每个词的idf和词本身放入idfMap
		}

		// 创建Pair类数组存储词对应的tf-idf值
		Pair<String, Double>[] mypair = new Pair[tfMap.size()];
		Set<String> set = tfMap.keySet();
		Iterator<String> it = set.iterator(); // 迭代器
		String term;
		int n = 0;
		while (it.hasNext()) { // 遍历tfMap
			term = (String) it.next();
			Double A = tfMap.get(term);
			Double B = idfMap.get(term);
			Double result = A * B; // result存储tf-idf值
			mypair[n] = new Pair<>(term, result); // 将词和对应的tf-idf值加入mypair
			n++;
		}

		StockSorterImpl sorter = new StockSorterImpl();
		mypair = sorter.sort(mypair); // 调用sort方法对mypair进行排序

		return mypair;  //返回排序后的词频数组
	}
    
    
	// 此方法计算某个content的分词结果的tf-idf值，
	// 排序后选取20个词作为此content的关键词返回
	public List<String> getResult2(List<String> words, StockInfo[] stockInfos) {

		// 创建HashMap类变量tfMap存储词对应的tf值
		HashMap<String, Double> tfMap = new HashMap<String, Double>();
		// 取词算出TF值
		for (int i = 0; i < words.size(); i++) {
			int times = 0; // times存储词出现的次数
			for (int j = 0; j < words.size(); j++) {
				if (words.get(i).equals(words.get(j))) {
					times++; // 出现一次，times加1
				}
			}
			double frequency = (double) times / words.size();// 计算词频TF
			tfMap.put(words.get(i), frequency);// 得到每个词的词频和词本身放入hashmap
		}

		// 创建HashMap类变量idfMap存储词对应idf值
		HashMap<String, Double> idfMap = new HashMap<String, Double>();
		// 取词算出IDF值
		for (int i = 0; i < words.size(); i++) {
			int times = 0; // times存储含此词的文章数
			for (int j = 0; j < stockInfos.length; j++) {
				if (stockInfos[j].CONTENT.contains(words.get(i)) == true) {
					times++; // 每检测到一篇文章含有这个词，times加1
				}
			}
			double frequency = (double) Math.log(stockInfos.length) / Math.log(times + 1);
			idfMap.put(words.get(i), frequency);// 得到每个词的idf和词本身放入hashmap
		}

		// 创建Pair类数组存储词对应的tf-idf值
		Pair<String, Double>[] mypair = new Pair[tfMap.size()];
		List<String> mylist = new ArrayList<String>();
		Set<String> set = tfMap.keySet();
		Iterator<String> it = set.iterator(); // 迭代器
		String term;
		int n = 0;
		while (it.hasNext()) { // 遍历tfMap
			term = (String) it.next();
			Double A = tfMap.get(term);
			Double B = idfMap.get(term);
			Double result = A * B; // result即为tf-idf值
			mypair[n] = new Pair<>(term, result); // 将词和对应的tf-idf值加入mypair
			n++;
		}

		StockSorterImpl sorter = new StockSorterImpl();
		mypair = sorter.sort(mypair); // 调用sort方法对数组mypair按照tf-idf值进行排序

		if (mypair.length > 20) { // 如果数组长度大于20，则选取前20个关键词
			for (int i = 0; i < 20; i++)
				mylist.add(mypair[i].getKey());
		} else {
			for (int i = 0; i < mypair.length; i++) // 如果数组长度小于20，则获取所有关键词
				mylist.add(mypair[i].getKey());
		}
		return mylist; // 返回关键词集合
	}
}
