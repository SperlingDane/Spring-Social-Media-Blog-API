package com.example.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    public MessageRepository messageRepository;

    public List<Message> getAllMessages(){
        Iterable<Message> message = messageRepository.findAll();
        List<Message> messageList = new ArrayList<Message>();

        message.forEach(messageList::add);
        return messageList;
    }
}
