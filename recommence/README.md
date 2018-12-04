# recommence java内容推荐
需要在服务器上运行

● Maven项目  
● Ansj分词  
● TF-IDF算法  
● 余弦相似度  
● 协同过滤算法


##  1.Maven项目 
**在pom.xml文件添加相应配置**

 ```java
        <dependency>
          <groupId>org.ansj</groupId>
          <artifactId>ansj_seg</artifactId>
          <version>5.1.3</version>
        </dependency>
```

#### Maven的优点：  
1.统一管理jar包  
2.提供了统一开发规范与工具



## 2. Ansj分词

Maven引入Ansj分词包
  

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
    			          .........
    				}
    			}
    		}
    
    
词云：

![](http://www.javatree.cn/file-server/e/20171119/b1a3cda0c6104386abec57b72b4def92.png)



## 3.TF-IDF算法

●计算TF值时，将所有answer看作**一篇**文章，分子（某个词在文章出现的次数）即为某个词在所有answer中出现的次数，分母（文章总词数）即为所有answer的总词数（分词过滤后的词数）  
●计算IDF时，每个answer看作一篇文章，即共有60篇文章，语料库即为这60篇文章。



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

IDF也类似。


## 4.余弦相似度

#### 计算60篇文章每两篇的相似度

●首先，按照TF-IDF算法从两篇文章中各取20个关键词，并合并成一个集合  
●然后，计算这两句话对于这个集合中的词的词频，从而获得两个词频向量  
●最后，求出这两个向量夹角的余弦，余弦值值越接近1，说明两句话越相似

![](http://www.javatree.cn/file-server/e/20171110/f39f3daf58b44b4daf726b2934da792f.png)

计算相似度矩阵时，注意到A与B的相似度和B与A的相似度应该是相同的，也就是说Matrix[j][k] ==Matrix[k][j]，并且Matrix[i][i] ==1.0；



    double[][] matrix = new double[stocks.length][stocks.length];
    		for (int i = 0; i < stocks.length; i++)
    			matrix[i][i] = 1;
    		for (int j = 0; j < stocks.length; j++) {
    			for (int k = stocks.length - 1; k > j; k--) {
    				matrix[j][k] = calculateSimilar(stocks[j], stocks[k], stocks);
					//调用calculateSimilar方法，计算两个content的相似度
    				matrix[k][j] = matrix[j][k];
    			}
    		}


## 5.协同过滤算法

算法通过挖掘用户的历史行为，发现用户的偏好，基于不同的偏好，对用户进行群组划分，并推荐品味相似的商品。


    double[][] scores = new double[userInterest.length][matrix.length];
    		for (int i = 0; i < userInterest.length; i++) {
    			for (int j = 0; j < matrix.length; j++) {
    				String term = userInterest[i].mark[j];
    				String num = "1";
    				if (term.equals(num) == true) // 如果已阅读，scores赋值为-1
    					scores[i][j] = -1;
    				else {
    					for (int k = 0; k < matrix.length; k++) { // 如果没有阅读，通过累加得到scores
    						.........
    					}
    				}
    			}
    		}

