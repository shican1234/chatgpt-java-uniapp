package io.renren.common.utils;


import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * 海报图工具类
 */
public class PosterUtils {

    public static final String PING_FANG_SCREGULAR_TTF = "/Users/shican/logs/PingFang SC Regular.ttf";
    public static final String PING_FANG_MEDIUM_TTF = "/Users/shican/logs/PingFang Medium.ttf";

    /**
     * 生成分享页海报图
     * String posterBufImage  背景图
     * String headImage  用户头像url
     * String invUrl  用户邀请地址 生成二维码
     * String logoUrl  平台logo的url
     * @return
     * @throws Exception
     */
    public static BufferedImage createImage(Font regularFont,Font mediumFont,String posterBufImageUrl,String avatar,String invUrl,String logoUrl,String nickName,String title,String content) throws Exception {

//        File file_PING_FANG_SCREGULAR_TTF = ResourceUtils.getFile("classpath:font/PingFang SC Regular.ttf");
//        File file_PING_FANG_MEDIUM_TTF = ResourceUtils.getFile("classpath:font/PingFang Medium.ttf");
//        //读取字体文件，设置文字字体
//        InputStream regularFile = new FileInputStream(file_PING_FANG_SCREGULAR_TTF.getPath());
//        InputStream mediumFile = new FileInputStream(file_PING_FANG_MEDIUM_TTF.getPath());
//        Font regularFont = Font.createFont(Font.TRUETYPE_FONT, regularFile);
//        Font mediumFont = Font.createFont(Font.TRUETYPE_FONT, mediumFile);

        //设置整张图片的大小
        int width = 290 * 3;
        int height = 436 * 3;
        // RGB形式
        BufferedImage bgBufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bgBufImage.createGraphics();
        // 设置背景色
        graphics.setBackground(Color.WHITE);
        // 通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
        graphics.clearRect(0, 0, width, height);
        graphics.setBackground(new Color(255, 255, 255));
        //读取主图
//        BufferedImage posterBufImage = ImageIO.read(new FileInputStream("/Users/shican/logs/bg.jpg"));
        BufferedImage posterBufImage = ImageIO.read(new URL(posterBufImageUrl));
//        BufferedImage headImage = ImageIO.read(new FileInputStream("/Users/shican/logs/avatar.jpeg"));
        BufferedImage headImage = ImageIO.read(new URL(avatar));
        // 画一个圆形，放置头像
        BufferedImage roundHeadImg = new BufferedImage(headImage.getWidth(), headImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D roundHeadGraphics = roundHeadImg.createGraphics();
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, roundHeadImg.getWidth(), roundHeadImg.getHeight());
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadImg = roundHeadGraphics.getDeviceConfiguration().createCompatibleImage(headImage.getWidth(),
                headImage.getHeight(), Transparency.TRANSLUCENT);
        roundHeadGraphics = roundHeadImg.createGraphics();
        // 使用 setRenderingHint 设置抗锯齿
        roundHeadGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        roundHeadGraphics.setClip(shape);
        roundHeadGraphics.drawImage(headImage, 0, 0, null);
        roundHeadGraphics.dispose();
        //画海报图
        graphics.drawImage(posterBufImage, 0, 0, 290 * 3, 241 * 3, null);
        //圆形头像图
        graphics.drawImage(roundHeadImg, 15 * 3, 221 * 3, 40 * 3, 40 * 3, null);
        // 用户昵称
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        regularFont = regularFont.deriveFont(Font.BOLD, 12 * 3);
        graphics.setFont(regularFont);
        // 设置画笔,设置Paint属性
        graphics.setPaint(new Color(102, 102, 102));
        graphics.drawString(nickName, 60 * 3, 257 * 3);//用户昵称
        // 设置画笔,设置Paint属性
        mediumFont = mediumFont.deriveFont(Font.BOLD, 15 * 3);
        graphics.setPaint(new Color(51, 51, 51));
        graphics.setFont(mediumFont);
        graphics.drawString(title, 15 * 3, 288 * 3);//分享标题
        regularFont = regularFont.deriveFont(Font.BOLD, 11 * 3);
        // 设置画笔,设置Paint属性
        graphics.setPaint(new Color(102, 102, 102));
        graphics.setFont(regularFont);
        //分享内容
        drawStringWithFontStyleLineFeed(graphics, content, 253 * 3, 15 * 3, 310 * 3, regularFont);
        //中间横线
        graphics.setPaint(new Color(229, 229, 229));
        graphics.drawLine(0, 345 * 3, 290 * 3, 345 * 3);
        // 生成二维码
        BufferedImage qrCodeImage = QRCodeUtils.encode(invUrl, false);
        //二维码图
        graphics.drawImage(qrCodeImage, 15 * 3, 361 * 3, 60 * 3, 60 * 3, null);
        // 抗锯齿
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 表示这段文字在图片上的位置(x,y) 设置内容。
        regularFont = regularFont.deriveFont(Font.BOLD, 11 * 3);
        graphics.setPaint(new Color(51, 51, 51));
        graphics.setFont(regularFont);
        drawStringWithFontStyleLineFeed(graphics, "长按识别二维码\n查看精彩", 77 * 3, 85 * 3, 385 * 3, regularFont);

        //logo图
//        InputStream logIputStream = new FileInputStream("/Users/shican/logs/logo.png");
//        InputStream logIputStream = new FileInputStream("/Users/shican/logs/logo.png");
        BufferedImage logImage = ImageIO.read(new URL(logoUrl));
        graphics.drawImage(logImage, 230 * 3, 364 * 3, 40 * 3, 40 * 3, null);

        //字体logo
//        InputStream logoFontInputStream = new FileInputStream("D:\\img\\wenzi.jpg");
//        BufferedImage logFontImage = ImageIO.read(logoFontInputStream);
//        graphics.drawImage(logFontImage, 225 * 3, 412 * 3, 50 * 3, 9 * 3, null);
//        graphics.dispose();

        //生成圆角图 -- 不要圆角此段代码可删除
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = outputImage.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fill(new RoundRectangle2D.Float(0, 0, width, height, 50,
                50));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
        g2.drawImage(bgBufImage, 0, 0, null);
        g2.dispose();

        return outputImage;
    }


