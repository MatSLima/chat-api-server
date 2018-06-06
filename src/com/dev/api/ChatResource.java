package com.dev.api;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dev.api.model.dao.ChatDAO;
import com.dev.api.model.dao.MessageDAO;
import com.dev.api.model.dao.UserChatDAO;
import com.dev.api.model.dao.UserDAO;
import com.dev.api.model.entities.Chat;
import com.dev.api.model.entities.Message;
import com.dev.api.model.entities.User;

@Path("/chat")
public class ChatResource {
       
       @POST
       @Consumes(MediaType.APPLICATION_JSON)
       @Produces(MediaType.APPLICATION_JSON)
       public Response getChats(User u) {
           	ChatDAO cDAO = new ChatDAO();
	        try {
				List<Chat> chats = cDAO.buscarChatsByUser(u);				
				return Response.status(200).entity(chats).build();
			} catch (SQLException e) {			
				e.printStackTrace();
			}
	        return Response.status(404).build();
       }
       
       @POST
       @Consumes(MediaType.APPLICATION_JSON)
       @Path("/new")
       @Produces(MediaType.APPLICATION_JSON)
       public Response postChat(Chat c) {
    	   	ChatDAO cDAO = new ChatDAO();
    	   	UserChatDAO uDAO = new UserChatDAO();
    	   	
	        c = cDAO.inserirChat(c);
	        
	        for(User u : c.getUsers()) {
	        	uDAO.inserir(u, c); 
	        }
	        return Response.status(200).entity(c).build();
       }
       

}
