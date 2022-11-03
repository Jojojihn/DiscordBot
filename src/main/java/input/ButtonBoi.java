package input;

import databaseStuff.DBManager;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class ButtonBoi extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("daddy")) {
            try {
                DBManager dbManager = new DBManager();
                dbManager.connect();
                dbManager.sendSQL("UPDATE users SET nickname = 'Daddy' WHERE id = " + event.getUser().getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            event.getMessage().delete().queue();
        }
    }
}
