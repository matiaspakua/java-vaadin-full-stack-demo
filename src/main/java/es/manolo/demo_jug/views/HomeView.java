package es.manolo.demo_jug.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.ai.chat.client.ChatClient;
import org.vaadin.voiceengine.VoiceEngine;

@Route("")
public class HomeView extends VerticalLayout {
    private TextField textField = new TextField();
    private Button button = new Button("Ask");
    private TextArea textArea = new TextArea();

    public HomeView(ChatClient.Builder chatClientBuilder) {
        ChatClient chatClient = chatClientBuilder.build();
        VoiceEngine voiceEngine = new VoiceEngine().setButtons(VoiceEngine.Buttons.MICROPHONE, VoiceEngine.Buttons.PLAY, VoiceEngine.Buttons.CANCEL);

        HorizontalLayout question = new HorizontalLayout(textField, button, voiceEngine);
        UI ui = UI.getCurrent();
        button.addClickListener(e -> {
            chatClient.prompt().user(textField.getValue()).stream().content().subscribe(token -> {
                ui.access(() -> {
                    textArea.setValue(textArea.getValue() + token);
                });
            });
//            String content = chatClient.prompt().user(textField.getValue()).call().content();
//            textArea.setValue(content);
        });

        voiceEngine.addEndListener(e -> {
            textField.setValue(voiceEngine.getRecorded());
            button.click();
        });
        this.setSizeFull();
        textArea.setSizeFull();
        question.setWidthFull();
        textField.setWidthFull();

        add(question, textArea);
    }
}
