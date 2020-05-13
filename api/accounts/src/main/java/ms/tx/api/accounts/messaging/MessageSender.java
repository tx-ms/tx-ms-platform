package ms.tx.api.accounts.messaging;


import ms.tx.api.accounts.messaging.type.PayloadEventType;

public interface MessageSender {

    <T extends PayloadEventType> void send(T payload);

}
