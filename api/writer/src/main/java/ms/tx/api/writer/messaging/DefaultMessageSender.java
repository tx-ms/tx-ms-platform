package ms.tx.api.writer.messaging;

import ms.tx.api.writer.messaging.type.PayloadEventType;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultMessageSender implements MessageSender {

    private final Map<String, Queue> queues;

    private final AmqpAdmin amqpAdmin;

    private final AmqpTemplate template;

    public DefaultMessageSender(AmqpAdmin amqpAdmin, AmqpTemplate template) {
        this.amqpAdmin = amqpAdmin;
        this.template = template;
        this.queues = new HashMap<>();
    }

    @Override
    public <T extends PayloadEventType> void send(T payload) {
        var type = payload.getClass().getSimpleName();
        if (!this.queues.containsKey(type)) {
            var queue = new Queue(type);
            this.amqpAdmin.declareQueue(queue);
            this.queues.put(type, queue);
        }

        this.template.convertAndSend(type, payload);
    }
}
