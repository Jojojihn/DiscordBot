package Games.WaifuCollection;

import databaseStuff.DBWaif;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.Objects;
import static databaseStuff.DBManager.*;

public class MainWaifu extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("addcustomwaifu")){
            boolean allowed = checkRoleAccess(event.getMember().getRoles(), 1);
            if(!allowed) {
                event.reply("You do not have permission to do this!").queue();
            }else {
                if (event.getOption("name") == null || event.getOption("description") == null || event.getOption("rarity") == null || event.getOption("price") == null || event.getOption("image") == null) {
                    event.reply("Please provide all the required arguments!").setEphemeral(true).queue();
                } else {
                    String name = Objects.requireNonNull(event.getOption("name")).getAsString();
                    String description = Objects.requireNonNull(event.getOption("description")).getAsString();
                    int rarity = Objects.requireNonNull(event.getOption("rarity")).getAsInt();
                    int price = Objects.requireNonNull(event.getOption("price")).getAsInt();
                    String image = Objects.requireNonNull(event.getOption("image")).getAsString();
                    addWaifu(new DBWaif(name, description, rarity, price, image));
                    event.reply("Added " + name + " to the database!").queue();
                }
            }
        }else if (event.getName().equals("showwaifu")){
            if(checkRoleAccess(event.getMember().getRoles(),1)){
                if (event.getOption("name") == null) {
                    event.reply("Please provide a name!").setEphemeral(true).queue();
                } else {
                    String name = Objects.requireNonNull(event.getOption("name")).getAsString();
                    name = name.toLowerCase().strip();
                    DBWaif waifu = getWaifuByName(name);
                    if (waifu == null) {
                        event.reply("This waifu does not exist!").setEphemeral(true).queue();
                    } else {
                        event.reply(waifu.name + " is a " + waifu.rarity + " star waifu with the description: " + waifu.description + " and the image: " + waifu.image).queue();
                    }
                }
            }else{
                event.reply("You do not have permission to do this!").queue();
            }
        }else if(event.getName().equals("mywaifus")){
            event.reply("//TODO").queue();
            //TODO: Add my waifus
        }
    }

}
