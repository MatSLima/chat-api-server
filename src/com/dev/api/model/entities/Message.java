package com.dev.api.model.entities;

public class Message {
    private int id;
    private UserChat userChat;
    private String message;

    public Message() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public UserChat getUserChat() {
		return userChat;
	}

	public void setUserChat(UserChat userChat) {
		this.userChat = userChat;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

