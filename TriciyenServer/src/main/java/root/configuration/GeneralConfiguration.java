package root.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import root.service.AccountService;
import root.service.impl.AccountServiceImpl;

@Configuration
public class GeneralConfiguration {

    @Bean
    public AccountService getAccountService() {
        return new AccountServiceImpl();
    }
}
