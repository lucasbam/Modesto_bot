package BarrosCompany.ModestoDiscord.Interfaces;

import java.util.ArrayList;

import sx.blah.discord.handle.obj.IMessage;

public interface IJogo {
	ArrayList<String> listaDeJogos = new ArrayList<String>();
	public void quitarJogo(String id, IMessage m);
}
