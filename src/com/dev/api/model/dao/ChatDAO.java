package com.dev.api.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.dev.api.model.entities.Chat;
import com.dev.api.model.entities.Message;
import com.dev.api.model.entities.User;
import com.mysql.jdbc.Statement;


public class ChatDAO {

    public Chat buscarChat(int idChat) throws SQLException {
        String sql = null;
        PreparedStatement stm = null;

        sql = "SELECT * FROM message WHERE id_chat = " + idChat;
        stm = ConnectionFactory.getConnection().prepareStatement(sql);

        ResultSet rs = stm.executeQuery();
        Chat chat = new Chat();
        rs.next();
        chat.setId(rs.getInt("id_chat"));
        chat.setCreatedAt(new Date());        

        return chat;
    }

    public ArrayList<Chat> buscarChatsByUser(User user) throws SQLException {

        String sql = null;
        PreparedStatement stm = null;

        sql = "SELECT * FROM chat INNER JOIN user_chat as uc ON chat.id = uc.id_chat WHERE uc.id_user = ?";
        stm = ConnectionFactory.getConnection().prepareStatement(sql);
        stm.setInt(1, user.getId());
        
        ResultSet rs = stm.executeQuery();
        ArrayList<Chat> chats = new ArrayList<Chat>();
        while(rs.next()){
        	Chat c = new Chat();
        	c.setId(rs.getInt("id_chat"));
        	c.setName(rs.getString("nome"));
            c.setCreatedAt(new Date());  
            
            chats.add(c);
        }

        return chats;

    }
    
    public Chat inserirChat(Chat c) {
		String sql = null;
        PreparedStatement stm = null;

        sql = "INSERT INTO chat (nome, created_at) VALUES (?, now())";
        try {
			stm = ConnectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, c.getName());
			stm.executeUpdate();	
			
			ResultSet rs = stm.getGeneratedKeys();
			rs.next();
			c.setId(rs.getInt(1));
		} catch (SQLException e) { 
			e.printStackTrace();
		}        
        
        return c;
	}

}

