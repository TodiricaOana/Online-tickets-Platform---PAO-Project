package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import exceptions.ClientNotFoundException;
import exceptions.ExistingClientException;
import exceptions.WrongPasswordException;
import model.Child;
import model.Client;
import model.Retired;
import model.Student;
import service.ClientService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("unchecked")
@WebServlet("/client")
public class AuthenticationController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int Case = Integer.parseInt(req.getParameter("case"));
        String email = req.getParameter("email");
        String password;

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = null;

        switch (Case) {
            case 1:
                password = req.getParameter("password");
                String type = req.getParameter("type");
                String firstName = req.getParameter("firstName");
                String lastName = req.getParameter("lastName");

                Client client = null;

                if (type.equals("Adult"))
                    client = new Client(firstName, lastName, email, password);

                if (type.equals("Child"))
                    client = new Child(firstName, lastName, email, password);

                if (type.equals("Retired"))
                    client = new Retired(firstName, lastName, email, password);

                if (type.equals("Student"))
                    client = new Student(firstName, lastName, email, password);

                try {
                    assert client != null;
                    ClientService.getInstance().register(client);
                    json = ow.withRootName("success").writeValueAsString("You have been successfully registered!");
                } catch (ExistingClientException e) {
                    json = ow.withRootName("failure").writeValueAsString(e.getMessage());
                    e.printStackTrace();
                }
                break;

            case 2:
                password = req.getParameter("password");
                try {
                    ClientService.getInstance().login(email, password);
                    json = ow.withRootName("success").writeValueAsString("success");
                } catch (ClientNotFoundException | WrongPasswordException e) {
                    json = ow.withRootName("failure").writeValueAsString(e.getMessage());
                    e.printStackTrace();
                }
                break;

            case 3:
                String oldPassword = req.getParameter("oldPassword");
                String newPassword = req.getParameter("newPassword");
                try {
                    ClientService.getInstance().updatePassword(email, oldPassword, newPassword);
                    json = ow.withRootName("success").writeValueAsString("Your password was updated!");
                } catch (WrongPasswordException e) {
                    json = ow.withRootName("failure").writeValueAsString(e.getMessage());
                    e.printStackTrace();
                }
                break;
        }

        resp.getWriter().write(json);
    }
}
