package org.ojothepojo.lifx.event;


import lombok.Data;
import org.ojothepojo.lifx.message.Message;

@Data
public class MessageReceivedEvent {

    private Message message;

    public MessageReceivedEvent(Message message) {
        this.message= message;
    }

}
