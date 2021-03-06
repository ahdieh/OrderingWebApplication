package controller;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UserBean;
import model.User;

/**
 * Servlet implementation class ChangeFirstNameServlet
 */
@WebServlet({ "/ChangeFirstNameServlet", "/ChangeFirstName" })
public class ChangeFirstNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserBean userBean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangeFirstNameServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		userBean = (UserBean) request.getSession().getAttribute("userBean");
		String newName = request.getParameter("name");

		if (userBean != null && newName.length() > 0) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("FinalProject");
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			User user = em.find(User.class, userBean.getUserId());
			user.setName(newName);
			em.getTransaction().commit();
			userBean.setName(newName);
			request.getSession().setAttribute("userBean", userBean);
			em.close();
			emf.close();

			response.setContentType("application/json");
			JsonObject json = Json.createObjectBuilder().add("newName", newName).build();
			response.getWriter().write(json.toString());
		}
	}
}
