package input;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.Objects;

import static databaseStuff.DBManager.sendSQL;

public class ButtonBoi extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("daddy")) {
            sendSQL("UPDATE users SET nickname = 'Daddy' WHERE id = " + event.getUser().getId());
            event.reply("Alright Daddy").setEphemeral(true).queue();
        }else if (event.getComponentId().equals("mommy")) {
            sendSQL("UPDATE users SET nickname = 'Mommy' WHERE id = " + event.getUser().getId());
            event.reply("Alright Mommy").setEphemeral(true).queue();
        }else if (event.getComponentId().equals("master")) {
            sendSQL("UPDATE users SET nickname = 'Master' WHERE id = " + event.getUser().getId());
            event.reply("Alright Master").setEphemeral(true).queue();
        }else if (event.getComponentId().equals("baby")) {
            sendSQL("UPDATE users SET nickname = 'Baby' WHERE id = " + event.getUser().getId());
            event.reply("Alright Baby").setEphemeral(true).queue();
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
            String nick = Objects.requireNonNull(event.getValue("nickname")).getAsString();
            nick = nick.replace("'", "*");
            nick = nick.replace(";", "*");
            sendSQL("UPDATE users SET nickname = '"+nick+"' WHERE id = " + event.getUser().getId());
            event.reply("Alright "+nick).setEphemeral(true).queue();
        }
    }
}
