package ms.tx.api.writer.messaging;


import ms.tx.api.writer.messaging.type.PayloadEventType;

public interface MessageSender {

    <T extends PayloadEventType> void send(T payload);

}
