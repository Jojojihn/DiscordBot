package input;

import databaseStuff.DBManager;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.sql.SQLException;

public class ButtonBoi extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("daddy")) {
            try {
                DBManager dbManager = new DBManager();
                dbManager.connect();
                dbManager.sendSQL("UPDATE users SET nickname = 'Daddy' WHERE id = " + event.getUser().getId());
                event.reply("Alright Daddy").setEphemeral(true).queue();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else if (event.getComponentId().equals("mommy")) {
            try {
                DBManager dbManager = new DBManager();
                dbManager.connect();
                dbManager.sendSQL("UPDATE users SET nickname = 'Mommy' WHERE id = " + event.getUser().getId());
                event.reply("Alright Mommy").setEphemeral(true).queue();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else if (event.getComponentId().equals("master")) {
            try {
                DBManager dbManager = new DBManager();
                dbManager.connect();
                dbManager.sendSQL("UPDATE users SET nickname = 'Master' WHERE id = " + event.getUser().getId());
                event.reply("Alright Master").setEphemeral(true).queue();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else if (event.getComponentId().equals("baby")) {
            try {
                DBManager dbManager = new DBManager();
                dbManager.connect();
                dbManager.sendSQL("UPDATE users SET nickname = 'Baby' WHERE id = " + event.getUser().getId());
                event.reply("Alright Baby").setEphemeral(true).queue();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else if (event.getComponentId().equals("custom")) {
            TextInput nickname = TextInput.create("nickname", "What Nickname do you want?", TextInputStyle.SHORT)
                    .setPlaceholder("Please choose a horny one. UwU")
                    .setMaxLength(20)
                    .build();
            Modal modal = Modal.create("customNick", "Custom Nickname")
                    .addActionRows(ActionRow.of(nickname))
                    .build();
            event.replyModal(modal).queue();
        }
    }
    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (event.getModalId().equals("customNick")) {
            String nick = event.getValue("nickname").getAsString();
            try {
                DBManager dbManager = new DBManager();
                dbManager.connect();
                dbManager.sendSQL("UPDATE users SET nickname = '"+nick+"' WHERE id = " + event.getUser().getId());
                event.reply("Alright "+nick).setEphemeral(true).queue();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
