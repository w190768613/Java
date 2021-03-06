# sort java排序算法

运行时需**配置程序自变量**，第一个是将要读取的文件 data.txt，第二个是存放输出的文件 output.txt，可参考 setting.png

### 使用JAVA I.O数据流，从data.txt中读取数据，排序，并写入output.txt

●实体类StockInfo 用于存储读入的数据  
●接口FileHandler 负责进行文件的读取  
●接口StockSorter 用于对读取的数据进行排序。


# 经典排序算法介绍
## 一、冒泡排序 BubbleSort
介绍：重复访问要排序的数组，一次比较两个元素，如果顺序错误就交换顺序

步骤：

1. 比较相邻的元素，如果第一个比第二个大，就交换他们两个。

2. 对第0个到第n-1个数据做同样的工作。这时，最大的数就“浮”到了数组最后的位置上。

3. 针对所有的元素重复以上的步骤，除了最后一个。

4. 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。

## 二、选择排序 SelectionSort
介绍：选择排序无疑是最简单直观的排序。它的工作原理如下。

步骤：

1. 在未排序序列中找到最小（大）元素，存放到排序序列的起始位置。

2. 再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。

3. 以此类推，直到所有元素均排序完毕。

## 三、插入排序 InsertionSort
介绍：对于每个未排序的元素，在已排序序列中从后向前扫描，找到相应位置并插入。

步骤：

1. 从第一个元素开始，该元素可以认为已经被排序

2. 取出下一个元素，在已经排序的元素序列中从后向前扫描

3. 如果被扫描的元素（已排序）大于新元素，将该元素后移一位

4. 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置

5. 将新元素插入到该位置后

6. 重复步骤2~5

## 四、归并排序 MergeSort
介绍：归并排序是采用分治法的一个非常典型的应用。归并排序的思想就是先递归分解数组，再合并数组。

先考虑合并两个有序数组，基本思路是比较两个数组的最前面的数，谁小就先取谁，取了后相应的指针就往后移一位。然后再比较，直至一个数组为空，最后把另一个数组的剩余部分复制过来即可。

再考虑递归分解，基本思路是将数组分解成left和right，如果这两个数组内部数据是有序的，那么就可以用上面合并数组的方法将这两个数组合并排序。如何让这两个数组内部是有序的？可以再二分，直至分解出的小组只含有一个元素时为止，此时认为该小组内部已有序。然后合并排序相邻二个小组即可。

## 五、快速排序 QuickSort
介绍： 采用了分治法的思想；通常明显比同为Ο(n log n)的其他算法更快

步骤：

1. 从数列中挑出一个元素作为基准数。

2. 分区过程，将比基准数大的放到右边，小于或等于它的数都放到左边。

3. 再对左右区间递归执行第二步，直至各区间只有一个数。
