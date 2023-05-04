package mainStuff;

import api.APImain;
import commandStuff.CommandManager;
import input.ButtonBoi;
import input.ListeningBoi;
import input.ObedientBoi;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.managers.Presence;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static databaseStuff.DBManager.connect;

public class Bot {
    static JDA bot;
    public static void main(String[] args) throws Exception {
        APImain api = new APImain();
        api.start();
        connect();
        URL tokenUrl = Bot.class.getResource("/token.txt");
        assert tokenUrl != null;
        String token = Files.readString(Paths.get(tokenUrl.toURI()));
        bot = JDABuilder.createDefault(token, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.SCHEDULED_EVENTS )
                .addEventListeners(new ListeningBoi())
                .addEventListeners(new ObedientBoi())
                .addEventListeners(new ButtonBoi())
                .build();
        Presence presence = bot.getPresence();
        presence.setActivity(Activity.listening("to your commands"));
        bot.awaitReady();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Update Commands? (y/n)");
        String input = scanner.nextLine();
        if(input.toLowerCase().strip().equals("y")){
            CommandManager.updateCommands(bot);
        }else{
            System.out.println("Commands not updated");
        }

    }
    public static void updateStatus(String status){
        bot.getPresence().setActivity(Activity.of(Activity.ActivityType.PLAYING, status));
    }

}
