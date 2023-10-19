package com.unimuenster.govlearnapi.common.responsewrapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Response<T> {
    public T payload;
    public Message[] messages;

    public static <K> Response of(K payload, Message[] messages){
        Response<K> response = new Response<>();
        response.setMessages(messages);
        response.setPayload(payload);
        return response;
    }

    public static <K> Response of(K payload, Message message){
        Response<K> response = new Response<>();
        response.setMessages(new Message[] {message});
        response.setPayload(payload);
        return response;
    }

    public static <K> Response of(K payload, boolean success){
        Response<K> response = new Response<>();

        if (success){
            response.setMessages(new Message[] {new Message(Message.SUCCESS)});
        }else{
            response.setMessages(new Message[] {new Message(Message.FAILURE)});
        }
        response.setPayload(payload);
        return response;
    }

    public static Response of(boolean success){
        if (success){
            return of(new Message(Message.SUCCESS));
        }else{
            return of(new Message(Message.FAILURE));
        }
    }

    public static Response of(Message payload){
        Response response = new Response<>();
        response.setMessages(new Message[]{payload});
        return response;
    }

    public static Response of(Message[] messages){
        Response response = new Response<>();
        response.setMessages(messages);
        return response;
    }

    public static <K> Response of(K payload){
        if ( payload instanceof Message) {
            return of((Message) payload);
        }

        Response<K> response = new Response<>();
        response.setPayload(payload);
        return response;
    }
}
