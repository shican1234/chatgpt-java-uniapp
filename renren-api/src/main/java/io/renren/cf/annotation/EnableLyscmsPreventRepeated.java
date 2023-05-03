package io.renren.cf.annotation;


import io.renren.cf.config.PreventRepeatImportSelector;
import io.renren.cf.strategy.PreventStrategy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(value = {PreventRepeatImportSelector.class})
@Documented
public @interface EnableLyscmsPreventRepeated {

    /**
     * 外部传入策略
     */
    PreventStrategy strategy() default PreventStrategy.POST;
}
