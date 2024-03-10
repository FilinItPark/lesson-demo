package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Random;

public class GuessNumberBot extends TelegramLongPollingBot {

    private Random random = new Random();
    private int targetNumber;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() == true) {
            Message messageFromUser = update.getMessage();

            if (messageFromUser.hasText() == true) {
                String textFromMessage = messageFromUser.getText();

                // log2 (диапазон)

                String chatId = messageFromUser.getChatId().toString();
                // -2^64;2^64-1

                if (textFromMessage.equals("/start")) {
                    targetNumber = random.nextInt(101);
                    sendMessage(chatId, "Привет! Я загадал число от 0 до 100! Попробуй отгадать :)");
                } else {
                    int guessNumber = Integer.parseInt(textFromMessage);

                    if (guessNumber == targetNumber) {
                        sendMessage(chatId, "Ура! Ты отгадал число!");
                    } else if(guessNumber < targetNumber) {
                        sendMessage(chatId, "Загаданное число больше введеного :((");
                    } else {
                        sendMessage(chatId, "Загаданное число меньше введеного :((");
                    }
                }
            }
        }
    }

    void sendMessage(String chatId, String textFromMessage) {
        SendMessage sendMessage = new SendMessage(chatId, textFromMessage);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {

        }
    }


    @Override
    public String getBotUsername() {
        // Здесь нужно указать имя бота
        return "openlessondemotenmarchbot";
    }

    @Override
    public String getBotToken() {
        // Здесь нужно указать токен, полученный от BotFather
        return "7065100018:AAHBQZYGOqTOx80-FmVK4pUzHayd4icCvtM";
    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            botsApi.registerBot(new GuessNumberBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
