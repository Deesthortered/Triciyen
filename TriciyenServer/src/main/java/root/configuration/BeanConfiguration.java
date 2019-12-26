package root.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import root.service.AccountService;
import root.service.ConversationService;
import root.service.MessageService;
import root.service.UserConversationService;
import root.service.impl.AccountServiceImpl;
import root.service.impl.ConversationServiceImpl;
import root.service.impl.MessageServiceImpl;
import root.service.impl.UserConversationServiceImpl;

@Configuration
public class BeanConfiguration {

    @Bean
    public AccountService getAccountService() {
        return new AccountServiceImpl();
    }

    @Bean
    public ConversationService getConversationService() {
        return new ConversationServiceImpl();
    }

    @Bean
    public MessageService getMessageService() {
        return new MessageServiceImpl();
    }

    @Bean
    public UserConversationService getUserConversationService() {
        return new UserConversationServiceImpl();
    }
}
