package mainStuff;

import input.ListeningBoi;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.managers.Presence;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Bot {
    static JDA bot;
    public static void main(String[] args) throws Exception {
        URL tokenUrl = Bot.class.getResource("/token.txt");
        String token = Files.readString(Paths.get(tokenUrl.toURI()));
        bot = JDABuilder.createDefault(token, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS)
                .addEventListeners(new ListeningBoi())
                .build();
        Presence presence = bot.getPresence();
        presence.setActivity(Activity.listening("to your commands"));
        bot.awaitReady();
    }

    public static void updateStatus(String status){
        bot.getPresence().setActivity(Activity.of(Activity.ActivityType.PLAYING, status));
    }

}
