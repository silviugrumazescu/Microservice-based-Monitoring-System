package com.example.monitoringmicroservice.configuration;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${spring.rabbitmq.exchange}")
    String directExchangeName;

    @Value("${spring.rabbitmq.devicequeue}")
    String deviceQueueName;

    @Value("${spring.rabbitmq.consumptionqueue}")
    String consumptionQueueName;

    @Value("${spring.rabbitmq.routingkey}")
    String routingKey;

    @Value("${spring.rabbitmq.host}")
    String host;

    @Value("${spring.rabbitmq.virtual-host}")
    String vhost;

    @Value("${spring.rabbitmq.port}")
    Integer port;

    @Value("${spring.rabbitmq.username}")
    String username;

    @Value("${spring.rabbitmq.password}")
    String password;

    @Bean
    CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(vhost);
        return cachingConnectionFactory;
    }

    @Bean
    Queue consumptionQueue() {
       return new Queue(consumptionQueueName, false);
    }

    @Bean
    Queue deviceQueue() {
        return new Queue(deviceQueueName, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(directExchangeName);
    }

    @Bean
    Binding binding1(DirectExchange exchange) {
        return BindingBuilder.bind(consumptionQueue()).to(exchange).with(consumptionQueueName);
    }

    @Bean
    Binding binding2(DirectExchange exchange) {
        return BindingBuilder.bind(deviceQueue()).to(exchange).with(deviceQueueName);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
