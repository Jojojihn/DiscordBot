package input;

import net.dv8tion.jda.api.entities.Message;

public class SlayingBoi {
    public boolean slay(Message message) {
        if (message.getContentRaw().toLowerCase().matches("s+l+a+y+")) {
            return true;
        }
        return false;
    }
}
