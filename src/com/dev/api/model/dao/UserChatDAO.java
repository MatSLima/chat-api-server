package com.dev.api.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dev.api.model.entities.Chat;
import com.dev.api.model.entities.Message;
import com.dev.api.model.entities.User;
import com.dev.api.model.entities.UserChat;

public class UserChatDAO {
	
	public UserChat buscarUserByMessage(int idMessage) throws SQLException {

        String sql = null;
        PreparedStatement stm = null;

        sql = "SELECT * FROM user_chat INNER JOIN message ON user_chat.id = message.id_user_chat WHERE message.id = " + idMessage;
        stm = ConnectionFactory.getConnection().prepareStatement(sql);
        
        ResultSet rs = stm.executeQuery();
        rs.next();
        UserDAO uDAO = new UserDAO();
        UserChat uChat = new UserChat();
        uChat.setId(rs.getInt("id"));
        uChat.setUser(uDAO.buscarUser(rs.getInt("id_user")));
        
        return uChat;

    }
	
	public User inserir(User u, Chat c) {
		String sql = null;
        PreparedStatement stm = null;

        sql = "INSERT INTO user_chat (id_chat, id_user, created_at) VALUES (?, ?, now())";
        try {
			stm = ConnectionFactory.getConnection().prepareStatement(sql);
			stm.setInt(1, c.getId());
			stm.setInt(2, u.getId());
			stm.executeUpdate();			
		} catch (SQLException e) { 
			e.printStackTrace();
		}        
        
        return u;
	}
}
