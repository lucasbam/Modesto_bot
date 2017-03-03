package BarrosCompany.ModestoDiscord.Handlers;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

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
	
	
}
