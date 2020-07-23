package org.example;

import org.example.constant.BotState;
import org.example.handlers.InputMessageHandler;
import org.example.model.CityEntity;
import org.example.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class EvilTouristBot extends TelegramLongPollingBot {

    @Autowired
    private CityRepository cityRepository;

    BotState botState;

    private static String TOKEN = "1352587367:AAGiBZnPKONJ6aJ6JR6qv5JACJbvyEq2tWE";
    private static String USERNAME = "EvilTouristBot";

    public EvilTouristBot(DefaultBotOptions botOptions) { super(botOptions); }

    public EvilTouristBot() {
        super();
    }

    @Override
    public void onUpdateReceived(Update update){
        if(update.getMessage() != null && update.getMessage().hasText()){
            long chatId = update.getMessage().getChatId();

            InputMessageHandler inputMessageHandler = new InputMessageHandler();

            botState = inputMessageHandler.getBotState(update.getMessage());

            String messageText = inputMessageHandler.getMessageText(botState, update.getMessage(), cityRepository);
            try{
                execute(new SendMessage(chatId, messageText));
            } catch(TelegramApiException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }
}
