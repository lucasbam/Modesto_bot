package BarrosCompany.ModestoDiscord.Commands;

import BarrosCompany.ModestoDiscord.Handlers.MensageHandler;
import BarrosCompany.ModestoDiscord.Interfaces.IComando;
import BarrosCompany.ModestoDiscord.Jogos.jDescubraLol;
import sx.blah.discord.handle.obj.IMessage;

public class DescubraLol implements IComando {
	
	String playerId;
	jDescubraLol instancia = null;
	
	public void Executar(IMessage msg) {		
		if(instancia == null)
			instancia = new jDescubraLol(msg, this);
		else
			MensageHandler.enviarMensagem("Ja existe um jogo", msg);
	}
	
	public void setInstanciaNull(){
		instancia = null;
	}

}
