package ms.tx.api.users.messaging;


import ms.tx.api.users.messaging.type.PayloadEventType;

public interface MessageSender {

    <T extends PayloadEventType> void send(T payload);

}
