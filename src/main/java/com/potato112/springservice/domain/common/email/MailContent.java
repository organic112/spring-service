package com.potato112.springservice.domain.common.email;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;


public interface MailContent {

    String getEmail();
    String getSubject();

    default String build(ITemplateEngine templateEngine){

        Context context = new Context();
        //context.setVariable("model" , this);
        return templateEngine.process("emailTemplate", context);
    }
}
