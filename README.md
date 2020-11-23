# 1. 程序目的
从dbo.TopCategory获取品类名称，并从知乎中获取该品类的下拉框热词

# 2. 程序使用步骤
1. 在baiduKeywordsManage.txt中输入“品类”在百度关键词规划中找到的关键字（**只保留当前数据，不要保留历史记录**）
2. 运行 Crawler.java, 在光标中输入“品类名称”，如保温饭盒
3. 如果品类已经存在于database，程序会中断执行
4. 如果不存在与database，程序会把数据存入到databse <br>
   * 品类存入dbo.TopCategory 
   * 获取到的知乎下拉框关键字 + 百度关键词规划 存入dbo.HotWord

# 3. 结果保存
1. 保存在当前项目的hotWords.txt中
2. 保存在dbo.HotWord中