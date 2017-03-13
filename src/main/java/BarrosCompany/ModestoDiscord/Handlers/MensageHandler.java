package BarrosCompany.ModestoDiscord.Handlers;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import BarrosCompany.ModestoDiscord.ModestoBot;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
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
	public static void enviarMensagem(String resposta, IChannel ch){
		try {
			ch.sendMessage(resposta);
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
	
	public static IMessage enviarMsgEstilizada(String titulo, String corpo, Color cor, IChannel ch){
		EmbedObject eb = new EmbedBuilder().withColor(cor).withTitle(titulo).withDesc(corpo).build();
		try {
			return new MessageBuilder(ModestoBot.Bot).withChannel(ch).withEmbed(eb).build();
		} catch (RateLimitException e1) {
			e1.printStackTrace();
		} catch (DiscordException e1) {
			e1.printStackTrace();
		} catch (MissingPermissionsException e1) {
			e1.printStackTrace();
		}
//		RequestBuffer.request(() -> {
//			try{
//				System.out.println("antes do return");
//				return new MessageBuilder(ModestoBot.Bot).withChannel(ch).withEmbed(eb).build();
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			return null;
//		});
//		return null;
		return null;
	}
	public static IMessage enviarMsgEstilizada(String titulo, String corpo, Color cor, IChannel ch, int delay){
		EmbedObject eb = new EmbedBuilder().withColor(cor).withTitle(titulo).withDesc(corpo).build();
		IMessage m = null;
		RequestBuffer.request(() -> {
			try{
				IMessage x = new MessageBuilder(ModestoBot.Bot).withChannel(ch).withEmbed(eb).build();
				Thread.sleep(delay);
				x.delete();
				return x;
			}catch(Exception e){
				e.printStackTrace();
			}
			return m;
		});
		return m;
	}
	public static void erroJogoAcontecendo(IMessage msg){
		enviarMsgEstilizada("Erro!", "Você já está em um jogo. Se esse não for o caso, digite %[comandodojogo] quit (ex. %dc quit)!", Color.RED, msg);
	}
	
	public static void erroComandoInvalido(IMessage msg){
		enviarMsgEstilizada("Erro!", "Comando inválido!", Color.RED, msg);
	}
	
	public static void excluirMensagem(IMessage msg){
		try {
			msg.delete();
		} catch (MissingPermissionsException e) {
			e.printStackTrace();
		} catch (RateLimitException e) {
			e.printStackTrace();
		} catch (DiscordException e) {
			e.printStackTrace();
		}
	}
	
	
}
