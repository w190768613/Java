# search java内容搜索
直接运行 Jframe.java 即可

● Swing界面  
● 文件选择器导入文件  
● 文件保存  
● 事件监听器  

## 一、文件选择器导入文件 
这里使用**文件选择器**来导入文件，它是java封装好的一个用于读取文件和保存文件的工具  

打开文件的方法如下：`


                    // 文件选择器
    				JFileChooser fd = new JFileChooser();
    				fd.showOpenDialog(null);
    				File f = fd.getSelectedFile();
    				if (f != null) {
					......
					}

可以找一些方法对文件选择器进行**美化**（当然这也不是很好看）：


                    // 美化文件选择器
    				if (UIManager.getLookAndFeel().isSupportedLookAndFeel()) {
    					final String platform = UIManager.getSystemLookAndFeelClassName();
    					if (!UIManager.getLookAndFeel().getName().equals(platform)) {
    						try {
    							UIManager.setLookAndFeel(platform);
    						} catch (Exception exception) {
    							exception.printStackTrace();
    						}
    					}
    				}


效果如下：

![](http://www.javatree.cn/file-server/e/20171204/f2c701c3288d490196518382b531141e.png)

## 二、文件保存

保存文件的原理和文件导入类似


                            JFileChooser jf = new JFileChooser();
    						jf.setFileSelectionMode(JFileChooser.SAVE_DIALOG | JFileChooser.DIRECTORIES_ONLY);
    						jf.showDialog(null, null);
    					    File fi = jf.getSelectedFile();
    						String f = fi.getAbsolutePath() + "data.png";确定保存地址
    						File myfile = new File(f);
    						try {
    							BufferedImage img = ImageIO.read(image);
    							ImageIO.write(img, "png", myfile);//保存图片
    						} catch (IOException e1) {
    							e1.printStackTrace();
    				        }
                    


## 三、事件监听器

实验中我们需要用到**鼠标监听器**。在模板下写具体操作的函数即可。


                 //鼠标点击事件
                  importButton.addActionListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent arg0) {
                     //代码
                    }
        		 });


如果是可视化界面，则更好实现。右击按钮，选择**Add event hander ->action**，以上代码便会**自动生成**。
