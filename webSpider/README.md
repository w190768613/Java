# webSpider java爬虫
爬取加州大学圣地亚哥分校的研究生项目；直接运行即可

● Jsoup  
● 爬虫  
● UUID 通用唯一识别码  
● 打包成jar包  


## 一、Jsoup

Jsoup是基于JAVA的HTML解析器，可**直接解析某个URL地址**，或者**HTML文本**内容  
**中文文档**地址http://www.open-open.com/jsoup/

引入Maven依赖：

```
        <!-- https://mvnrepository.com/artifact/org.jsoup/jsoup -->
        <dependency>
            <groupId>org.jsoup</groupId>
            <artifactId>jsoup</artifactId>
            <version>1.8.3</version>
        </dependency>
        <dependency>
          <groupId>org.javalite</groupId>
          <artifactId>activejdbc</artifactId>
          <version>1.4.13</version>
      </dependency>
      
```


## 二、爬虫

使用userAgent模拟浏览器，并设置timeout降低爬取速度


     Document doc = Jsoup.connect(url)
    		.userAgent(  //模拟浏览器
    			 "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
    		.timeout(10000)  //设置延时
				.get();



## 三、 UUID 通用唯一识别码

  UUID含义是通用唯一识别码 (Universally Unique Identifier)，这是一个软件建构的标准，也是被开源软件基金会 (Open Software Foundation, OSF)的组织应用在分布式计算环境 (Distributed Computing Environment, DCE) 领域的一部分。
  
  
  UUID 的目的，是让分布式系统中的所有元素，都能有唯一的辨识资讯，而不需要透过中央控制端来做辨识资讯的指定。如此一来，每个人都可以建立不与其它人冲突的 UUID。在这样的情况下，就不需考虑数据库建立时的名称重复问题。
  
  
  目前最广泛应用的 UUID，即是微软的 Microsoft's Globally Unique Identifiers (GUIDs)，而其他重要的应用，则有 Linux ext2/ext3 档案系统、LUKS 加密分割区、GNOME、KDE、Mac OS X 等等


    // 生成 UUID
    String uuid = UUID.randomUUID().toString().trim().replaceAll(“-“, “”);`



## 四、打包成jar包

#### 使用Fat Jar插件

使用Eclipse的在线安装，快要完成时报错如下：

![](http://www.javatree.cn/file-server/e/20171213/cf18c211de67400a95ade0d237437791.png)

求助百度，发现原因是**eclipse和fatjar版本不兼容**

解决方法是**下载一个eclipse2.0插件**，具体如下：

● 进入Help -> Install New Software...,  
● 选择Work with列表下的**The Eclipse Project Updates** - http://download.eclipse.org/eclipse/updates/4.5  
● 在弹出的可安装组件中选择：**Eclipse Tests, Examples, and Extras**  
● 打开下拉选择：**Eclipse 2.0 Style Plugin Support**安装；

至此，eclipse2.0插件安装完成。

重启后在线安装Fat Jar，Work with 输入fatjar - http://kurucz-grafika.de/fatjar 安装成功


右键项目选择Export->other->Fat Jar Exporter 打包项目，勾选use extern Jar-Name，命名jar，选择Main-class，勾选One-JAR，如图所示

![](http://www.javatree.cn/file-server/e/20171213/abb36e480ffc44828e1dd1cc28e7188d.png)


这样，jar包就打包成功了
