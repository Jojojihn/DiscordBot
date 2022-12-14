package mainStuff;

import api.APImain;
import input.ButtonBoi;
import input.ListeningBoi;
import input.ObedientBoi;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.managers.Presence;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static databaseStuff.DBManager.connect;
import static databaseStuff.DBManager.disconnect;

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
                .addEventListeners(new Games.WaifuCollection.MainWaifu())
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
        if(scanner.nextLine().equals("exit")){
            disconnect();
        }
    }

    public static void updateCommands(){
        for(Guild guild : bot.getGuilds()){
            guild.updateCommands().addCommands(
                    Commands.slash("help", "I'll do my best to help :)"),
                    Commands.slash("gayporn", "It's a secret"),
                    Commands.slash("callme", "Tell me how to talk to you"),
                    Commands.slash("vctest", "Test the planned vc feature")
                            .addOption(OptionType.CHANNEL, "vc", "Which VC do you want me to join?"),
                    Commands.slash("owocheck", "Check how many OwOs a User has.")
                            .addOption(OptionType.USER, "user", "Which user do you want to check?"),
                    Commands.slash("addcustomwaifu", "Adds a fully custom waifu")
                            .addOption(OptionType.STRING, "name", "What is the name of the waifu?")
                            .addOption(OptionType.STRING, "description", "What is the description of the waifu?")
                            .addOption(OptionType.INTEGER, "rarity", "What is the rarity of the waifu?")
                            .addOption(OptionType.INTEGER, "price", "How many OwOs for this waifu?")
                            .addOption(OptionType.STRING, "image", "What is link to the image of the waifu?"),
                    Commands.slash("showwaifu", "Shows a specific waifu")
                            .addOption(OptionType.STRING, "name", "What is the name of the waifu?"),
                    Commands.slash("addrole", "Adds a role with bot access level")
                            .addOption(OptionType.ROLE, "role", "Whats the role?")
                            .addOption(OptionType.STRING , "accessgroup", "What is the access group? (To see all the access groups, use the command 'showaccessgroups')"),
                    Commands.slash("showaccessgroups", "Shows all the access groups"),
                    Commands.slash("removewaifu", "Removes a waifu")
                            .addOption(OptionType.STRING, "name", "What is the name of the waifu?")
            ).queue();
        }
    }
    public static void updateStatus(String status){
        bot.getPresence().setActivity(Activity.of(Activity.ActivityType.PLAYING, status));
    }

}
