package input;

import commandStuff.CommandManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import mainStuff.Bot;

public class ListeningBoi extends ListenerAdapter {
    int slayCounter = 0;
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        SlayingBoi slayboi = new SlayingBoi();
        if(slayboi.slay(message)){
            slayCounter+=1;
            Bot.updateStatus("Slay Counter: " + Integer.toString(slayCounter));
        }
        if (message.getMentions().isMentioned(event.getJDA().getSelfUser())) {
            message.addReaction(Emoji.fromUnicode("U+1F440")).queue();
            String[] args = content.split("\s+");
            if (args.length > 1) {
                CommandManager.manageCommand(args, event);
            }
        }
    }
}
