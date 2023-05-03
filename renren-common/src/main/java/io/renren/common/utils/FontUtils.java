package io.renren.common.utils;


import java.awt.*;
import java.io.InputStream;
import java.util.Objects;

/**
 * 字体工具类.
 *
 * @author <a href="mailto:xiaoQQya@126.com">xiaoQQya</a>
 * @since 2023/03/30
 */
public class FontUtils {

    private FontUtils() {
    }

    /**
     * 从资源文件加载本地字体.
     *
     * @param fontPath  字体路径, 相对 resources 目录的相对路径, 如 fonts/simsun.ttc
     * @param fontStyle 字体风格, 可选值: Font.PLAIN, Font.BOLD, Font.ITALIC
     * @param fontSize  字体大小
     * @return 字体
     */
    public static Font loadLocalFont(String fontPath, int fontStyle, float fontSize) {
        Font font;
        try (InputStream fontStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fontPath)) {
            if (Objects.isNull(fontStream)) {
                throw new FontException("Font resource is null.");
            }

            font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            font = font.deriveFont(fontStyle, fontSize);
        } catch (Exception e) {
            throw new FontException(e.getMessage(), e.getCause());
        }
        return font;
    }

    /**
     * 字体异常.
     *
     * @author <a href="mailto:xiaoQQya@126.com">xiaoQQya</a>
     * @since 2023/03/30
     */
    static class FontException extends RuntimeException {

        public FontException(String message) {
            super(message);
        }

        public FontException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

