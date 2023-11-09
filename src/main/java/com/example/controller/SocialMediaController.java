package com.example.controller;

import java.util.List;
import java.util.Optional;

import javax.swing.RepaintManager;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.blankUsernameException;
import com.example.exception.InvalidUsernameException;

import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;

    @PostMapping("/register")
    private ResponseEntity<Account> register(@RequestBody Account account) {
        try{
            if(!accountService.accountExists(account)){
                if((account.getUsername() != null || account.getUsername() != "") && account.getPassword().length() > 4){
                    
                    Account createdAccount = accountService.registerAccount(new Account(account.getUsername(), account.getPassword()));
                    
                    return new ResponseEntity<>(createdAccount, HttpStatus.OK);
                    
                }
                else{
                    throw new Exception("Username is blank or Password is too short");
                }
            }
            else{
                throw new InvalidUsernameException("Username already exists");
            }
    }
    catch(InvalidUsernameException e){
            return ResponseEntity.status(409).body(null);
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping("/login")
    private ResponseEntity<Account> login(@RequestBody Account account){
        Account existingAccount = accountService.loginToAccount(account);
        if(existingAccount == null){
            return ResponseEntity.status(401).body(null);
        }
        return new ResponseEntity<Account>(existingAccount, HttpStatus.OK);
    }

    @PostMapping("/messages")
    private ResponseEntity<Message> createMessage(@RequestBody Message message){
        if(accountService.accountExists(message.getPosted_by())){
            Message newMessage = messageService.createMessage(message);
            if(newMessage != null){
                return new ResponseEntity<>(newMessage, HttpStatus.OK);
            }
        }

        return ResponseEntity.status(400).body(null);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messageList = messageService.getAllMessages();
        if (messageList != null){
            return ResponseEntity.status(200).body(messageList);
        }
        return null;
    }
    
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Optional<Message>> getMessageByIdHandler(@PathVariable("message_id") Integer messageId){
        Optional<Message> message = messageService.getMessageById(messageId);
        if(message.isEmpty()){
            return ResponseEntity.status(200).body(null);
        }
        return ResponseEntity.status(200).body(message);
    }
}
