package com.racetalk.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.racetalk.entity.ChatMessage;
import com.racetalk.entity.User;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.ChatMessageService;
import com.racetalk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "ChatHandler", urlPatterns = "/chat/handle")
public class ChatHandler extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(ChatHandler.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private ChatMessageService chatMessageService;
    private UserService userService;

    @Override
    public void init() {
        chatMessageService = (ChatMessageService) getServletContext().getAttribute("chatMessageService");
        userService = (UserService) getServletContext().getAttribute("userService");
        if (chatMessageService == null || userService == null) {
            throw new IllegalStateException("Services are not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            List<ChatMessage> messages = chatMessageService.getAllMessages();
            resp.setContentType("application/json;charset=UTF-8");

            List<Map<String, String>> jsonList = messages.stream()
                    .map(m -> Map.of(
                            "username", m.getUser() != null ? m.getUser().getUsername() : "Аноним",
                            "content", m.getContent(),
                            "createdAt", m.getCreatedAt().toString()
                    ))
                    .collect(Collectors.toList());

            mapper.writeValue(resp.getWriter(), jsonList);
        } catch (ServiceException e) {
            logger.error("Failed to get chat messages", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(resp.getWriter(), Map.of("error", "Failed to retrieve chat messages"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String currentUsername = (String) req.getSession().getAttribute("user");
            Optional<User> currentUserOpt = userService.getByUsername(currentUsername);
            User currentUser = currentUserOpt.get();

            String messageText = req.getParameter("message");
            resp.setContentType("application/json;charset=UTF-8");

            ChatMessage message = new ChatMessage();
            message.setUser(currentUser);
            message.setContent(messageText.trim());
            message.setCreatedAt(LocalDateTime.now());

            chatMessageService.postMessage(message);

            mapper.writeValue(resp.getWriter(), Map.of(
                    "success", true,
                    "username", currentUser.getUsername(),
                    "createdAt", message.getCreatedAt().toString()));
        } catch (ServiceException e) {
            logger.error("Failed to post chat message", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(resp.getWriter(), Map.of("error", "Failed to post chat message"));
            }
    }
}