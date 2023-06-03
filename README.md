### 项目说明
- JAVA+unipp开发的chatgpt程序
- 开发脚手架基于人人开源 https://gitee.com/renrenio/renren-security
- chatgpt JAVASDK 基于Grt1228大佬开源的,地址https://github.com/Grt1228/chatgpt-java
- java开发的chatgpt小程序  前端使用uniapp   可打包多端运行  APP  H5  公众号  小程序等 ,三级分销,卡密充值,提供opai的反向代理,
- 后台包括海报管理,充值配置管理,GPTKEY管理,用户管理,分销提现管理,问答模板配置,AI模配置等
- 基于GPT3.5 SDK包括了4.0的功能 因为没有4.0的key所以没试过4.0的
- WEB版本包括SD绘画,FS绘画等
- 接入三方内容检测功能,免得乱提问题坐牢去了
- 具体功能看演示(演示为商业版本)
- WEB演示地址：https://pc.momoyucm.top
- H5演示地址：https://ai.momoyucm.top
- 后台页面仓库地址 https://gitee.com/shican1234/chatgpt-admin-ui.git
- UNIAPP仓库地址 https://gitee.com/shican1234/chatgpt-uniapp.git
- 商业版H5公众号和小程序演示: ![输入图片说明](renren-admin/src/main/resources/public/gzh2.jpg)

<br>




**本地JAVA部署**
- 通过git下载源码
- idea、eclipse需安装lombok插件，不然会提示找不到entity的get set方法
- 创建数据库renren_security，数据库编码为UTF-8
- 执行renren-api/db/chatgpt.sql文件，初始化数据
- 后台管理默认账号密码为admin
- 修改application-dev.yml文件，更新MySQL账号和密码
- 运行后在后台管理-->系统设置-->参数管理对应修改公众号/小程序等APPID

**本地后台管理VUE运行**
- 您需要提前在本地安装[Node.js](https://nodejs.org/en/)，版本号为：[12.x、14.x]，再使用[Git](https://git-scm.com/)克隆项目或者直接下载项目后，然后通过`终端命令行`执行以下命令。

```bash
# 切换到项目根目录

# 安装插件
npm install

# 启动项目
npm run serve
```
**本地UNIAPP代码运行**
- 修改utils/evn.js中的BASE_URL为你的api地址
- 修改utils/evn.js中的BASE_WS为你的ws api地址
- 修改utils/request.js中的BASE_URL为你的api地址
- 修改manifest.json中的小程序appid
```bash
# 切换到项目根目录

# 安装插件
npm install
```
<br>

### 项目截图(截图内容为商业版本)
**WEB页面**
![输入图片说明](renren-admin/src/main/resources/public/pc0.png)
![输入图片说明](renren-admin/src/main/resources/public/pc1.png)
![输入图片说明](renren-admin/src/main/resources/public/pc2.png)
![输入图片说明](renren-admin/src/main/resources/public/pc3.png)
![输入图片说明](renren-admin/src/main/resources/public/pc4.png)
![输入图片说明](renren-admin/src/main/resources/public/pc5.png)
![输入图片说明](renren-admin/src/main/resources/public/pc6.png)
![输入图片说明](renren-admin/src/main/resources/public/pc7.png)
![输入图片说明](renren-admin/src/main/resources/public/pc8.png)

**UNIAPP页面**
![输入图片说明](renren-admin/src/main/resources/public/qd1.png)
![输入图片说明](renren-admin/src/main/resources/public/qd2.jpeg)
![输入图片说明](renren-admin/src/main/resources/public/qd3.jpeg)
![输入图片说明](renren-admin/src/main/resources/public/qd4.jpeg)
![输入图片说明](renren-admin/src/main/resources/public/qd5.jpeg)
![输入图片说明](renren-admin/src/main/resources/public/qd6.jpeg)
![输入图片说明](renren-admin/src/main/resources/public/qd7.jpeg)
![输入图片说明](renren-admin/src/main/resources/public/qd8.jpeg)
![输入图片说明](renren-admin/src/main/resources/public/qd9.jpeg)
![输入图片说明](renren-admin/src/main/resources/public/qd10.jpeg)
**后台页面**
![输入图片说明](renren-admin/src/main/resources/public/ht1.png)
![输入图片说明](renren-admin/src/main/resources/public/ht2.png)
![输入图片说明](renren-admin/src/main/resources/public/ht3.png)
![输入图片说明](renren-admin/src/main/resources/public/ht4.png)
![输入图片说明](renren-admin/src/main/resources/public/ht5.png)
![输入图片说明](renren-admin/src/main/resources/public/ht6.png)
![输入图片说明](renren-admin/src/main/resources/public/ht7.png)
![输入图片说明](renren-admin/src/main/resources/public/ht8.png)
![输入图片说明](renren-admin/src/main/resources/public/ht9.png)
![输入图片说明](renren-admin/src/main/resources/public/ht10.png)
![输入图片说明](renren-admin/src/main/resources/public/ht11.png)
![输入图片说明](renren-admin/src/main/resources/public/ht12.png)

### 普通版本与商业版的区别
![区别](renren-admin/src/main/resources/public/对比.png)
<br>


### 详细可联系
VX:shican19930703
![输入图片说明](renren-admin/src/main/resources/public/wx.jpg)
