package com.project.uncleedikbot.bot;

import com.project.uncleedikbot.entity.Sticker;
import com.project.uncleedikbot.repository.StickerRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
@Data
public class UpdateController {

    private BotStart botStart;
    private TestClass testClass;
    private final StickerRepository stickerRepository;


    public UpdateController(TestClass testClass, StickerRepository stickerRepository) {
        this.testClass = testClass;
        this.stickerRepository = stickerRepository;
    }

    public void registerBot(BotStart botStart) {
        this.botStart = botStart;
    }

    @SneakyThrows
    public void checkMessage(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
                case "/addsticker" -> {
                    registerSticker(update);
                }
                case "/mystickers" -> {
                    stickerRepository.findAllByChatId(update.getMessage().getChatId()).forEach(
                            sticker -> {
                                sendStickers(update,sticker.getStickerId());
                            }
                    );
                }
                case "/help" -> {
                    sendMessage(update,"Список команд бота\n\n" +
                            "/addsticker - Добавить стикер\n\n" +
                            "/mystickers - Список моих стикеров");
                }
                default -> {
                    Sticker sticker = getActualSticker(update);
                    if (sticker != null) {
                        stickerRepository.delete(sticker);
                        sendMessage(update, " Нужно было добавить стикер, а не ломать бота))))");
                    } else {
                        sendMessage(update,"Команды бота - /help");
                    }
                }
            }
            if (update.hasMessage() && update.getMessage().hasPhoto()) {
                Sticker sticker = getActualSticker(update);
                if (sticker != null) {
                    stickerRepository.delete(sticker);
                    sendMessage(update, " Нужно было добавить стикер, а не ломать бота))))");
                } else {
                    sendMessage(update,"Отличное фото!!!");
                }
            }
        } if (update.hasMessage() && update.getMessage().hasSticker()) {
            Sticker sticker = getActualSticker(update);
            addSticker(update, sticker);
            sendMessage(update, "Стикер добавлен");
        }
    }

    private void registerSticker(Update update) {
        Sticker sticker = new Sticker();
        sticker.setActive(true);
        sticker.setChatId(update.getMessage().getChatId());
        stickerRepository.save(sticker);
        sendMessageSticker(update);
    }

    private void sendMessageSticker(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText("\uD83C\uDFF7\uFE0F Отправьте стикер в чат");
        try {
            botStart.execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void sendMessage(Update update , String text) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText(text);
        try {
            botStart.execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    private Sticker getActualSticker(Update update) {
        return stickerRepository.findByChatIdAndIsActiveTrue(update.getMessage().getChatId());
    }

    private void addSticker(Update update, Sticker sticker) {
        sticker.setStickerId(update.getMessage().getSticker().getFileId());
        sticker.setChatId(update.getMessage().getChatId());
        sticker.setActive(false);
        stickerRepository.save(sticker);
    }

    private void sendStickers(Update update, String sticker) {

        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(update.getMessage().getChatId());
        sendSticker.setSticker(new InputFile(sticker));
        try {
            botStart.execute(sendSticker);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(),e);
        }
    }

}
