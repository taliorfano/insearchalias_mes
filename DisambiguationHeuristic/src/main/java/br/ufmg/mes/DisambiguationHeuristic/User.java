package br.ufmg.mes.DisambiguationHeuristic;
import java.util.Date;

/***
 * 
 * @author Talita
 *
 */
public class User {
	private String name;
	private String email;

	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean equals(Object o) {	
	    User user = (User) o;
	    return this.name.equals(user.name) || this.email.equals(user.email);
	}
	
	public int hashCode() {
		return email.length()*name.length();
	}
}
