package com.racetalk.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.racetalk.dao.impl.ChatMessageDaoImpl;
import com.racetalk.entity.ChatMessage;
import com.racetalk.entity.User;
import com.racetalk.service.ChatMessageService;
import com.racetalk.service.impl.ChatMessageServiceImpl;

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
    private ChatMessageService chatService = new ChatMessageServiceImpl(new ChatMessageDaoImpl());
    private final static ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/index");
        }

        List<ChatMessage> messages = chatService.getAllMessages();
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
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            User currentUser = (User) req.getSession().getAttribute("user");
            if (currentUser == null) {
                resp.sendRedirect(req.getContextPath() + "/index");
                return;
            }

            String messageText = req.getParameter("message");
            if (messageText == null) {
                req.setAttribute("MessageErrorMessage", "Сообщение не может быть пустым");
                doGet(req, resp);
            }

            ChatMessage message = new ChatMessage();
            message.setUser(currentUser);
            message.setContent(messageText);
            message.setCreatedAt(LocalDateTime.now());

            chatService.postMessage(message);

            resp.sendRedirect(req.getContextPath() + "/chat");
        }
}
