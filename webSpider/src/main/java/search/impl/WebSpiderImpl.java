package search.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import search.Parser;
import search.WebSpider;

public class WebSpiderImpl implements WebSpider{

	@Override
	public Parser getParser() {
		 Parser parser=new ParserImpl(); 
		return parser;
	}

	@Override
	public List<String> getHtmlFromWeb() {

		List<String> programurl = new ArrayList<>(); // 存储项目URL
		final List<String> htmls = new ArrayList<>(); // 存储html
		String parturl = "https://apply.grad.ucsd.edu";

		getProgramUrl(programurl); // 调用私有getProgramUrl函数，获取项目URL（相对路径）
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5); // 创建固定大小的线程池
		for (int i = 0; i < programurl.size(); i++) { // 遍历存储URL的集合
			final String url = parturl + programurl.get(i); // 合成完整的URL
			fixedThreadPool.execute(new Runnable() { // 多线程爬虫
				@Override
				public void run() {
					try {
						// 模拟浏览器访问
						Document doc = Jsoup.connect(url)
								.userAgent( // 模拟浏览器
										"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
								.timeout(10000).get(); // 设置延时
						Elements progtable = doc.select("div.tab-content"); // 选择存储项目信息的div
						for (Element table : progtable) // 对于其中的每个项目
							htmls.add(table.toString()); // 将对应的HTML存入List中
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		fixedThreadPool.shutdown();
		while (!fixedThreadPool.isTerminated()) {
		} // 在所有子线程都运行完毕再继续主线程
		return htmls; // 返回存储项目html的集合
	}
	
	//获取项目URL
	private void getProgramUrl(List<String> programurl) {

		String url = "https://apply.grad.ucsd.edu/masters-programs";  //研究生项目主页面
		try {
			// 模拟浏览器访问
			Document doc = Jsoup.connect(url)
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
					.timeout(10000).get();
			Elements urls = doc.select("div.drawer").select("a");
			int mark = 0; // 作为标记
			for (Element prourl : urls) { // 遍历获取的url
				String address = prourl.attr("href");
				if (mark == 1) { // 因为每个学院的项目在同一个静态页面中
					mark = 0; // 这里我们只获取每个学院的第一个项目的URL即可
					programurl.add(address);
				}
				if (address.equals("#"))  //在该校的网站中，每个学院的项目间用#隔开
					mark = 1;  //因此，遇到#号便改变标记，下一个URL便是我们需要的URL
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
}
}
