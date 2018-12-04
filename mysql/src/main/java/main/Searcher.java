package main;

import search.FileHandler;
import search.Parser;
import search.WebSpider;
import vo.Program;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * 在助教给的接口中做了一些修改
 */
public class Searcher {

    /**
     *  在助教给的接口中做了一些修改
     * @param args
     */
    public  void runspider(){
    	
        try {
            Class.forName("search.impl.Manager");
        } catch (ClassNotFoundException e) {
            System.out.println("Manager类不存在");
        }

        List<WebSpider> webSpiders = SearchManager.getWebSpider();

        List<Program> programs = new ArrayList<>();
        for (WebSpider webSpider : webSpiders) {
            Parser parser = webSpider.getParser();
            List<String> pages = webSpider.getHtmlFromWeb();
           
            for (String page : pages) {
                programs.add(parser.parseHtml(page));
            }
        }

        FileHandler fileHandler = SearchManager.getFileHandler();
        fileHandler.program2File(programs);


    }
}
