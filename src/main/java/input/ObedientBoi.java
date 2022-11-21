package input;

import databaseStuff.DBManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import util.UsefulFunctions;

import java.sql.ResultSet;
import java.sql.SQLException;

import static vcStuff.GoodGirl.connectToVC;

public class ObedientBoi extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("ping")) {
            event.reply("Pong!").queue();
        } else if (event.getName().equals("help")) {
            if (event.getMember().getEffectiveName().equals("Big Boy")) {
                event.reply("I'll do my best to help Small Boy :)").queue();
            } else {
                String nick = UsefulFunctions.getNick(event.getMember().getId());
                event.reply("I'll do my best to help " + nick + " :)").queue();
            }
        } else if (event.getName().equals("gayporn")) {
            event.getChannel().sendMessage("""
                    ──────▄▌▐▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\u200B▀▀▀▀▀▀▌
                    ───▄▄██▌█ beep beep ▄▄▄▌▐██▌█ gay porn delivery
                    ███████▌█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\u200B▄▄▄▄▄▄▌
                    ▀(@)▀▀▀▀▀▀▀(@)(@)▀▀▀▀▀▀▀▀▀▀▀▀▀\u200B▀▀▀▀(@)▀""").queue();
        } else if (event.getName().equals("callme")) {
            DBManager dbManager = new DBManager();
            ResultSet rs;
            try {
                dbManager.connect();
                try {
                    rs = dbManager.sendSQLwithResult("SELECT * FROM users WHERE id = '" + event.getMember().getId() + "'");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    if (rs.getString(1) == null) {
                        dbManager.sendSQL("INSERT INTO users (id, name, nickname, currency) VALUES ('" + event.getMember().getId() + "' ," + "'" + event.getMember().getEffectiveName() + "', ' User ', '0')");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                String nickname = UsefulFunctions.getNick(event.getMember().getId());
                event.reply("What do you want me to call you, " + nickname + "?")
                        .setEphemeral(true)
                        .addActionRow(
                                Button.secondary("daddy", "Daddy"),
                                Button.secondary("mommy", "Mommy"),
                                Button.secondary("master", "Master"),
                                Button.secondary("baby", "Baby"),
                                Button.secondary("custom", "Custom"))
                        .queue();
                dbManager.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else if(event.getName().equals("vctest")){
            connectToVC(event.getOption("vc").getAsString(), event.getGuild().getName());
            System.out.println(event.getOption("vc").getAsString());
        }
    }
}
