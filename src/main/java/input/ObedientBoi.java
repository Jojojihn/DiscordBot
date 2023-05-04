package input;

import databaseStuff.DBRole;
import databaseStuff.DBWaif;
import databaseStuff.DCUser;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import java.util.Objects;

import static databaseStuff.DBManager.*;
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
                String nick = Objects.requireNonNull(getUserByID(event.getUser().getId())).nickname;
                event.reply("I'll do my best to help " + nick + " :)").queue();
            }
        } else if (event.getName().equals("gayporn")) {
            event.getChannel().sendMessage("""
                    ──────▄▌▐▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\u200B▀▀▀▀▀▀▌
                    ───▄▄██▌█ beep beep ▄▄▄▌▐██▌█ gay porn delivery
                    ███████▌█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\u200B▄▄▄▄▄▄▌
                    ▀(@)▀▀▀▀▀▀▀(@)(@)▀▀▀▀▀▀▀▀▀▀▀▀▀\u200B▀▀▀▀(@)▀""").queue();
        } else if (event.getName().equals("callme")) {
            DCUser dcUser = getUserByID(event.getUser().getId());
            if(dcUser == null) {
                dcUser = new DCUser(event.getUser().getId(), Objects.requireNonNull(event.getMember()).getEffectiveName(), "User", 0);
                addUser(dcUser);
            }
            String nickname = dcUser.nickname;

            event.reply("What do you want me to call you, " + nickname + "?")
                    .setEphemeral(true)
                    .addActionRow(
                            Button.secondary("daddy", "Daddy"),
                            Button.secondary("mommy", "Mommy"),
                            Button.secondary("master", "Master"),
                            Button.secondary("baby", "Baby"),
                            Button.secondary("custom", "Custom"))
                    .queue();
        } else if (event.getName().equals("vctest")) {
            connectToVC(Objects.requireNonNull(event.getOption("vc")).getAsString(), Objects.requireNonNull(event.getGuild()).getName());
            System.out.println(Objects.requireNonNull(event.getOption("vc")).getAsString());
        } else if (event.getName().equals("owocheck")) {
            if (event.getOption("user") == null) {
                event.reply("Give me a user to check!").setEphemeral(true).queue();
            } else {
                DCUser dcu = getUserByID(Objects.requireNonNull(event.getOption("user")).getAsUser().getId());
                if (dcu == null) {
                    event.reply("This User does not have any OwO's").setEphemeral(true).queue();
                } else {
                    String nick = dcu.nickname;
                    int currency = dcu.currency;
                    event.reply(nick + " has " + currency + " OwO's").queue();
                }
            }
        }else if (event.getName().equals("addcustomwaifu")){
            boolean allowed = false;
            for(Role r: Objects.requireNonNull(event.getMember()).getRoles()) {
                String id = r.getId();
                DBRole dbRole = getRoleByID(id);
                if (dbRole != null) {
                    if (dbRole.access == 1 || dbRole.access == 4) {
                        allowed = true;
                    }
                }
            }
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
            if (event.getOption("name") == null){
                event.reply("Please provide a name!").setEphemeral(true).queue();
            }else{
                String name = Objects.requireNonNull(event.getOption("name")).getAsString();
                name = name.toLowerCase().strip();
                DBWaif waifu = getWaifuByName(name);
                if (waifu == null){
                    event.reply("This waifu does not exist!").setEphemeral(true).queue();
                }else{
                    event.reply(waifu.name + " is a " + waifu.rarity + " star waifu with the description: " + waifu.description + " and the image: " + waifu.image).queue();
                }
            }
        }else if (event.getName().equals("showaccessgroups")){
            event.reply("The access groups are: \n -Waifu (Manages the waifu game) \n -Moderator (Manages the moderation commands) \n -Admin (Manages the admin commands) \n -Owner (Manages the owner commands)").queue();
        }else if (event.getName().equals("addrole")){
            if (event.getOption("role") == null || event.getOption("accessgroup") == null){
                event.reply("Please provide all the required arguments!").setEphemeral(true).queue();
            }else{
                String role = Objects.requireNonNull(event.getOption("role")).getAsString();
                String accessgroup = Objects.requireNonNull(event.getOption("accessgroup")).getAsString();
                accessgroup = accessgroup.toLowerCase().strip();
                switch (accessgroup){
                    case "waifu" -> addRole(new DBRole(role,1));
                    case "moderator" -> addRole(new DBRole(role, 2));
                    case "admin" -> addRole(new DBRole(role, 3));
                    case "owner" -> addRole(new DBRole(role, 4));
                    default -> event.reply("This access group does not exist!").setEphemeral(true).queue();
                }
                event.reply("Added " + role + " to the database!").queue();
            }
        }else if(event.getName().equals("removewaifu")){
            if(event.getOption("name") == null){
                event.reply("Please provide a name!").setEphemeral(true).queue();
            }else{
                String name = Objects.requireNonNull(event.getOption("name")).getAsString();
                name = name.toLowerCase().strip();
                DBWaif waifu = getWaifuByName(name);
                if (waifu == null){
                    event.reply("This waifu does not exist!").setEphemeral(true).queue();
                }else{
                    removeWaifu(waifu.name);
                    event.reply("Removed " + waifu.name + " from the database!").queue();
                }
            }
        }
    }
}

