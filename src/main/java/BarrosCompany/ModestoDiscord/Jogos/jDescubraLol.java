package BarrosCompany.ModestoDiscord.Jogos;

import BarrosCompany.ModestoDiscord.Commands.DescubraLol;
import BarrosCompany.ModestoDiscord.Handlers.MensageHandler;
import sx.blah.discord.handle.obj.IMessage;

public class jDescubraLol {
	DescubraLol Instancia;
	String idJogo;
	public jDescubraLol(IMessage msg, DescubraLol i){
		Instancia = i;
		idJogo = msg.getAuthor().getID() + "," + msg.getGuild().getID();
		
		MensageHandler.enviarMensagem("Jogo ok!", msg);
	}
}
