package com.project.uncleedikbot.bot;

import com.project.uncleedikbot.service.util.RandomUtil;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.security.SecureRandom;

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
        sticker.setSticker(new InputFile(new File(getRandomEdStickers())));
        return sticker;
    }

    public String getRandomEdStickers() {
        int n = RandomUtil.getRandomNumber(30);
        if (n > 25) {
            return "stickers/sticker.webp";
        } else if (n>20) {
            return "stickers/sticker1.webp";
        } else if (n > 15) {
            return "stickers/sticker 2.webp";
        } else if (n > 10) {
            return "stickers/sticker 3.webp";
        } else if (n > 5) {
            return "stickers/sticker 4.webp";
        } else {
            return "stickers/sticker 5.webp";
        }
    }

}
