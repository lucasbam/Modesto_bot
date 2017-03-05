package BarrosCompany.ModestoDiscord.Interfaces;

import java.util.ArrayList;

import BarrosCompany.ModestoDiscord.Handlers.MensageHandler;
import sx.blah.discord.handle.obj.IChannel;

public interface IJogo {
	ArrayList<String> listaDeJogos = new ArrayList<String>();

	public default void quitarJogo(String idJogo, IChannel ch){
		for (int i = 0; i < listaDeJogos.size(); i++) {
			if(listaDeJogos.get(i).equals(idJogo)){
				listaDeJogos.remove(i);
				MensageHandler.enviarMensagem("Jogo encerrado com sucesso.", ch);
			}
		}
	}
}
