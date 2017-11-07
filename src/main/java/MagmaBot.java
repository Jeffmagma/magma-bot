import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageBuilder;

public class MagmaBot implements IListener<Event> {
    private IDiscordClient client;

    MagmaBot(IDiscordClient client) {
        this.client = client;
    }

    private void sendMessage(String message, MessageReceivedEvent event) {
        new MessageBuilder(this.client).withChannel(event.getChannel()).withContent(message).build();
    }

    public void handle(Event event) {
        if (event instanceof MessageReceivedEvent) {
            MessageReceivedEvent messageReceivedEvent = ((MessageReceivedEvent)event);
            IMessage message = messageReceivedEvent.getMessage();
            if (message.getContent().equalsIgnoreCase("!help"))
                sendMessage(message.getAuthor().mention() + " asked for help", messageReceivedEvent);
        } else if (event instanceof ReadyEvent) {
            client.changePlayingText("!help for help");
        }
    }
}
