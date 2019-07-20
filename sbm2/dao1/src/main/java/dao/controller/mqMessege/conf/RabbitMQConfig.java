package dao.controller.mqMessege.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_A = "SPRING_QUEUEA";

    public static final String EXCA = "SPRING_FANOUT";

    @Bean(name = QUEUE_A)
    public Queue queue1(){
        return new Queue(QUEUE_A,false);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(EXCA,false,true);
    }

    @Bean
    public Binding fanoutBinding3(){return BindingBuilder.bind(queue1()).to(fanoutExchange());}
}
