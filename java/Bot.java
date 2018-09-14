import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    
    public static void main(String[] args) {

        ApiContextInitializer.init();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {

            telegramBotsApi.registerBot(new Bot());

        } catch (TelegramApiRequestException e) {

            e.printStackTrace();

        }

    }

    public void sendMsg(Message message, String text) {

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableMarkdown(true);

        sendMessage.setChatId(message.getChatId().toString());

        sendMessage.setReplyToMessageId(message.getMessageId());

        sendMessage.setText(text);

        try {

            setButtons(sendMessage);

            sendMessage(sendMessage);

        }
        catch (TelegramApiException e) {

            e.printStackTrace();

        }

    }

    private void sendMessage(SendMessage sendMessage) {
    }

    public void onUpdateReceived(Update update) {

        Model model = new Model();

        Message message = update.getMessage();

        if (message != null && message.hasText()) {

            switch (message.getText()) {

                case "/help":

                    sendMsg(message, "Please, add the city.");

                    break;


                default:

                    try {

                        sendMsg(message, Weather.getWeather(message.getText(), model));

                    } catch (IOException e) {

                        sendMsg(message, "City not found&");

                    }

            }

        }

    }

    public void setButtons(SendMessage sendMessage) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);

        replyKeyboardMarkup.setResizeKeyboard(true);

        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("/help"));

        keyboardRowList.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    @Override
    public void onUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update update) {

    }

    public String getBotUsername() {

        return "Myweather_bot";

    }

    public String getBotToken() {

        return "600133059:AAFGaW08W2wM3JyzfRHUsHN4UV5PYpOR1AQ";

    }

}