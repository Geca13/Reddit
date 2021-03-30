package com.example.reddit.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {
	
	private String subject;
	
	private String recepient;
	
	private String body;

}
