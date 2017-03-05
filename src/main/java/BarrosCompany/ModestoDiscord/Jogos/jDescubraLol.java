package BarrosCompany.ModestoDiscord.Jogos;

import BarrosCompany.ModestoDiscord.Commands.DescubraLol;
import BarrosCompany.ModestoDiscord.Data.dbManager;
import BarrosCompany.ModestoDiscord.Handlers.MensageHandler;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

public class jDescubraLol {
	DescubraLol Instancia;
	int currentLevel;
	String playerId;
	IChannel Channel;
	String idJogo;
	
	public jDescubraLol(IMessage msg, DescubraLol i){
		Instancia = i;
		Channel = msg.getChannel();
		playerId = msg.getAuthor().getID();
		idJogo = playerId + "," + msg.getGuild().getID();
		
		dbManager db = new dbManager();
		if(!db.existeProgresso(playerId))
			db.criarJogo(playerId);
		currentLevel = db.loadGame(playerId);
		
		MensageHandler.enviarMensagem("Jogo ok!", msg);
	}
}