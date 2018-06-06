package com.dev.api.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.dev.api.model.entities.Message;

public class MessageDAO {

    public ArrayList<Message> buscarMensagens(int idChat) throws SQLException {
        String sql = null;
        PreparedStatement stm = null;

        sql = "SELECT * FROM message INNER JOIN user_chat as uc ON message.id_user_chat = uc.id WHERE uc.id_chat = " + idChat + " ORDER BY message.id";
        stm = ConnectionFactory.getConnection().prepareStatement(sql);

        ResultSet rs = stm.executeQuery();
        ArrayList<Message> mensagens = new ArrayList<Message>();
        while(rs.next()){
            Message m = new Message();
            m.setId(rs.getInt("id"));
            m.setMessage(rs.getString("message"));
            m.setUserChat(new UserChatDAO().buscarUserByMessage(m.getId()));
            mensagens.add(m);
        }

        return mensagens;

    }

	public Message inserirMensagem(Message m) {
		String sql = null;
        PreparedStatement stm = null;

        sql = "INSERT INTO message (id_user_chat, message, created_at) VALUES (?, ?, now())";
        try {
			stm = ConnectionFactory.getConnection().prepareStatement(sql);
			stm.setInt(1, m.getUserChat().getId());
			stm.setString(2, m.getMessage());
			stm.executeUpdate();			
		} catch (SQLException e) { 
			e.printStackTrace();
		}        
        
        return m;
	}
}
