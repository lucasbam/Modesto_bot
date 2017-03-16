package BarrosCompany.ModestoDiscord.Jogos;

import java.awt.Color;
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
	String Resposta; 
	String playerId, guildaId;
	boolean prepared = false, jogando = true;
	DescubraLol Instancia;
	IChannel Channel;
	int currentLevel;
	int tentativasTotais, tentativasLocais = 0, tentativasDaSessao = 0, championIniciado;
	IMessage ultimoChampion;
	
	public jDescubraLol(IMessage msg, DescubraLol i){
		Instancia = i;
		Channel = msg.getChannel();
		playerId = msg.getAuthor().getID();
		guildaId = msg.getGuild().getID();
		tentativasTotais = dbManager.loadInt(playerId, "descubraLol_progresso", "tentativas");
		currentLevel = dbManager.loadInt(playerId, "descubraLol_progresso", "currentLevel");
		championIniciado = currentLevel;
		
		if(!dbManager.existeRegistro(playerId, "descubraLol_progresso", "id"))
			dbManager.criarJogo(playerId, "descubraLol_progresso");
		
		dbManager.criarInstancia(playerId, guildaId, "descubraLol_instancias");
		
		if(currentLevel == 0)
			MensageHandler.enviarMsgEstilizada("Descubra o campeão", "Bem-vindo! Seu objetivo é adivinhar qual o personagem do League of Legends o bot quis representar a partir de emojis padrões do Discord.", Color.YELLOW, msg);
		
		double maxChampionId = dbManager.getMaxValue("descubraLol_champions", "id");
		if (currentLevel > maxChampionId){
			ModestoBot.Bot.getDispatcher().unregisterListener(this);
			double x = (maxChampionId/tentativasTotais);
			String rendimento = String.format("%.2f", x*100.0);
			MensageHandler.enviarMsgEstilizada("@"+msg.getAuthor().getName(), 
			"Você já completou o jogo. Aguarde mais atualizações! \n " + 
			"Seu rendimento foi de " + rendimento +"%", 
			Color.WHITE, Channel);
			Instancia.quitarJogo(playerId, guildaId);
			return;
		}
		
		mostrarNivel(msg.getAuthor());
	}

	private void mostrarNivel(IUser usuario) {
		if(ultimoChampion != null)
			MensageHandler.excluirMensagem(ultimoChampion);
		if (currentLevel == dbManager.getMaxValue("descubraLol_champions", "id")){
			MensageHandler.enviarMsgEstilizada("Parabéns @"+usuario.getName()+"!", "Você completou o jogo! Aguarde mais atualizações!", Color.WHITE, Channel);
			Instancia.quitarJogo(playerId, guildaId);
			return;
		}
		if(jogando){
			Resposta = dbManager.pesquisarString(currentLevel, "descubraLol_champions", "nome");
			String representacao = dbManager.pesquisarString(currentLevel, "descubraLol_champions", "representacao");
			ultimoChampion = MensageHandler.enviarMsgEstilizada("@"+usuario.getName()+" CAMPEÃO "+ currentLevel, representacao, Color.DARK_GRAY, Channel);
			prepared = true;
		}
	}
	
	private void checarPalpite(String Palpite, IUser usuario){
		prepared = false;
		System.out.println(tentativasLocais);
		if(Palpite.toLowerCase().equals(Resposta)){
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
			    	MensageHandler.enviarMsgEstilizada("@"+usuario.getName(), "Correto!", Color.GREEN, Channel, 2000);
			    }
			});
			t.start();
			passarNivel();
			mostrarNivel(usuario);
		}else{
			Thread t = new Thread(new Runnable() {
			    @Override
				public void run() {
			    	MensageHandler.enviarMsgEstilizada("Oops!", "Resposta errada.", Color.RED, Channel, 4000);
			    }
			});
			t.start();
			if(tentativasLocais == 5)
				MensageHandler.enviarMsgEstilizada("Dica", dbManager.pesquisarString(currentLevel, "descubraLol_champions", "dica"), Color.BLUE, Channel);
			prepared = true;
		}
		
		tentativasLocais++;
		tentativasDaSessao++;
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
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							double x = currentLevel-championIniciado;
							double y = x/tentativasDaSessao;
							String rendimento = String.format("%.2f", y*100);
					    	MensageHandler.enviarMsgEstilizada("Encerrado com sucesso.", "Seu rendimento 'champion/tentativa' foi de " + rendimento + "%", Color.WHITE, Channel, 4000);
					    }
					});
					if(tentativasDaSessao > 0)
						t.start();
					else{
						MensageHandler.enviarMensagem("Jogo encerrado com sucesso!", Channel);
					}
					ModestoBot.Bot.getDispatcher().unregisterListener(this);
					return;
			}
			else{
				MensageHandler.erroComandoInvalido(msg);
				return;
				}
			}
			return;
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
		dbManager.setInteger(playerId, "descubraLol_progresso", "currentLevel", currentLevel);
		tentativasTotais = tentativasTotais+tentativasLocais;
		dbManager.setInteger(playerId, "descubraLol_progresso", "tentativas", tentativasTotais);
		tentativasLocais = 0;
	}

	public boolean isJogando() {
		return jogando;
	}

	public void setJogando(boolean jogando) {
		this.jogando = jogando;
	}

}