package BarrosCompany.ModestoDiscord.Handlers;

import java.awt.Color;

import BarrosCompany.ModestoDiscord.ModestoBot;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

public class MensageHandler {
	
	public static void enviarMensagem(String resposta, IMessage message){
		try {
			message.getChannel().sendMessage(resposta);
		} catch (MissingPermissionsException e) {
			e.printStackTrace();
		} catch (RateLimitException e) {
			e.printStackTrace();
		} catch (DiscordException e) {
			e.printStackTrace();
		}
	}
	
	public static IMessage enviarMsgEstilizada(String titulo, String corpo, Color cor, IMessage message){
		EmbedObject eb = new EmbedBuilder().withColor(cor).withTitle(titulo).withDesc(corpo).build();
		IMessage m = null;
		RequestBuffer.request(() -> {
			try{
				IMessage x = new MessageBuilder(ModestoBot.Bot).withChannel(message.getChannel()).withEmbed(eb).build();
				return x;
			}catch(Exception e){
				e.printStackTrace();
			}
			return m;
		});
		return m;
	}
	
	public static void erroJogoAcontecendo(IMessage msg){
		enviarMsgEstilizada("Erro!", "Um jogo já está acontecendo!", Color.RED, msg);
	}
	
	
}
