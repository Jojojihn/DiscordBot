package input;

import net.dv8tion.jda.api.entities.Message;

public class SlayingBoi {
    public boolean slay(Message message) {
        if (message.getContentRaw().toLowerCase().startsWith("s")) {
            char[] chars = message.getContentRaw().toLowerCase().toCharArray();
            int i = 0;
            boolean breaker = false;
            while (chars[i] == 's' && !breaker) {
                i += 1;
                if (chars.length == i) {
                    breaker = true;
                    i -= 1;
                }
                if (chars[i] == 'l') {
                    while (chars[i] == 'l' && !breaker) {
                        i += 1;
                        if (chars.length == i) {
                            breaker = true;
                            i -= 1;
                        }
                        if (chars[i] == 'a') {
                            while (chars[i] == 'a' && !breaker) {
                                i += 1;
                                if (chars.length == i) {
                                    breaker = true;
                                    i -= 1;
                                }
                                if (chars[i] == 'y') {
                                    while (chars[i] == 'y' && !breaker) {
                                        i += 1;
                                        if (chars.length == i) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
