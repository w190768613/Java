package util;

import javafx.util.Pair;
import vo.StockInfo;

public class StockSorterImpl implements StockSorter {
	/**
     * Accepting series of stock info, sorting them ascending according to their comment length.
     * List.sort() or Arrays.sort() are not allowed.
     * You have to write your own algorithms,Bubble sort、quick sort、merge sort、select sort,etc.
     *
     * @param mypair stock information
     * @return sorted stock
     */
	// 此方法对Pair类型数组进行排序
		public Pair<String, Double>[] sort(Pair<String, Double>[] mypair) {
			int index;
			String temkey;
			Double temvalue;
			for (int j = 1; j < mypair.length; j++) { // 直接选择排序
				index = 0;
				for (int k = 1; k <= mypair.length - j; k++) {
					if (mypair[k].getValue() < mypair[index].getValue())
						index = k;
				}
				temkey = mypair[mypair.length - j].getKey();
				temvalue = mypair[mypair.length - j].getValue();
				mypair[mypair.length - j] = mypair[index];
				mypair[index] = new Pair<>(temkey, temvalue);
			}
			return mypair; // 返回排序后的数组
		}

	
		// 此方法对StockInfo类型数组进行降序排序
		public StockInfo[] sort(StockInfo[] info) {
			int index;
			for (int j = 1; j < info.length; j++) { // 直接选择排序
				index = 0;
				for (int k = 1; k <= info.length - j; k++) {
					if (info[k].getANSWER().length() < info[index].getANSWER().length()) 
						index = k;
					}
				StockInfo temp;
				temp = info[info.length - j];
				info[info.length - j] = info[index];
				info[index] = temp;
			}
			return info; // 返回排序后的数组
		}

	

		// 对二维double数组进行排序
		public static double[][] scoreSort(double[][] scores) {
			double[][] newsid = new double[scores.length][10];
			int index;
			for (int i = 0; i < newsid.length; i++) {
				for (int j = 0; j < 10; j++) { // 直接选择排序
					index = j;
					for (int k = j; k < 60; k++) {
						if (scores[i][k] > scores[i][index]) {
							index = k;
						}
					}
					newsid[i][j] = index;
					double temp;
					temp = scores[i][j];
					scores[i][j] = scores[i][index];
					scores[i][index] = temp;
				}
			}
			return newsid;
		}
	


	public static int[] intsort(int[] score) {
		int[] newsid = new int[10];
		int index;
		for (int j = 0; j < 10; j++) { // 
			index = j;
			for (int k = j; k < score.length; k++) 
				if (score[k] > score[index]) index = k;
				
			newsid[j] = index;
			int temp;
			temp = score[j];
			score[j] = score[index];
			score[index] = temp;
		}
		return newsid;
	}
		
	}
