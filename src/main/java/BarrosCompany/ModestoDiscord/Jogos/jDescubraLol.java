package BarrosCompany.ModestoDiscord.Jogos;

import BarrosCompany.ModestoDiscord.Commands.DescubraLol;
import BarrosCompany.ModestoDiscord.Handlers.MensageHandler;
import sx.blah.discord.handle.obj.IMessage;

public class jDescubraLol {
DescubraLol Instancia;
	
	public jDescubraLol(IMessage msg, DescubraLol f){
		Instancia = f;
		MensageHandler.enviarMensagem("Jogo ok!", msg);
	}
}
