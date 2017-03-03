package BarrosCompany.ModestoDiscord.Commands;

import BarrosCompany.ModestoDiscord.Handlers.MensageHandler;
import BarrosCompany.ModestoDiscord.Interfaces.IComando;
import sx.blah.discord.handle.obj.IMessage;

public class Adedonha implements IComando {

	public void Executar(IMessage msg) {
		MensageHandler.enviarMensagem("Adedonha.", msg);

	}

}
