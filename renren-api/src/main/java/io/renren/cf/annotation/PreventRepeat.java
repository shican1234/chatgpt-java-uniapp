package io.renren.cf.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreventRepeat {

    /**
     * 设置请求锁定时间 单位
     *
     * @return
     */
    int lockTime() default 10;
}
