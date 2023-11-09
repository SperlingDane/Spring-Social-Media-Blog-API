package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    public MessageRepository messageRepository;

    public Message createMessage(Message message){
        if(message.getMessage_text().length() < 255 && message.getMessage_text() != ""){
            return messageRepository.save(message);
        }
        return null;
    }

    public List<Message> getAllMessages(){
        Iterable<Message> messages = messageRepository.findAll();
        List<Message> messageList = new ArrayList<Message>();
        for (Message message : messages) {
            messageList.add(message);
        }
        

        return messageList;
    }
}
