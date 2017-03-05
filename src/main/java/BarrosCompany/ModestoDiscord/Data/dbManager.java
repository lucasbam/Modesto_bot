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
			con = DriverManager.getConnection("jdbc:mysql://mysql4.gear.host/botmodesto?autoReconnect=true&useSSL=false", "botmodesto", "Lw6Imk!w?yjS");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public String pesquisarString(int id, String tabela, String string){
		String query = "SELECT * FROM "+ tabela +" WHERE id = ?";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return rs.getString(string);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void setInt(int id, String tabela, String coluna, int i){
		String query = "UPDATE " + tabela + " SET " + coluna + "=" + i + " WHERE id = ?";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(query);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setInt(String id, String tabela, String coluna, int i){
		String query = "UPDATE " + tabela + " SET " + coluna + "=" + i + " WHERE id = ?";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, id);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void criarInstancia(String playerId, String guildaId, String tabela){
		String query = "INSERT INTO "+ tabela + "(id, guilda) VALUES(?,?)";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, playerId);
			stmt.setString(2, guildaId);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean existeInstancia(String playerId, String guildaId, String tabela){
		try {
			String query = "SELECT COUNT(*) AS total FROM " + tabela + " WHERE id = ? AND guilda = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, playerId);
			stmt.setString(2, guildaId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			System.out.println(rs.getInt("total"));
			if(rs.getInt("total") > 0){
				stmt.close();
				rs.close();
				return true;
			}else{
				stmt.close();
				rs.close();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean existeRegistro(String id, String tabela, String valor){
		try {
			String query = "SELECT COUNT(1) FROM " + tabela + " WHERE " + valor + " = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			if(rs.getInt(1) > 0){
				stmt.close();
				rs.close();
				return true;
			}else{
				stmt.close();
				rs.close();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void criarJogo(String playerId, String tabela) {
		String query = "INSERT INTO "+ tabela + "(id, currentLevel) VALUES(?,?)";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, playerId);
			stmt.setInt(2, 0);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int loadGame(String playerId, String tabela){
		System.out.println("Tá chegando no load!");
		String query = "SELECT * FROM "+ tabela +" WHERE id = ?";
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
	public void excluirInstancia(String playerId, String guildaId, String tabela) {
		String query = "DELETE FROM "+ tabela + " WHERE id = ? AND guilda = ?";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, playerId);
			stmt.setString(2, guildaId);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
