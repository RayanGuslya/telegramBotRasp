package settings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class FirstTelegramBotApplication{
	public static void main(String[] args) throws TelegramApiException {
		SpringApplication.run(FirstTelegramBotApplication.class, args);
		CommandBot bot = new CommandBot();
//		TestCommandBot bot = new TestCommandBot();
		TelegramBotsApi telegramBot = new TelegramBotsApi(DefaultBotSession.class);
		telegramBot.registerBot(bot);
	}

}
