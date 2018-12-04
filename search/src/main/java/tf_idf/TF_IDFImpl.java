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
    
	 //�˷�����������answer�ִʺ�õ��Ĵʵ�tf-idfֵ
		public Pair<String, Double>[] getResult(List<String> words, StockInfo[] stockInfos) {
			// ����HashMap�����tfMap�洢�ʶ�Ӧ��tfֵ
			HashMap<String, Double> tfMap = new HashMap<String, Double>();

			// ȡ�����TFֵ
			for (int i = 0; i < words.size(); i++) {
				int times = 0; // times�洢�ʳ��ֵĴ���
				for (int j = 0; j < words.size(); j++) {
					if (words.get(i).equals(words.get(j)))
						times++; // ����һ�Σ�times��1
				}
				double frequency = (double) times / words.size(); // �����ƵTF
				tfMap.put(words.get(i), frequency); // �õ�ÿ���ʵĴ�Ƶ�ʹʱ������tfMap
			}

			// ����HashMap�����idfMap�洢�ʶ�Ӧidfֵ
			HashMap<String, Double> idfMap = new HashMap<String, Double>();

			// ȡ�����IDFֵ
			for (int i = 0; i < words.size(); i++) {
				int times = 0; // times�洢���˴ʵ�������
				for (int j = 0; j < stockInfos.length; j++) {
					if (stockInfos[j].ANSWER.contains(words.get(i)) == true) 
						times++; // ÿ��⵽һƪ���º�������ʣ�times��1
					}
				double frequency = (double) Math.log(stockInfos.length) / Math.log(times + 1);
				idfMap.put(words.get(i), frequency);// �õ�ÿ���ʵ�idf�ʹʱ������idfMap
			}

			// ����Pair������洢�ʶ�Ӧ��tf-idfֵ
			Pair<String, Double>[] mypair = new Pair[tfMap.size()];
			Set<String> set = tfMap.keySet();
			Iterator<String> it = set.iterator(); // ������
			String term;
			int n = 0;
			while (it.hasNext()) { // ����tfMap
				term = (String) it.next();
				Double A = tfMap.get(term);
				Double B = idfMap.get(term);
				Double result = A * B; // result�洢tf-idfֵ
				mypair[n] = new Pair<>(term, result); // ���ʺͶ�Ӧ��tf-idfֵ����mypair
				n++;
			}

			StockSorterImpl sorter = new StockSorterImpl();
			mypair = sorter.sort(mypair); // ����sort������mypair��������

			return mypair;  //���������Ĵ�Ƶ����
		}
	    
    
    
		// �˷�������ĳ��content�ķִʽ����tf-idfֵ��
		// �����ѡȡ20������Ϊ��content�Ĺؼ��ʷ���
		public List<String> getResult2(List<String> words, StockInfo[] stockInfos) {

			// ����HashMap�����tfMap�洢�ʶ�Ӧ��tfֵ
			HashMap<String, Double> tfMap = new HashMap<String, Double>();
			// ȡ�����TFֵ
			for (int i = 0; i < words.size(); i++) {
				int times = 0; // times�洢�ʳ��ֵĴ���
				for (int j = 0; j < words.size(); j++) {
					if (words.get(i).equals(words.get(j))) {
						times++; // ����һ�Σ�times��1
					}
				}
				double frequency = (double) times / words.size();// �����ƵTF
				tfMap.put(words.get(i), frequency);// �õ�ÿ���ʵĴ�Ƶ�ʹʱ������hashmap
			}

			// ����HashMap�����idfMap�洢�ʶ�Ӧidfֵ
			HashMap<String, Double> idfMap = new HashMap<String, Double>();
			// ȡ�����IDFֵ
			for (int i = 0; i < words.size(); i++) {
				int times = 0; // times�洢���˴ʵ�������
				for (int j = 0; j < stockInfos.length; j++) {
					if (stockInfos[j].CONTENT.contains(words.get(i)) == true) {
						times++; // ÿ��⵽һƪ���º�������ʣ�times��1
					}
				}
				double frequency = (double) Math.log(stockInfos.length) / Math.log(times + 1);
				idfMap.put(words.get(i), frequency);// �õ�ÿ���ʵ�idf�ʹʱ������hashmap
			}

			// ����Pair������洢�ʶ�Ӧ��tf-idfֵ
			Pair<String, Double>[] mypair = new Pair[tfMap.size()];
			List<String> mylist = new ArrayList<String>();
			Set<String> set = tfMap.keySet();
			Iterator<String> it = set.iterator(); // ������
			String term;
			int n = 0;
			while (it.hasNext()) { // ����tfMap
				term = (String) it.next();
				Double A = tfMap.get(term);
				Double B = idfMap.get(term);
				Double result = A * B; // result��Ϊtf-idfֵ
				mypair[n] = new Pair<>(term, result); // ���ʺͶ�Ӧ��tf-idfֵ����mypair
				n++;
			}

			StockSorterImpl sorter = new StockSorterImpl();
			mypair = sorter.sort(mypair); // ����sort����������mypair����tf-idfֵ��������

			if (mypair.length > 20) { // ������鳤�ȴ���20����ѡȡǰ20���ؼ���
				for (int i = 0; i < 20; i++)
					mylist.add(mypair[i].getKey());
			} else {
				for (int i = 0; i < mypair.length; i++) // ������鳤��С��20�����ȡ���йؼ���
					mylist.add(mypair[i].getKey());
			}
			return mylist; // ���عؼ��ʼ���
		}
	}
