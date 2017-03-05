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
	dbManager db;
	String Resposta;
	DescubraLol Instancia;
	int currentLevel;
	String playerId;
	IChannel Channel;
	String guildaId;
	
	public jDescubraLol(IMessage msg, DescubraLol i){
		Instancia = i;
		Channel = msg.getChannel();
		playerId = msg.getAuthor().getID();
		guildaId = msg.getGuild().getID();
		
		db = new dbManager();
		
		if(msg.getContent().split(" ").length > 1){
			if (msg.getContent().substring(4).equals("quit")){
				db.excluirInstancia(playerId, guildaId, "descubraLol_instancias");
				return;
			}
			else{
				MensageHandler.erroComandoInvalido(msg);
				return;
			}
		}
		
		if(!db.existeInstancia(playerId, guildaId, "descubraLol_instancias")){
			db.criarInstancia(msg.getAuthor().getID(), msg.getGuild().getID(), "descubraLol_instancias");
		}else{
			MensageHandler.enviarMensagem("Erro, jogo já existe!", msg);
			return;
		}
		
		if(!db.existeRegistro(playerId, "descubraLol_progresso", "id"))
			db.criarJogo(playerId, "descubraLol_progresso");
		currentLevel = db.loadGame(playerId, "descubraLol_progresso");
		
		if(currentLevel == 0){
			MensageHandler.enviarMsgEstilizada("Descubra o campeão", "Bem-vindo! Seu objetivo é adivinhar qual o personagem do League of Legends o bot quis representar a partir de emojis padrões do Discord.", Color.YELLOW, msg);
		}
		
		setResposta();
		mostrarLevel(msg.getAuthor());
	}

	private void mostrarLevel(IUser usuario) {
		String representacao = db.pesquisarString(currentLevel, "descubraLol_champions", "representacao");
		MensageHandler.enviarMsgEstilizada("@"+usuario.getName()+" CAMPEÃO "+ currentLevel, representacao, Color.DARK_GRAY, Channel);
	}
	
	private void checarPalpite(String Palpite, IUser usuario){
		if(Palpite.toLowerCase().equals(Resposta)){
			MensageHandler.enviarMsgEstilizada("@"+usuario.getName(), "Correto!", Color.GREEN, Channel);
			currentLevel++;
			salvarJogo();
			setResposta();
			mostrarLevel(usuario);
		}else{
			MensageHandler.enviarMsgEstilizada("Oops!", "Resposta errada.", Color.RED, Channel);
		}
	}
	
	@EventSubscriber
	public void onMessageEvent(MessageReceivedEvent event){
		IMessage msg = event.getMessage();
		
		if(msg.getContent().split(" ").length > 1){
			if (msg.getContent().substring(4).equals("quit")){
				db.excluirInstancia(playerId, guildaId, "descubraLol_instancias");
				return;
			}
			else{
				MensageHandler.erroComandoInvalido(msg);
				return;
			}
		}
		
		if(!(msg.getAuthor().getID().equals(playerId))){
			System.out.println("Não identificando como o player.");
			return;
		}
		if(msg.getAuthor().isBot())
			return;
		if(!db.existeInstancia(playerId, guildaId, "descubraLol_instancias")){
			System.out.println("Sugere que não existe jogo.");
			return;
		}
		
		
		String palpite = msg.getContent();
		checarPalpite(palpite, msg.getAuthor());
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
		db.setInt(playerId, "descubraLol_progresso", "currentLevel", currentLevel);
	}
	
	private void setResposta(){
		Resposta = db.pesquisarString(currentLevel, "descubraLol_champions", "nome");
	}
}