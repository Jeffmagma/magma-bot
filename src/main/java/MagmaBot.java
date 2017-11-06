import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.MessageBuilder;

public class MagmaBot implements IListener<MessageReceivedEvent> {
    private IDiscordClient client;

    MagmaBot(IDiscordClient client) {
        this.client = client;
        EventDispatcher dispatcher = client.getDispatcher(); // Gets the client's event dispatcher
        dispatcher.registerListener(this); // Registers this bot as an event listener
        client.changePlayingText("!help for help");
    }

    private void sendMessage(String message, MessageReceivedEvent event) {
        new MessageBuilder(this.client).withChannel(event.getChannel()).withContent(message).send();
    }


    public void handle(MessageReceivedEvent event) {
        IMessage message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("!help"))
            sendMessage(message.getAuthor().mention() + " asked for help", event);
    }
}
