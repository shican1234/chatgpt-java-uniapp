package io.renren.cf.config.qualifier;


import io.renren.cf.support.qualifier.PreventQualifier;
import io.renren.cf.support.qualifier.impl.AllPreventQualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AllQualifier {

    @Bean
    public PreventQualifier allPreventQualifier() {
        return new AllPreventQualifier();
    }
}
