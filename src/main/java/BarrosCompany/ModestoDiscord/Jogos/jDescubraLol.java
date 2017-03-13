package BarrosCompany.ModestoDiscord.Jogos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import BarrosCompany.ModestoDiscord.ModestoBot;
import BarrosCompany.ModestoDiscord.Commands.DescubraLol;
import BarrosCompany.ModestoDiscord.Data.dbManager;
import BarrosCompany.ModestoDiscord.Handlers.MensageHandler;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class jDescubraLol {
//	dbManager db;
	String Resposta, playerId, guildaId;
	boolean prepared = false, jogando = true;
	DescubraLol Instancia;
	IChannel Channel;
	int currentLevel;
	//ArrayList<IMessage> mensagens = new ArrayList<IMessage>();
	IMessage ultimoChampion;
	
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
		
		mostrarNivel(msg.getAuthor());
	}

	private void mostrarNivel(IUser usuario) {
		if(ultimoChampion != null)
			MensageHandler.excluirMensagem(ultimoChampion);
		if(jogando){
			Resposta = dbManager.pesquisarString(currentLevel, "descubraLol_champions", "nome");
			String representacao = dbManager.pesquisarString(currentLevel, "descubraLol_champions", "representacao");
			ultimoChampion = MensageHandler.enviarMsgEstilizada("@"+usuario.getName()+" CAMPEÃO "+ currentLevel, representacao, Color.DARK_GRAY, Channel);
			System.out.println("ultimochampion:"+ultimoChampion);
			prepared = true;
		}
	}
	
	private void checarPalpite(String Palpite, IUser usuario){
		prepared = false;
		if(Palpite.toLowerCase().equals(Resposta)){
			MensageHandler.enviarMsgEstilizada("@"+usuario.getName(), "Correto!", Color.GREEN, Channel, 2000);
			passarNivel();
			mostrarNivel(usuario);
		}else{
			MensageHandler.enviarMsgEstilizada("Oops!", "Resposta errada.", Color.RED, Channel, 3000);
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
		
		if(msg.getContent().toLowerCase().startsWith("%dc")){
			if(msg.getContent().split(" ").length > 1){
				if (msg.getContent().substring(4).equals("quit")){
					Instancia.quitarJogo(playerId, guildaId);
					ModestoBot.Bot.getDispatcher().unregisterListener(this);
					return;
			}
			else{
				MensageHandler.erroComandoInvalido(msg);
				return;
				}
			}
		}
		if(!jogando)
			return;
		if(!prepared)
			return;
		
		String palpite = msg.getContent();
		IUser autor = msg.getAuthor();
		checarPalpite(palpite, autor);
		MensageHandler.excluirMensagem(msg);
	}
	///
	public void salvarJogo(){
		dbManager.setInt(playerId, "descubraLol_progresso", "currentLevel", currentLevel);
	}

	public boolean isJogando() {
		return jogando;
	}

	public void setJogando(boolean jogando) {
		this.jogando = jogando;
	}

}