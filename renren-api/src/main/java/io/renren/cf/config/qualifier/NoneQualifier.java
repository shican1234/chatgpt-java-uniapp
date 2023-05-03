package io.renren.cf.config.qualifier;


import io.renren.cf.support.qualifier.PreventQualifier;
import io.renren.cf.support.qualifier.impl.NonePreventQualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NoneQualifier {

    @Bean
    public PreventQualifier nonePreventQualifier() {
        return new NonePreventQualifier();
    }
}
