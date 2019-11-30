package root.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import root.service.AccountService;
import root.service.ConversationService;
import root.service.impl.AccountServiceImpl;
import root.service.impl.ConversationServiceImpl;

@Configuration
public class GeneralConfiguration {

    @Bean
    public AccountService getAccountService() {
        return new AccountServiceImpl();
    }

    @Bean
    public ConversationService getConversationService() {
        return new ConversationServiceImpl();
    }
}