    //写文字
    private static void drawStringWithFontStyleLineFeed(Graphics2D g, String strContent,int maxWdith, int loc_X, int loc_Y, Font font){
        g.setFont(font);
        //获取字符串 字符的总宽度
        int strWidth = getStringLength(g,strContent);
        int strHeight = getStringHeight(g);
        if(strWidth > maxWdith){
            int rowstrnum = getRowStrNum(strContent.length(),maxWdith,strWidth);
            int rows = getRows(strWidth,maxWdith);
            for (int i = 0; i < 2; i++) {
                String temp = "" ;
                //获取各行的String
                if(i == 1){
                    if(rows == 2){
                        temp = strContent.substring(i * rowstrnum);
                    }else{
                        temp = strContent.substring(i * rowstrnum,(i + 1) * rowstrnum - 1);
                        temp = "\n" + temp + "...";
                    }
                    loc_Y = loc_Y + strHeight;
                }
                if(i == 0){
                    temp = strContent.substring(0,rowstrnum);
                }
                g.drawString(temp, loc_X, loc_Y);
            }
        }else{
            //直接绘制
            g.drawString(strContent, loc_X, loc_Y);
        }
    }

    /** 获取文字总长度 */
    private static int getStringLength(Graphics g, String str) {
        char[] strcha = str.toCharArray();
        int strWidth = g.getFontMetrics().charsWidth(strcha, 0, str.length());
        return strWidth;
    }

    /** 获取字符高度 */
    private static int getStringHeight(Graphics g) {
        int height = g.getFontMetrics().getHeight();
        return height;
    }

    /** 每行的字符数 */
    private static int getRowStrNum(int strnum,int rowWidth,int strWidth){
        int rowstrnum = 0;
        rowstrnum = (rowWidth * strnum) / strWidth;
        return rowstrnum;
    }

    /** 字符行数 */
    private static int getRows(int strWidth,int rowWidth){
        int rows=0;
        if(strWidth%rowWidth>0){
            rows=strWidth/rowWidth+1;
        }else{
            rows=strWidth/rowWidth;
        }
        return rows;
    }

    public InputStream getResourceStream(String filePath) {
        return this.getClass().getResourceAsStream(filePath);
    }

    /**
     * 将bufferedImage 转成file
     *
     * @param bufferedImage
     * @return File
     * @throws Exception
     */
    public MultipartFile getFile(BufferedImage bufferedImage) throws Exception {
        //创建一个ByteArrayOutputStream
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //把BufferedImage写入ByteArrayOutputStream
        ImageIO.write(bufferedImage, "png", os);
        //ByteArrayOutputStream转成InputStream
        InputStream input = new ByteArrayInputStream(os.toByteArray());
        //InputStream转成MultipartFile
        MultipartFile multipartFile = new MockMultipartFile("file", "file.png", "text/plain", input);
        return multipartFile;
    }


    /**
     * 生成二维码并转成图片
     *
     * @return
     */
    public MultipartFile createQrCode(String qrCodeContent) {
        // 生成二维码
        MultipartFile multipartFile = null;
        try {
            BufferedImage bufferedImage = QRCodeUtils.encode(qrCodeContent, false);
            //创建一个ByteArrayOutputStream
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            //把BufferedImage写入ByteArrayOutputStream
            ImageIO.write(bufferedImage, "jpg", os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            multipartFile = new MockMultipartFile("file", "file.jpg", "text/plain", input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return multipartFile;
    }

    public static void main(String[] args) {
//        try {
//            BufferedImage image = PosterUtils.createImage();
//            ImageIO.write(image, "png", new File("/Users/shican/logs/demo101.png"));
//            System.out.println("***********图片合成了********");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
