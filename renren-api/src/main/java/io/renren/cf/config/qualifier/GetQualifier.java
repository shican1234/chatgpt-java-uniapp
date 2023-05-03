package io.renren.cf.config.qualifier;

import io.renren.cf.support.qualifier.PreventQualifier;
import io.renren.cf.support.qualifier.impl.GetPreventQualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetQualifier {

    @Bean
    public PreventQualifier getPreventQualifier() {
        return new GetPreventQualifier();
    }
}
