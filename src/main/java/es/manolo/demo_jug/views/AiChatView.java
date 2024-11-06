package es.manolo.demo_jug.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.ai.chat.client.ChatClient;
import org.vaadin.voiceengine.VoiceEngine;


@Route(value = "ai-chat", layout = MainLayout.class)
public class AiChatView extends VerticalLayout {
    AiChatView(ChatClient.Builder builder) {
        ChatClient openai = builder.build();
        VoiceEngine voiceEngine = new VoiceEngine().setButtons(VoiceEngine.Buttons.MICROPHONE, VoiceEngine.Buttons.PLAY, VoiceEngine.Buttons.CANCEL);
        UI ui = UI.getCurrent();

        TextArea response = new TextArea();
        TextField input = new TextField();
        input.setPlaceholder("Ask me anything");
        Button send = new Button("Ask", e -> {
            String question = input.getValue();
            openai.prompt().user(question).stream().content().subscribe(token -> {
                ui.access(() -> response.setValue(response.getValue() + token ));
            }, null, () -> {
                ui.access(() -> voiceEngine.play(response.getValue()));
            });
        });
        send.addClickShortcut(Key.ENTER);

        voiceEngine.addEndListener(e -> {
            input.setValue(voiceEngine.getRecorded());
            send.click();
        });

        HorizontalLayout layout = new HorizontalLayout(input, send, voiceEngine);
        add(layout, response);

        setSizeFull();
        layout.setWidthFull();
        response.setWidthFull();
        layout.expand(input);
        expand(response);


    }
}
