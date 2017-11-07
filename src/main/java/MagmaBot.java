import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MagmaBot implements IListener<Event> {
    private IDiscordClient client;
    private Map<IUser, ArrayList<String>> todo;

    MagmaBot(IDiscordClient client) {
        this.client = client;
        todo = new HashMap<>();
    }

    public void handle(Event event) {
        if (event instanceof MessageReceivedEvent) {
            MessageReceivedEvent messageReceivedEvent = ((MessageReceivedEvent) event);
            IChannel channel = messageReceivedEvent.getChannel();
            IMessage message = messageReceivedEvent.getMessage();
            String text = message.getContent().trim();
            if (text.length() > 0 && text.charAt(0) != '!') return;
            if (text.equals("!help")) {
                channel.sendMessage(message.getAuthor().mention() + ", type `!todo` to view your todolist, and `!todoadd [item]` to add an item to your todolist!");
            } else if (text.equals("!embed")) {
                EmbedBuilder embed = new EmbedBuilder().withColor(Color.RED);
                embed.appendField("Can i", "newline\nin embed?", true);
                channel.sendMessage(embed.build());
            } else if (text.equals("!todo")) {
                EmbedBuilder embed = new EmbedBuilder().withColor(message.getAuthor().getColorForGuild(message.getGuild()));
                StringBuilder list = new StringBuilder();
                if (todo.get(message.getAuthor()) == null) {
                    list.append("is empty!");
                } else for (int i = 0; i < todo.get(message.getAuthor()).size(); i++) {
                    list.append('>').append(todo.get(message.getAuthor()).get(i)).append('\n');
                }
                embed.appendField(message.getAuthor().getName() + "'s todolist", list.toString(), true);
                channel.sendMessage(embed.build());
            } else if (text.startsWith("!todoadd ")) {
                text = text.substring(text.indexOf(' ') + 1);
                todo.computeIfAbsent(message.getAuthor(), k -> new ArrayList<>());
                todo.get(message.getAuthor()).add(text);
            }
        } else if (event instanceof ReadyEvent) {
            client.changePlayingText("!help for help");
        } else if (event instanceof UserJoinEvent) {
            UserJoinEvent userJoinEvent = ((UserJoinEvent) event);
            IChannel channel = client.getChannelByID(255138575065153536L);
            channel.sendMessage(userJoinEvent.getUser().mention() + ", thanks for joining the server! Message Jeffmagma if you want a color change");
        }
    }
}
