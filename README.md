# lexian-webservice
乐鲜商城电子商务系统
> 本项目起始于2018年8月20日,服务于18-19-1学期实训，为lexian-mall的项目代码

目录解释：

- 服务器端的代码位于src目录下。
- 前端的代码位于web目录下。
- goods_img目录下存放的是所有商品的图片
- type_img目录下存放的是商品主分类的图片。
- lexian.sql是项目的数据库表以及数据。
- lib目录存放的是javaweb项目本身的jar包
- 项目引用的所有外部jar包存放于web/WEB-INF/lib目录下。

部署方案：
一、环境配置
1、服务器环境配置：
Apache-tomcat-7.0.90
Jdk 1.8
MySQL 5.5.60

2、服务器运行软件：
IDEA 2018.2.3
Navicat for MySQL

3、浏览器建议版本：
Chrome 58+
国产双核浏览器极速模式（360，搜狗，QQ）

二、部署过程
1、tomcat的安装配置：
首先打开项目安装包内的apache-tomcat-7.0.90，解压并安装之，然后配置环境变量。
安装完成后右击“我的电脑”，然后“属性”—>"高级系统设置"—>"环境变量"，在系统变量中添加以下变量
a、TOMCAT_HOME，该变量指向解压文件的路径，该目录下有lib、bin等文件夹。
添加方法如下：点击"环境变量"下的“新建”，在“变量名”中填写“TOMCAT_HOME”，在“变量值”中填写解压文件的路径D:\Tomcat\apache-tomcat-9.0.0.M1-windows-x64\apache-tomcat-9（后面没有分号）然后点击“确定”;
b、CATALINA_HOME，该变量的值与TOMCAT_HOME相同，设置方法同a步骤；
c、在“系统变量”中找到Path变量，双击打开Path变量，在“变量值”的最后面添加%CATALINA_HOME%\bin（后面没有分号）；
d、在“系统变量”中找到CLASSPath变量，双击打开CLASSPath变量，在“变量值”的最后加%CATALINA_HOME%\lib\servlet-api.jar（后面没有分号）。

2、jdk1.8的安装配置
参见https://www.cnblogs.com/zlslch/p/5658399.html
系统属性界面，在“这台电脑”，右键，属性，高级，环境变量 	
在“系统变量”中，设置3属性JAVA_HOME、CLASSPATH、Path（不区分大小写）,若已存在则点击“编辑”，不存在则点击“新建”；
新建JAVA_HOME指明JDK安装路径，就是刚才安装时所选择的路径C:\Program Files\Java\Jdk1.8.0_60，此路径下包括lib，bin，jre等文件夹（此变量最好设置，因为以后运行tomcat，eclipse等都需要依*此变量）；
 寻找 Path 变量
在变量值最后输入
 ;%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin
（注意原来Path的变量值末尾有没有;号，如果没有，先输入；号再输入上面的代码）
新建CLASSPATH 变量
.;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar
检验是否配置成功 运行cmd 输入 java -version （java 和 -version 之间有空格）

3、MySQL 5.5.60安装配置
参见https://www.jb51.net/article/96898.htm

4、项目二次开发部署过程

1、将项目根目录(即lexian-webservice)下的lexian.sql文件导入数据库。
2、打开IDEA，打开该项目。配置项目src目录下c3p0-config.xml，将数据库连接账号密码修改为自己的。
