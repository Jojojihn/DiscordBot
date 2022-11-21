package vcStuff;

import net.dv8tion.jda.api.JDA;

public class GoodGirl {
    static JDA bot;
    public static void connectToVC(String vcName, String guildName) {
        System.out.println("Connecting to " + vcName + " in " + guildName);
        bot.getGuilds().forEach(guild -> {
            if (guild.getName().equals(guildName)) {
                guild.getVoiceChannels().forEach(voiceChannel -> {
                    if (voiceChannel.getId().equals(vcName)) {
                        voiceChannel.getGuild().getAudioManager().openAudioConnection(voiceChannel);
                    }
                });
            }
        });
    }
}
