package sk.upjs.nosql_redis_chat;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class SubscriberService extends Service<Void> {
    private RedisConnection redisConnection;
    private ObservableList<String> spravy;

    public SubscriberService(RedisConnection connection,ObservableList<String> spravy){
        redisConnection = connection;
        this.spravy = spravy;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                StringRedisSerializer serializer = new StringRedisSerializer();
                byte[] channel = serializer.serialize("chat*");
                redisConnection.pSubscribe(new MessageListener() {
                    @Override
                    public void onMessage(Message message, byte[] pattern) {
                        String text = serializer.deserialize(message.getBody());
                        Platform.runLater(()->spravy.add(text));
                    }
                }, channel);
                return null;
            }
        };
    }
}
