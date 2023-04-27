package commandStuff;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class CommandManager {

    public static void updateCommands(JDA bot){
        for(Guild guild : bot.getGuilds()){
            guild.updateCommands().addCommands(
                    Commands.slash("ping", "Pong"),
                    Commands.slash("help", "I'll do my best to help :)"),
                    Commands.slash("gayporn", "It's a secret"),
                    Commands.slash("callme", "Tell me how to talk to you"),
                    Commands.slash("vctest", "Test the planned vc feature")
                            .addOption(OptionType.CHANNEL, "vc", "Which VC do you want me to join?"),
                    Commands.slash("owocheck", "Check how many OwOs a User has.")
                            .addOption(OptionType.USER, "user", "Which user do you want to check?"),
                    Commands.slash("addcustomwaifu", "Adds a fully custom waifu")
                            .addOption(OptionType.STRING, "name", "What is the name of the waifu?")
                            .addOption(OptionType.STRING, "description", "What is the description of the waifu?")
                            .addOption(OptionType.INTEGER, "rarity", "What is the rarity of the waifu?")
                            .addOption(OptionType.INTEGER, "price", "How many OwOs for this waifu?")
                            .addOption(OptionType.STRING, "image", "What is link to the image of the waifu?"),
                    Commands.slash("showwaifu", "Shows a specific waifu")
                            .addOption(OptionType.STRING, "name", "What is the name of the waifu?"),
                    Commands.slash("addrole", "Adds a role with bot access level")
                            .addOption(OptionType.ROLE, "role", "Whats the role?")
                            .addOption(OptionType.STRING , "accessgroup", "What is the access group? (To see all the access groups, use the command 'showaccessgroups')"),
                    Commands.slash("showaccessgroups", "Shows all the access groups"),
                    Commands.slash("removewaifu", "Removes a waifu")
                            .addOption(OptionType.STRING, "name", "What is the name of the waifu?")
            ).queue();
            System.out.println("Commands updated for " + guild.getName());
        }
    }

}
