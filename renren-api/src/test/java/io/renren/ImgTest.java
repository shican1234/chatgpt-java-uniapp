package io.renren;

import cn.hutool.core.codec.Base64;
import com.unfbx.chatgpt.entity.billing.Subscription;
import io.renren.common.utils.FontUtils;
import io.renren.common.utils.PosterUtils;
import io.renren.common.utils.QRCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ImgTest {
    @Test
    public void testA() throws Exception {
//        InputStream in1 = this.getClass().getResourceAsStream("/fonts/PingFang SC Regular.ttf");
//        Font regularFont = Font.createFont(Font.TRUETYPE_FONT, in1);
        Font regularFont = FontUtils.loadLocalFont("fonts/PingFang SC Regular.ttf", Font.TRUETYPE_FONT, 400);
        Font mediumFont = FontUtils.loadLocalFont("fonts/PingFang Medium.ttf", Font.TRUETYPE_FONT, 400);
        //昵称显示的字体（此字体库是从网上下载的，非windows自带），使用时引用FontName字体名称即可（字体名称：双击字体库可以看到字体名称）
//        InputStream in2 = this.getClass().getResourceAsStream("/fonts/PingFang Medium.ttf");
//        Font mediumFont = Font.createFont(Font.TRUETYPE_FONT, in2);
//        BufferedImage image = PosterUtils.createImage(regularFont, mediumFont);
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        ImageIO.write(image, "png", stream);
//        String asBase64 = "data:image/png;base64,"+ Base64.encode(stream.toByteArray());
//        System.out.println(asBase64);
//        ImageIO.write(image, "png", new File("/Users/shican/logs/demo1011.png"));
    }
}
