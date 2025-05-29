package com.openclassroom.chatbox.controller;


import com.openclassroom.chatbox.model.AppUser;
import com.openclassroom.chatbox.model.Message;
import com.openclassroom.chatbox.repository.AppUserRepository;
import com.openclassroom.chatbox.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;


@Slf4j
@Controller
public class ChatController {

    private final AppUserRepository userRepository;
    private final MessageRepository messageRepository;

    public ChatController(AppUserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @GetMapping("/chat")
    public String chatPage(Model model, Principal principal) {
        AppUser current = userRepository.findByUsername(principal.getName()).orElseThrow();

        // destinataires possibles
        List<AppUser> recipients = current.getRole().equals("SAV")
                ? userRepository.findAll().stream().filter(u -> !u.getUsername().equals("sav")).toList()
                : List.of(userRepository.findByUsername("sav").orElseThrow());

        List<AppUser> users= userRepository.findAll();
        log.info("users: {}", users);

        // messages visibles
        List<Message> messages = messageRepository.findBySenderIdOrReceiverId(current.getId(), current.getId());
        messages.forEach(m -> {
            m.setDateAsDate(Date.from(m.getTimestamp().toInstant(ZoneOffset.UTC)));
            m.setSenderUsername(userRepository.findById(m.getSenderId()).map(AppUser::getUsername).orElse("inconnu"));
            m.setReceiverUsername(userRepository.findById(m.getReceiverId()).map(AppUser::getUsername).orElse("inconnu"));
        });

        model.addAttribute("messages", messages);
        model.addAttribute("user", current);
        model.addAttribute("recipients", recipients);


        return "chat-index";
    }

    @PostMapping("/chat/send")
    public String sendMessage(@RequestParam Long toId, @RequestParam String content, Principal principal) {
        AppUser sender = userRepository.findByUsername(principal.getName()).orElseThrow();

        Message msg = new Message();
        msg.setSenderId(sender.getId());
        msg.setReceiverId(toId);
        msg.setContent(content);
        msg.setTimestamp(LocalDateTime.now());

        messageRepository.save(msg);

        return "redirect:/chat";
    }
}

