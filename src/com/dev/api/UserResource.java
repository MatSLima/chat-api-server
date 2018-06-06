package com.dev.api;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dev.api.model.dao.UserChatDAO;
import com.dev.api.model.dao.UserDAO;
import com.dev.api.model.entities.Chat;
import com.dev.api.model.entities.User;
import com.dev.api.model.entities.UserChat;
 
 
@Path("/user")
public class UserResource {
 
       @GET
       @Produces(MediaType.APPLICATION_JSON)
       public User getUser(@QueryParam("id") int id) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
             User user = new User();
             user.setLogin("testedaapi");
             user.setSenha("teste");
             return user;
       }
       
       @POST
       @Consumes(MediaType.APPLICATION_JSON)
       @Produces(MediaType.APPLICATION_JSON)
       public Response postUser(User u) {
           	UserDAO uDAO = new UserDAO();
	        try {
				u = uDAO.buscaLogin(u);			
			} catch (SQLException e) {			
				e.printStackTrace();
			}
	        return Response.status(200).entity(u).build();
       }
       
       @POST
       @Path("/new")
       @Consumes(MediaType.APPLICATION_JSON)
       @Produces(MediaType.APPLICATION_JSON)
       public Response newUser(User u) {
           	UserDAO uDAO = new UserDAO();	 
           	try {
				User uVerify = uDAO.buscaByLogin(u.getLogin());
				if (uVerify == null) {
					u = uDAO.inserir(u);					
			        return Response.status(200).entity(u).build();
				}
				u.setId(0);
				return Response.status(200).entity(u).build();
				
           	} catch (SQLException e) {
				e.printStackTrace();
			}
           						
	        return Response.status(200).entity(u).build();
       }
       
       @POST
       @Path("/chat")
       @Consumes(MediaType.APPLICATION_JSON)
       @Produces(MediaType.APPLICATION_JSON)
       public Response postUserChat(UserChat uC) {
           	UserDAO uDAO = new UserDAO();
	        try {
	        	uC = uDAO.buscaUserChat(uC);			
			} catch (SQLException e) {			
				e.printStackTrace();
			}
	        return Response.status(200).entity(uC).build();
       }
       
       @POST
       @Path("/chat/new")
       @Consumes(MediaType.APPLICATION_JSON)
       @Produces(MediaType.APPLICATION_JSON)
       public Response addUser(User u) {
           	UserDAO uDAO = new UserDAO();
	        try {
	        	List<Chat> chats = u.getChats();
	        	u = uDAO.buscaByLogin(u.getLogin());
	        	if (u == null) {
	        		u = new User();
	        		u.setId(0);
	        	} else {
	        		UserChatDAO ucDAO = new UserChatDAO();
	        		for(Chat c : chats) {
	        			ucDAO.inserir(u, c);
	        		}
	        	}	        		        	
			} catch (SQLException e) {			
				e.printStackTrace();
			}
	        return Response.status(200).entity(u).build();
       }
       

}
