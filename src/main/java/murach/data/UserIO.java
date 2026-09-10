package murach.data;

import murach.business.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class UserIO{
	public static void add (User user, String path) {
		try (BufferedWriter out=new BufferedWriter(new FileWriter(path, true))){
			out.write(user.getEmail());
			out.write("|");
			out.write(user.getFirstName());
			out.write("|");
			out.write(user.getLastName());
			out.newLine();
		}
		catch(IOException e) {
			System.out.println("Error wrong writing data:" + e.getMessage());
		}
	}
	public static User getUser(String email, String path) {
		try (BufferedReader in=new BufferedReader(new FileReader(path))){
			String line;
			while((line= in.readLine()) !=null) {
				String[] fields = line.split("\\|");
				if (fields.length >= 3 && fields[0].equalsIgnoreCase(email)) {
                    User user = new User();
                    user.setEmail(fields[0]);
                    user.setFirstName(fields[1]);
                    user.setLastName(fields[2]);
                    return user;
                }
			}
			
		}
		catch(IOException e) {
			System.out.println("ERROR reading user data: "+ e.getMessage());
		}
		return null;
	}
}