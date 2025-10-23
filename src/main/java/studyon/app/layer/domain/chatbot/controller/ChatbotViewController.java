package studyon.app.layer.domain.chatbot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import studyon.app.layer.base.utils.ViewUtils;
import studyon.app.common.enums.View;

@Slf4j
@Controller
@RequestMapping("/chatbot")
@RequiredArgsConstructor
public class ChatbotViewController {

    @GetMapping("/chat")
    public String chatbotPage(Model model) {
        // JSP 렌더링
        return ViewUtils.returnView(model, View.CHATBOT, "chatbot");
    }
}
