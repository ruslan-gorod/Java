package servlets;

import dao.UserDb;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import models.User;

@WebServlet(value = "/servlets.editServlet")
public class editServlet extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        User user = UserDb.selectOne(login, "login");
        req.setAttribute("login",user.getLogin());
        req.setAttribute("password",user.getPassword());
        req.setAttribute("name",user.getName());
        req.setAttribute("id",user.getId());
        req.setAttribute("role",user.getRole());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/edit.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        User user = UserDb.selectOne(id,"id");
        String login = req.getParameter("login");
        String password =  req.getParameter("password");
        String name =  req.getParameter("nameUser");
        String role = req.getParameter("roleUser");
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setRole(role);
        UserDb.update(user);
        List<User> users = UserDb.select();
        req.setAttribute("users", users);
        req.setAttribute("role",user.getRole());
        getServletContext().getRequestDispatcher("/main.jsp").forward(req, resp);
    }
}