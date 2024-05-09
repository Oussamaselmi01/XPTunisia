package Main;

import Entities.User.User;
import Services.UserService.UserService;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        User s =new User("88888888","Oussema","test","123456","oussema@gmail.com","Ariana, 12450, Tuinsia",26888999,"admin");
        User s1 =new User(2,"88888888","Oussema","UPDATED","666666","oussema@gmail.com","Ariana, 12450, Tunisia",26888999,"admin");
        UserService us = new UserService();
        
        //us.insert(s);
        //us.update(s1);
        //String resultat=us.readById(2).toString();;
        //System.out.println(resultat);
        
       ArrayList<User> userList = us.readAll();

       for(User i:userList)
       {
        System.out.println(i.toString());
       }
        
        
        
    }
    
}
