package BarrosCompany.ModestoDiscord.Jogos;

import BarrosCompany.ModestoDiscord.Commands.DescubraLol;
import BarrosCompany.ModestoDiscord.Handlers.MensageHandler;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

public class jDescubraLol {
	DescubraLol Instancia;
	IChannel Channel;
	String idJogo;
	
	public jDescubraLol(IMessage msg, DescubraLol i){
		Instancia = i;
		Channel = msg.getChannel();
		idJogo = msg.getAuthor().getID() + "," + msg.getGuild().getID();
		
		MensageHandler.enviarMensagem("Jogo ok!", msg);
	}
}
