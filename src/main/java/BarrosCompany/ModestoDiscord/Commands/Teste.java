package BarrosCompany.ModestoDiscord.Commands;
import BarrosCompany.ModestoDiscord.Handlers.MensageHandler;
import BarrosCompany.ModestoDiscord.Interfaces.IComando;
import sx.blah.discord.handle.obj.IMessage;

public class Teste implements IComando {

	public void Executar(IMessage msg) {
		MensageHandler.enviarMensagem("Teste concluido.", msg);
	}
	
}
