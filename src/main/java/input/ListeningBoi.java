package input;

import commandStuff.CommandManager;
import databaseStuff.DCUser;
import mainStuff.Bot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static databaseStuff.DBManager.*;

public class ListeningBoi extends ListenerAdapter {
    int slayCounter = 0;
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        SlayingBoi slayboi = new SlayingBoi();
        if(slayboi.slay(message)){
            slayCounter+=1;
            Bot.updateStatus("Slay Counter: " + slayCounter);
        }
        if (message.getMentions().isMentioned(event.getJDA().getSelfUser())) {
            message.addReaction(Emoji.fromUnicode("U+1F440")).queue();
            String[] args = content.split(" ");
            if (args.length > 1) {
                CommandManager.manageCommand(args, event);
            }
        }
        if(message.toString().toLowerCase().replace('o','u').contains("uwu")){
            DCUser dcu = getUserByID(message.getAuthor().getId());
            if(dcu == null){
                addUser(new DCUser(message.getAuthor().getId(), message.getAuthor().getName(), "User", 0));
            }
            message.addReaction(Emoji.fromUnicode("U+1F4B0")).queue();
            message.addReaction(Emoji.fromUnicode("U+2795")).queue();
            message.addReaction(Emoji.fromFormatted("1️⃣")).queue();
            message.removeReaction(Emoji.fromUnicode("U+1F4B0"), event.getJDA().getSelfUser()).queueAfter(150, java.util.concurrent.TimeUnit.MILLISECONDS);
            message.removeReaction(Emoji.fromUnicode("U+2795"), event.getJDA().getSelfUser()).queueAfter(140, java.util.concurrent.TimeUnit.MILLISECONDS);
            message.removeReaction(Emoji.fromFormatted("1️⃣"), event.getJDA().getSelfUser()).queueAfter(130, java.util.concurrent.TimeUnit.MILLISECONDS);
            addCurrency(message.getAuthor().getId(), 1);
        }
    }
}
