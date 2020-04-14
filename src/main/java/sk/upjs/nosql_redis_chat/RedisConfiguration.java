package sk.upjs.nosql_redis_chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;


@Configuration
public class RedisConfiguration {

    static String[] clusterNodesLocal = {
            "10.0.0.2:7000",
            "10.0.0.3:7001",
            "10.0.0.4:7002",
    }; static String[] clusterNodesRemote = {
            "nosql.gursky.sk:6380",
            "nosql2.gursky.sk:6380",
            "nosql3.gursky.sk:6380",
    };
    static String HOSTNAME = "localhost";
    static String PASSWORD = "dh38fhw0238923df89djkd93la9fjs0mq9gjflv9jkddj934df90rj";

    @Bean
    public RedisClusterConfiguration getRedisClusterConfiguration(){
        return new RedisClusterConfiguration(Arrays.asList(clusterNodesRemote));
    }

    @Bean
    public RedisConnectionFactory connectionFactory() {
        RedisClusterConfiguration clusterConfiguration = getRedisClusterConfiguration();
//        standaloneConfiguration.setPassword(PASSWORD);
        LettuceConnectionFactory factory = new LettuceConnectionFactory(clusterConfiguration);
        factory.afterPropertiesSet();//spring by to mal robit sam, pre istotu to dame este raz
        return factory;
    }
    @Bean
    public RedisConnection redisConnection(RedisConnectionFactory factory){
        return factory.getConnection();
    }

    @Bean
    public RedisTemplate<String, String> getRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String,String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(new StringRedisSerializer());
//        template.afterPropertiesSet();
        return template;
    }

}
