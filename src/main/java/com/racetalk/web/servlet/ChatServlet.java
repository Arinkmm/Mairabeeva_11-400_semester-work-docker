package com.racetalk.web.servlet;

import com.racetalk.entity.ChatMessage;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.ChatMessageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Chat", urlPatterns = "/chat")
public class ChatServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ChatServlet.class);
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
        try {
            List<ChatMessage> messages = chatMessageService.getAllMessages();
            req.setAttribute("messages", messages);
            req.getRequestDispatcher("/templates/chat.ftl").forward(req, resp);
        } catch (ServiceException e) {
            logger.error("Failed to load chat messages", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}
