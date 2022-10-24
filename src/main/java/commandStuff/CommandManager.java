package commandStuff;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandManager {
    public static void manageCommand(String[] args, MessageReceivedEvent event){
        String command = args[1];
        command = command.toLowerCase();
        switch (command) {
            case "help" -> {
                event.getChannel().sendMessage("I'll do my best to help :)").queue();
            }
            case "amogus" -> {
                for(int i =0; i < 10; i++){
                    event.getChannel().sendMessage("Gusgus").queue();
                }
            }
            case "gayporn" -> {
                event.getChannel().sendMessage("──────▄▌▐▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\u200B▀▀▀▀▀▀▌\n" +
                                                    "───▄▄██▌█ beep beep ▄▄▄▌▐██▌█ gay porn delivery\n" +
                                                    "███████▌█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\u200B▄▄▄▄▄▄▌\n" +
                                                    "▀(@)▀▀▀▀▀▀▀(@)(@)▀▀▀▀▀▀▀▀▀▀▀▀▀\u200B▀▀▀▀(@)▀").queue();
            }
            default -> {
                event.getChannel().sendMessage("UwU. I am sorry, I dont know what you mean :sob:").queue();
            }
        }
    }
}
