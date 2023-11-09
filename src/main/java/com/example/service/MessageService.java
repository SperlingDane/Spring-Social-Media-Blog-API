package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<Message> getMessageById(int id){
        Optional<Message> message = messageRepository.findById(id);
        
        return message;
    }

    public Optional<Message> deleteMessageById(int id){
        Optional<Message> message = messageRepository.findById(id);
        if(message != null){
            messageRepository.deleteById(id);
            return message;
        }
        return null;
    }

    public Message updateMessageById(Message message, int id){
        Message original = messageRepository.getById(id);
        if(original != null && message.getMessage_text() != "" && message.getMessage_text().length() < 255){
            original.setMessage_text(message.getMessage_text());
            return messageRepository.save(original);
        }
        
        
        return null;
    }

    public List<Message> getMessagesByUserId(int id){
        List<Message> messageList = messageRepository.findAllByPostedBy(id);
        if(messageList != null){
            return messageList;
        }
        return null;
    }
}
