package com.dev.api;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dev.api.model.dao.MessageDAO;
import com.dev.api.model.entities.Chat;
import com.dev.api.model.entities.Message;

@Path("/message")
public class MessageResource {
       
       @POST
       @Consumes(MediaType.APPLICATION_JSON)
       @Produces(MediaType.APPLICATION_JSON)
       public Response postUser(Message m) {
           	MessageDAO mDAO = new MessageDAO();
	        m = mDAO.inserirMensagem(m);
	        return Response.status(200).entity(m).build();
       }
       
       @POST
       @Path("/messages")
       @Consumes(MediaType.APPLICATION_JSON)
       @Produces(MediaType.APPLICATION_JSON)
       public Response postUserChat(Chat chat) {
           	MessageDAO mDAO = new MessageDAO();
	        try {
	        	List<Message> messages = mDAO.buscarMensagens(chat.getId());	
	        	return Response.status(200).entity(messages).build();
	        } catch (SQLException e) {			
				e.printStackTrace();
			}
	        return Response.status(404).build();
       }
       

}