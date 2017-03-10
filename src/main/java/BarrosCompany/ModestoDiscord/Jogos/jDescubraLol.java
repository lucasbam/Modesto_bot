package BarrosCompany.ModestoDiscord.Jogos;

import java.awt.Color;

import BarrosCompany.ModestoDiscord.Commands.DescubraLol;
import BarrosCompany.ModestoDiscord.Data.dbManager;
import BarrosCompany.ModestoDiscord.Handlers.MensageHandler;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class jDescubraLol {
//	dbManager db;
	String Resposta;
	DescubraLol Instancia;
	int currentLevel;
	boolean prepared = false;
	String playerId;
	IChannel Channel;
	String guildaId;
	
	public jDescubraLol(IMessage msg, DescubraLol i){
		Instancia = i;
		Channel = msg.getChannel();
		playerId = msg.getAuthor().getID();
		guildaId = msg.getGuild().getID();

		
		if(!dbManager.existeRegistro(playerId, "descubraLol_progresso", "id"))
			dbManager.criarJogo(playerId, "descubraLol_progresso");
		
		currentLevel = dbManager.loadGame(playerId, "descubraLol_progresso");
		dbManager.criarInstancia(msg.getAuthor().getID(), msg.getGuild().getID(), "descubraLol_instancias");
		
		if(currentLevel == 0){
			MensageHandler.enviarMsgEstilizada("Descubra o campeão", "Bem-vindo! Seu objetivo é adivinhar qual o personagem do League of Legends o bot quis representar a partir de emojis padrões do Discord.", Color.YELLOW, msg);
		}
		
		iniciarJogo(msg.getAuthor());
	}
	
	private void iniciarJogo(IUser user){
		mostrarLevel(user);
	}

	private void mostrarLevel(IUser usuario) {
		Resposta = dbManager.pesquisarString(currentLevel, "descubraLol_champions", "nome");
		String representacao = dbManager.pesquisarString(currentLevel, "descubraLol_champions", "representacao");
		MensageHandler.enviarMsgEstilizada("@"+usuario.getName()+" CAMPEÃO "+ currentLevel, representacao, Color.DARK_GRAY, Channel);
		prepared = true;
	}
	
	private void checarPalpite(String Palpite, IUser usuario){
		prepared = false;
		if(Palpite.toLowerCase().equals(Resposta)){
			MensageHandler.enviarMsgEstilizada("@"+usuario.getName(), "Correto!", Color.GREEN, Channel);
			passarNivel();
			mostrarLevel(usuario);
		}else{
			MensageHandler.enviarMsgEstilizada("Oops!", "Resposta errada.", Color.RED, Channel);
			prepared = true;
		}
	}
	
	private void passarNivel(){
		currentLevel++;
		salvarJogo();
	}
	
	@EventSubscriber
	public void onMessageEvent(MessageReceivedEvent event){
		IMessage msg = event.getMessage();
		if(msg.getContent().isEmpty())
			return;
		if(msg.getContent().startsWith("%dc"))
			return;
		if(!(msg.getAuthor().getID().equals(playerId))){
			System.out.println("Não identificando como o player.");
			return;
		}
		if(msg.getAuthor().isBot())
			return;
		if(!dbManager.existeInstancia(playerId, guildaId, "descubraLol_instancias")){
			System.out.println("Sugere que não existe jogo.");
			return;
		}
		if(!prepared)
			return;
		
		String palpite = msg.getContent();
		IUser autor = msg.getAuthor();
		checarPalpite(palpite, autor);
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
	
	public void salvarJogo(){
		dbManager.setInt(playerId, "descubraLol_progresso", "currentLevel", currentLevel);
	}
}