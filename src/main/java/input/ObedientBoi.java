package input;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import util.UsefulFunctions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static databaseStuff.DBManager.sendSQL;
import static databaseStuff.DBManager.sendSQLwithResult;
import static vcStuff.GoodGirl.connectToVC;

public class ObedientBoi extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("ping")) {
            event.reply("Pong!").queue();
        } else if (event.getName().equals("help")) {
            if (Objects.requireNonNull(event.getMember()).getEffectiveName().equals("Big Boy")) {
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
            ResultSet rs;
            rs = sendSQLwithResult("SELECT * FROM users WHERE id = '" + Objects.requireNonNull(event.getMember()).getId() + "'");
            try {
                if (rs.getString(1) == null) {
                    sendSQL("INSERT INTO users (id, name, nickname, currency) VALUES ('" + event.getMember().getId() + "' ," + "'" + event.getMember().getEffectiveName() + "', ' User ', '0')");

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
                }
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (event.getName().equals("vctest")) {
            connectToVC(Objects.requireNonNull(event.getOption("vc")).getAsString(), Objects.requireNonNull(event.getGuild()).getName());
            System.out.println(Objects.requireNonNull(event.getOption("vc")).getAsString());
        } else if (event.getName().equals("owocheck")) {
            if (event.getOption("user") == null) {
                event.reply("Give me a user to check!").setEphemeral(true).queue();
            } else {
                try {
                    ResultSet rso = sendSQLwithResult("SELECT currency, name FROM users WHERE id = '" + Objects.requireNonNull(event.getOption("user")).getAsUser().getId() + "'");
                    int currency = rso.getInt(1);
                    String nick = rso.getString(2);
                    if (nick == null) {
                        event.reply("This User does not have any OwO's").setEphemeral(true).queue();
                    } else {
                        event.reply(nick + " has " + currency + " OwO's").queue();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

