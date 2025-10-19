package com.racetalk.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.racetalk.entity.ChatMessage;
import com.racetalk.entity.User;
import com.racetalk.service.ChatMessageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "Chat", urlPatterns = "/chat")
public class ChatServlet extends HttpServlet {
    private final static ObjectMapper mapper = new ObjectMapper();

    private ChatMessageService chatMessageService;

    @Override
    public void init() {
        chatMessageService = (ChatMessageService) getServletContext().getAttribute("chatMessageService");
        if (chatMessageService == null) {
            throw new IllegalStateException("ChatMessageService not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ChatMessage> messages = chatMessageService.getAllMessages();
        resp.setContentType("application/json;charset=UTF-8");

        List<Map<String, String>> jsonList = messages.stream()
                .map(m -> Map.of(
                        "username", m.getUser().getUsername(),
                        "content", m.getContent(),
                        "createdAt", m.getCreatedAt().toString()
                ))
                .collect(Collectors.toList());

        mapper.writeValue(resp.getWriter(), jsonList);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User currentUser = (User) req.getSession().getAttribute("user");

        String messageText = req.getParameter("message");
        resp.setContentType("application/json;charset=UTF-8");

        if (messageText == null || messageText.trim().isEmpty()) {
            mapper.writeValue(resp.getWriter(), Map.of("success", false, "message", "Сообщение не может быть пустым"));
            return;
        }

        ChatMessage message = new ChatMessage();
        message.setUser(currentUser);
        message.setContent(messageText.trim());
        message.setCreatedAt(LocalDateTime.now());

        chatMessageService.postMessage(message);

        mapper.writeValue(resp.getWriter(), Map.of("success", true, "message", "Сообщение отправлено"));
    }
}