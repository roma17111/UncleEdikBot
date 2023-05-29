package com.project.uncleedikbot.bot;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class UpdateController {

    private BotStart botStart;
    private TestClass testClass;


    public UpdateController(TestClass testClass) {
        this.testClass = testClass;
    }

    public void registerBot(BotStart botStart) {
        this.botStart = botStart;
    }

    @SneakyThrows
    public void checkMessage(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals("/command1")) {
                botStart.execute(testClass.sendSticker(update));
                botStart.sendMessage(testClass.testMessage(update));
                return;
            }
            botStart.execute(testClass.sendSticker(update));
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId());
            message.setText("Hello " + update.getMessage().getChat().getFirstName());
            try {
                botStart.execute(message);
            } catch (TelegramApiException e) {
                log.error(e.getMessage(),e);
            }
        }
    }

}
