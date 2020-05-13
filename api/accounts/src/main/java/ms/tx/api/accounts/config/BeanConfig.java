package ms.tx.api.accounts.config;

import ms.tx.api.accounts.payload.account.AccountCreation;
import ms.tx.api.accounts.payload.transaction.TransactionCreation;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue accountCreationQueue() {
        return new Queue(AccountCreation.class.getSimpleName());
    }

    @Bean
    public Queue transactionCreationQueue() {
        return new Queue(TransactionCreation.class.getSimpleName());
    }
}
