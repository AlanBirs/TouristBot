package org.example.handlers;

import org.example.constant.BotState;
import org.example.constant.Command;
import org.example.model.CityEntity;
import org.example.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class InputMessageHandler {

    BotState botState;

    public BotState getBotState(Message message){

        String inputMsg = message.getText();

        switch(inputMsg){
            case "/start":
                botState = BotState.START;
                break;
            default:
                botState = BotState.CITY_SEARCH;
                break;
        }

        return botState;
    }

    public String getMessageText(BotState botState, Message message, CityRepository cityRepository){
        String answer;
        if(botState == BotState.CITY_SEARCH) {
            try {
                CityEntity cityEntity = cityRepository.findFirstByCityName(message.getText());
                answer = cityEntity.getCityDescription();
            }catch (NullPointerException e){
                answer = "Такой город не найден.";
            }
            return answer;
        }
        else if (botState == BotState.START)
            return "Введите название города, и мы пришлём вам информацию о нём.";
        return "Ошибка! Похоже, что-то снова сломалось.";
    }
}
