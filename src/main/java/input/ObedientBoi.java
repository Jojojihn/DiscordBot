package input;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class ObedientBoi extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("ping")) {
            event.reply("Pong!").queue();
        }else if(event.getName().equals("help")) {
            event.reply("I'll do my best to help :)").queue();
        }else if(event.getName().equals("gaypyorn")){
            event.getChannel().sendMessage("──────▄▌▐▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\u200B▀▀▀▀▀▀▌\n" +
                    "───▄▄██▌█ beep beep ▄▄▄▌▐██▌█ gay porn delivery\n" +
                    "███████▌█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\u200B▄▄▄▄▄▄▌\n" +
                    "▀(@)▀▀▀▀▀▀▀(@)(@)▀▀▀▀▀▀▀▀▀▀▀▀▀\u200B▀▀▀▀(@)▀").queue();
        }else if(event.getName().equals("bot")){
            TextInput subject = TextInput.create("subject", "What do you want me to do?", TextInputStyle.SHORT)
                    .setPlaceholder("I'll be a good bot, I'll do anything for you master")
                    .setMinLength(10)
                    .setMaxLength(100) // or setRequiredRange(10, 100)
                    .build();

            Modal modal = Modal.create("modmail", "Modmail")
                    .addActionRows(ActionRow.of(subject))
                    .build();

            event.replyModal(modal).queue();
        }
    }
}
