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
                for (int i = 0; i < 10; i++) {
                    event.getChannel().sendMessage("Gusgus").queue();
                }
            }
            case "gayporn" -> {
                event.getChannel().sendMessage("""
                        ""
                                            ──────▄▌▐▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\\u200B▀▀▀▀▀▀▌
                                            ───▄▄██▌█ beep beep ▄▄▄▌▐██▌█ gay porn delivery
                                            ███████▌█▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\\u200B▄▄▄▄▄▄▌
                                            ▀(@)▀▀▀▀▀▀▀(@)(@)▀▀▀▀▀▀▀▀▀▀▀▀▀\\u200B▀▀▀▀(@)▀"\"""").queue();
            }
            default -> {
                event.getChannel().sendMessage("UwU. I am sowwy, I dont know what you mean :sob:").queue();
            }
        }
    }
}
