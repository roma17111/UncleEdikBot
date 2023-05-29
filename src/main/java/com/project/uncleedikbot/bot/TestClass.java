package com.project.uncleedikbot.bot;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;

@Component
public class TestClass {

    public SendMessage testMessage(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText("Это тестовая команда");
        return message;

    }

    public SendSticker sendSticker(Update update) {

        SendSticker sticker = new SendSticker();
        sticker.setChatId(update.getMessage().getChatId());
        sticker.setSticker(new InputFile(new File("stickers/sticker.webp")));
        return sticker;
    }

}
