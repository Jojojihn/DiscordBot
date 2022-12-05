package input;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.Objects;

import static databaseStuff.DBManager.setNickname;

public class ButtonBoi extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("daddy")) {
            setNickname(event.getUser().getId(), "Daddy");
            event.reply("Alright Daddy").setEphemeral(true).queue();
        }else if (event.getComponentId().equals("mommy")) {
            setNickname(event.getUser().getId(), "Mommy");
            event.reply("Alright Mommy").setEphemeral(true).queue();
        }else if (event.getComponentId().equals("master")) {
            setNickname(event.getUser().getId(), "Master");
            event.reply("Alright Master").setEphemeral(true).queue();
        }else if (event.getComponentId().equals("baby")) {
            setNickname(event.getUser().getId(), "Baby");
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
            setNickname(event.getUser().getId(), nick);
            event.reply("Alright "+nick).setEphemeral(true).queue();
        }
    }
}
