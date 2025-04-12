package com.RIT.ScheduleGenerator.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ai.timeString.spring.ai.chat.ChatClient;
import ai.timeString.spring.ai.chat.model.ChatModel;
import ai.timeString.spring.ai.prompt.Prompt;
import java.util.List;

@RestController
public class GeminiPrompt {

    @Autowired
    private ChatModel chatModel; // Example using ChatModel, you may need to configure it specifically for Gemini

    @PostMapping("/gemini")
    public String getGeminiResponse(@RequestBody String userPrompt) {
        Prompt prompt = new Prompt(List.of("user:" + userPrompt, "assistant:")); // Example prompt structure
        String response = chatModel.call(prompt); // Use your configured chat client
        return response;
    }
}