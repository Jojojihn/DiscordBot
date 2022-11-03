package input;

import databaseStuff.DBManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ObedientBoi extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("ping")) {
            event.reply("Pong!").queue();
        } else if (event.getName().equals("help")) {
            event.reply("I'll do my best to help :)").queue();
        } else if (event.getName().equals("gaypyorn")) {
            event.getChannel().sendMessage("──────▄▌▐▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\u200B▀▀▀▀▀▀▌\n" +
                    "───▄▄██▌█ beep beep ▄▄▄▌▐██▌█ gay porn delivery\n" +
                    "███████▌█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\u200B▄▄▄▄▄▄▌\n" +
                    "▀(@)▀▀▀▀▀▀▀(@)(@)▀▀▀▀▀▀▀▀▀▀▀▀▀\u200B▀▀▀▀(@)▀").queue();
        } else if (event.getName().equals("bot")) {
            TextInput subject = TextInput.create("subject", "What do you want me to do?", TextInputStyle.SHORT)
                    .setPlaceholder("I'll be a good bot, I'll do anything for you master")
                    .setMinLength(10)
                    .setMaxLength(100) // or setRequiredRange(10, 100)
                    .build();

            Modal modal = Modal.create("modmail", "Im an obedient Bot UwU")
                    .addActionRows(ActionRow.of(subject))
                    .build();

            event.replyModal(modal).queue();
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
                        dbManager.sendSQL("INSERT INTO users (id, name nickname, currency) VALUES ('" + event.getMember().getId() + "'" + event.getMember().getEffectiveName() + "'" + "', ' User ', '0')");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                rs = dbManager.sendSQLwithResult("SELECT * FROM users WHERE id = '" + event.getMember().getId() + "'");
                String nickname = rs.getString("nickname");
                event.reply("What do you want me to call you, " + nickname + "?")
                        .addActionRow(
                                Button.secondary("daddy", "Daddy"),
                                Button.secondary("mommy", "Mommy"),
                                Button.secondary("master", "Master"),
                                Button.secondary("baby", "Baby"),
                                Button.secondary("user", "User"))
                        .queue();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                dbManager.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
