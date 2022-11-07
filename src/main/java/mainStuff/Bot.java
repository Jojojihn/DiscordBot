package mainStuff;

import databaseStuff.DBManager;
import input.ButtonBoi;
import input.ListeningBoi;
import input.ObedientBoi;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.managers.Presence;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Bot {
    static JDA bot;
    public static void main(String[] args) throws Exception {
        DBManager dbManager = new DBManager();
        dbManager.connect();
        URL tokenUrl = Bot.class.getResource("/token.txt");
        assert tokenUrl != null;
        String token = Files.readString(Paths.get(tokenUrl.toURI()));
        bot = JDABuilder.createDefault(token, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS)
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
        if(input.equals("Y") || input.equals("y")){
            updateCommands();
        }else{
            System.out.println("Commands not updated");
        }
        dbManager.sendSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT, nickname TEXT, currency INTEGER)");

    }

    public static void updateCommands() throws InterruptedException {
        for(Guild guild : bot.getGuilds()){
            for(Command c : guild.retrieveCommands().complete()){
                c.delete().queue();
            }
            Thread.sleep(10);
            guild.updateCommands().addCommands(
                    Commands.slash("ping", "Pong"),
                    Commands.slash("help", "I'll do my best to help :)"),
                    Commands.slash("gayporn", "It's a secret"),
                    Commands.slash("callme", "Tell me how to talk to you")
            ).queue();
        }
    }
    public static void updateStatus(String status){
        bot.getPresence().setActivity(Activity.of(Activity.ActivityType.PLAYING, status));
    }

}
