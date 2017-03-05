package BarrosCompany.ModestoDiscord.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dbManager {
	Connection con;
	
	public dbManager() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://db4free.net/botmodesto?autoReconnect=true&useSSL=false", "lukketz", "fodase123");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean existeProgresso(String playerId){
		try {
			String query = "SELECT COUNT(1) FROM descubraLol_progresso WHERE playerId = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, playerId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			if(rs.getInt(1) > 0){
				stmt.close();
				return true;
			}else{
				stmt.close();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void criarJogo(String playerId) {
		String query = "INSERT INTO descubraLol_progresso(playerId, currentLevel) VALUES(?,?)";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, playerId);
			stmt.setInt(2, 0);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int loadGame(String playerId){
		System.out.println("Tá chegando no load!");
		String query = "SELECT * FROM descubraLol_progresso WHERE playerId = ?";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, playerId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return rs.getInt("currentLevel");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

}
