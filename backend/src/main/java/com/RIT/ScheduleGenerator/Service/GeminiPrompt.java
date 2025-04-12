package com.RIT.ScheduleGenerator.Service;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.Content;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.api.Part;
import com.google.cloud.vertexai.generativemodel.GenerativeModel;
import com.google.cloud.vertexai.generativemodel.ResponseHandler;
import com.google.cloud.vertexai.generativemodel.Tool;
import com.google.protobuf.util.JsonFormat;
import java.io.IOException;
import com.RIT.ScheduleGenerator.Controller.*;
import com.RIT.ScheduleGenerator.DTO.*;
import com.RIT.ScheduleGenerator.Entity.*;
import com.RIT.ScheduleGenerator.Repository.*;

public class GeminiPrompt {

    public static void Prompt() throws IOException {
        String project = "your-gcp-project-id";
        String location = "us-central1";
        String modelName = "gemini-pro";
        String promptText = "Pull out each course and its associated data from the following xml: ";

        VertexAI vertexAI = new VertexAI(project, location);
        GenerativeModel model = new GenerativeModel(modelName, vertexAI);

        Content content = Content.newBuilder()
                .addParts(Part.newBuilder().setText(promptText))
                .build();

        GenerateContentResponse response = model.generateContent(content);

        String generatedText = ResponseHandler.getText(response);
        System.out.println("Generated Text: " + generatedText);

        vertexAI.close();
    }
}