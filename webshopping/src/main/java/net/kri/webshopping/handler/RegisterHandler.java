package net.kri.webshopping.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;

import org.springframework.stereotype.Component;

import net.kri.webprojectbackend.dao.UserDAO;
import net.kri.webprojectbackend.dto.Address;
import net.kri.webprojectbackend.dto.Cart;
import net.kri.webprojectbackend.dto.User;
import net.kri.webshopping.model.RegisterModel;

@Component
public class RegisterHandler {
	
	
	@Autowired
	private UserDAO userDAO;

 
	
    public RegisterModel init() {
	 
 
      return new RegisterModel();
    }

  public void addUser(RegisterModel registerModel, User user) {
	  registerModel.setUser(user);
	 } 
  
  public void addBilling(RegisterModel registerModel, Address billing) {
	  registerModel.setBilling(billing);
	 }
  
  public String validateUser(User user, MessageContext error) {
	  
	  String transitionValue = "success";
	  
	  if((!user.getPassword().equals(user.getConfirmPassword())))  {
		  
		  error.addMessage(new MessageBuilder()
				  .error()
				  .source("confirmPassword")
				  .defaultText("Password does not match the confirm password!")
				  .build());
		  
		  transitionValue= "failure";
	  }
	  
	  if(userDAO.getByEmail(user.getEmail())!=null) {
		    error.addMessage(new MessageBuilder().error().source(
		      "email").defaultText("Email address is already taken!").build());
		    transitionValue = "failure";
		   }
	     
	   return transitionValue;
	   }
	 
	 
  
  
  public String saveAll(RegisterModel model) {
	  String transitionValue = "success";
	  
	  
	  User user = model.getUser();
	  
	  if(user.getRole().equals("USER")) {
	   // create a new cart
	   Cart cart = new Cart();
	   cart.setUser(user);
	   user.setCart(cart);
	  }
	  
	 
	  
	  userDAO.addUser(user);
	  
	  Address billing = model.getBilling();
	  billing.setUserId(user.getId());
	  billing.setBilling(true);
	  
	  userDAO.addAddress(billing);
	  
	  
	  return transitionValue;
  }

 } 
