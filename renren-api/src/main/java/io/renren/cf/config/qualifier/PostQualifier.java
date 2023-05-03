package io.renren.cf.config.qualifier;


import io.renren.cf.support.qualifier.PreventQualifier;
import io.renren.cf.support.qualifier.impl.PostPreventQualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostQualifier {

    @Bean
    public PreventQualifier postPreventQualifier() {
        return new PostPreventQualifier();
    }
}
