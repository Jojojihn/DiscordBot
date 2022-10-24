import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.managers.Presence;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class main {
    public static void main(String[] args) throws Exception {
        URL tokenUrl = main.class.getResource("/token.txt");
        String token = Files.readString(Paths.get(tokenUrl.toURI()));
        JDA bot = JDABuilder.createDefault(token).build();
        Presence presence = bot.getPresence();
        presence.setActivity(Activity.watching("you"));



    }
}
