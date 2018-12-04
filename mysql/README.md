# mysql Java数据库
直接运行 Jframe.java 即可

●**多线程爬虫**  
●使用了**ActiveJDBC框架**连接数据库  
●实现了**对本地数据库的增、删、改、查**操作，以及**对于云端数据库的查询和添加**  
●利用可视化开发工具**WindowBuilder**设计了**GUI**界面  
●简单的**模糊查询**

## 一、多线程爬虫
使用**固定大小的线程池**来管理线程。
线程池的意义在于管理短时间执行完毕的大量线程，通过**重用已存在的线程**，降低线程创建和销毁造成的消耗，**提高系统响应速度**。  
通过isTerminated()方法判断子线程是否全部结束，在**所有子线程都运行完毕再继续主线程**。

    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5); // 创建固定大小的线程池
    		for (int i = 0; i < programurl.size(); i++) { // 遍历存储URL的集合
    			fixedThreadPool.execute(new Runnable() {  // 多线程爬虫
    				@Override
    				public void run() {
    					。。。。。。
    				}
    			});
    		}
    		fixedThreadPool.shutdown();  //关闭线程池 
    		while (!fixedThreadPool.isTerminated()) {} // 在所有子线程都运行完毕再继续主线程

## 二、ActiveJDBC框架

### 1、引入Maven依赖


```python

   -  <dependencies>
-           <dependency>
-               <groupId>org.javalite</groupId>
-               <artifactId>activejdbc</artifactId>
-               <version>1.4.13</version>
-           </dependency>
-     
-           <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
-           <dependency>
-               <groupId>mysql</groupId>
-               <artifactId>mysql-connector-java</artifactId>
-               <version>5.1.6</version>
-           </dependency>
-     
-           <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
-           <dependency>
-               <groupId>org.slf4j</groupId>
-               <artifactId>slf4j-api</artifactId>
-               <version>1.7.25</version>
-           </dependency>
-     
-           <dependency>
-               <groupId>org.slf4j</groupId>
-               <artifactId>slf4j-nop</artifactId>
-               <version>1.7.25</version>
-           </dependency>
-       </dependencies>
```

同时需要添加**Maven插件 activejdbc-instrumentation**
```java
        <plugin>
              <groupId>org.javalite</groupId>
              <artifactId>activejdbc-instrumentation</artifactId>
              <version>1.4.13</version>
              <executions>
                  <execution>
                      <phase>process-classes</phase>
                      <goals>
                          <goal>instrument</goal>
                      </goals>
                  </execution>
              </executions>
          </plugin>
```

**PS**：
1、plugin是放在`<dependencies></dependencies>`之外的
2、plugin外要加plugins标签
3、plugins之外要加build标签
4、plugins和build**之间可能要加**PluginManagement标签

### 2、建立映射
ORM框架很重要的一点便是**建立映射**。
首先，需要**建立Model**,

    @Table("program")  //指定对应的Table
    public class Myprogram extends Model{
    	
    	  public Myprogram(){}  //构造函数（这个很重要）
    }

只需继承Model，写一个构造函数（方法体为空）即可。不需要属性，不需要其他方法。

然后，**对Model class进行预处理**
（**Eclipse**下）右击项目->运行方式->Maven builder，执行命令
    
     org.javalite:activejdbc-instrumentation:1.4.13:instrument

(注意：符号是英文符号；其他编译器可能在上述语句前要加 **mvn** 字符)
如果控制台提示BUILD SUCCESS，那么预处理成功，可以连接数据库操作Table了

### 3、数据库操作

**连接数据库**
    
    Base.open("com.mysql.jdbc.Driver", "jdbc：mysql://host:port/database", "USERNAME", "PASSWORD");
	Base.close();
	 
（host是地址/URL，port是接口，database是数据库名）

**增、查、删、改**

        // 查
       List<Myprogram> pros = Myprogram.where("university  =? ", text);
        // 增
        Person p = new Person();
        p.set("first_name", "Marilyn");
        p.set("last_name", "Monroe");
        p.set("dob", "1935-12-06");
        p.saveIt();  //注意！如果ID是自动递增的,用saveIt；如果ID用了UUID，要用insert
        // 改
        Employee e = Employee.findFirst("first_name = ?", "John");
        e.set("last_name", "Steinbeck").saveIt();
        // 删
        Employee e = Employee.findFirst("first_name = ?", "John");
        e.delete();

## 三、模糊查询

●用**写字典**的方法，预先**将关键词及其变体词放入字典**  
●用户搜索时，检索字典，确定用户想要搜索的内容，在数据库中查找对应的数据  
●利用**下拉选择框JComboBox**确定搜索范围

以US为例。
对于**有效关键词"US","USA","United States","America"**，建立**字符串"美国 US us USA Usa usa UNITED STATES United States UnitedStates America america"**放入字典  
那么，用户输入 US/us/USA/Usa/usa/United States/UNITED STATES/United/America/america/美国 ，都可以**搜索到四个有效关键词"US","USA","United States","America"对应的学校**

![](http://www.javatree.cn/file-server/e/20180104/370826de68014ee8ada459d3bf54cf6b.png)

![](http://www.javatree.cn/file-server/e/20180104/61f1dcf40d664f79a9852cc6cce23dc4.png)
