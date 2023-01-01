package codingmentor.javabackend.k3.test;

import codingmentor.javabackend.k3.DAO.LoginDAO;
import codingmentor.javabackend.k3.DAO.LoginDAOImpl;

public class Main {
	public static void main(String[] args) {
		LoginDAO user = new LoginDAO("test@1234", "1234");
		LoginDAOImpl login = new LoginDAOImpl();
		try {
			System.out.println(login.validate(user));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
