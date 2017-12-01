package com.codecool.jlamas.handlers;

import com.codecool.jlamas.controllers.*;
import com.codecool.jlamas.database.*;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.artifact.Artifact;
import com.codecool.jlamas.models.level.Level;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class StudentHandler extends AbstractHandler implements HttpHandler {

    private static final String MAIN = "templates/main.twig";
    private static final String NAV_MENU = "classpath:/templates/student/nav_menu.twig";
    private static final String PROFILE = "classpath:/templates/student/student.twig";
    private static final String WALLET = "classpath:/templates/student/wallet.twig";
    private static final String STORE = "classpath:/templates/student/store.twig";
    private static final String CHOOSE_STUDENT = "classpath:/templates/student/chooseStudentsForPurchase.twig";
    private static final String TEAM = "classpath:/templates/student/teamPurchase.twig";
    private static final String CHANGE_PASSWORD = "classpath:/templates/change_password.twig";
    private static final String LOGOUT = "/student/logout";

    private static final Integer ARTIFACT_INDEX = 4;
    private static final Integer TEAM_INDEX = 5;

    private WalletController walletController;
    private LevelDAO levelDAO;
    private Map<String, Callable> getCommands = new HashMap<>();
    private Map<String, Callable> postCommands = new HashMap<>();
    private Student student;
    private ArtifactDAO artifactDAO = new ArtifactDAO();
    private SessionDAO session = new SessionDAO();
    private CookieController cookieController = new CookieController();
    private Response responseCode = new Response();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response;
        String method = httpExchange.getRequestMethod();
        HttpCookie cookie = cookieController.getCookie(httpExchange);

        if (cookie != null) {
            String userType = new UserDAO().getType(session.getUserByCookie(httpExchange).getLogin().getValue());
            if (userType.equals("student")) {
                this.student = (Student) session.getUserByCookie(httpExchange);
                walletController = new WalletController(student);

                if (student != null) {

                    if (method.equals("GET")) {
                        if (httpExchange.getRequestURI().getPath().toString().equals(LOGOUT)) {
                            this.logout(httpExchange);

                        } else {
                            response = findCommand(httpExchange, getCommands);
                            responseCode.sendOKResponse(response, httpExchange);
                        }
                    } if (method.equals("POST")) {
                        response = findCommand(httpExchange, postCommands);
                        responseCode.sendOKResponse(response, httpExchange);
                    }
                }
            } else {
                responseCode.sendRedirectResponse(httpExchange, "/");
            }
        } else {
            responseCode.sendRedirectResponse(httpExchange, "/");
        }
    }

    protected void addGetCommands(HttpExchange httpExchange) {

        getCommands.put("/student", () -> { return displayProfile();} );
        getCommands.put("/student/wallet", () -> { return displayWallet();} );
        getCommands.put("/student/store/buy/.+", () -> { return buyArtifact(httpExchange);}  );
        getCommands.put("/student/store", () -> { return displayStore();} );
        getCommands.put("/student/team_purchases", () -> { return displayTeamPurchase("");} );
        getCommands.put("/student/team_purchases/add/[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ ]/.+", () -> { return addTeamPurchase(httpExchange);} );
        getCommands.put("/student/password/edit/.+", () -> {return displayEditPassword("");} );
    }

    protected void addPostCommands(HttpExchange httpExchange) {
        postCommands.put("/student/team_purchases/accept/.+", () -> { return acceptTeamPurchase(httpExchange);} );
        postCommands.put("/student/team_purchases/cancel/.+", () -> { return cancelTeamPurchase(httpExchange);} );
        postCommands.put("/student/password/edit/.+", () -> { return editPassword(httpExchange); });
    }

    protected JtwigModel getContent(String content_path) {
        JtwigModel model = JtwigModel.newModel();
        walletController = new WalletController(student);
        levelDAO = new LevelDAO();
        Integer totalEarnings = walletController.getDoneQuestsValue();
        Level level = levelDAO.getStudentLevel(totalEarnings);


        model.with("nav_path", NAV_MENU);
        model.with("logout_path", LOGOUT);
        model.with("content_path", content_path);
        model.with("login", student.getLogin().getValue());

        return model;
    }

    protected String displayProfile() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(MAIN);
        JtwigModel model = getContent(PROFILE);
        model.with("student", student);
        model.with("level", level);

        return template.render(model);
    }

    private String displayWallet() {

        JtwigTemplate template = JtwigTemplate.classpathTemplate(MAIN);
        JtwigModel model = getContent(WALLET);
        model.with("student", student);
        model.with("artifacts", new OwnedArtifactDAO().requestAllBy(student));

        return template.render(model);
    }

    private String displayStore() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(MAIN);
        JtwigModel model = getContent(STORE);

        model.with("message", null);
        model.with("student", student);
        model.with("artifacts", new ArtifactDAO().requestAll());

        return template.render(model);
    }

    private String displayBoughtArtifact(String message) {

        JtwigTemplate template = JtwigTemplate.classpathTemplate(MAIN);
        JtwigModel model = getContent(STORE);

        model.with("message", message);
        model.with("student", student);
        model.with("artifacts", new ArtifactDAO().requestAll());

        return template.render(model);
    }

    private String buyArtifact(HttpExchange httpExchange) {

        String artifactName = parseStringFromURL(httpExchange, ARTIFACT_INDEX);
        String message;

        if (walletController.buyArtifact(artifactDAO.selectArtifact(artifactName))) {
            message = "Artifact bought";
        } else {
            message = "Artifact can't be bought";
        }

        return displayBoughtArtifact(message);
    }

    private String addTeamPurchase(HttpExchange httpExchange) throws IOException {

        String artifactName = parseStringFromURL(httpExchange, ARTIFACT_INDEX);
        String team = parseStringFromURL(httpExchange, TEAM_INDEX);
        ArrayList<Student> students = new StudentDAO().getStudentsFromTeam(new TeamDAO().get(team));
        String message = "Pending purchase opened";

        new TeamPurchaseController().addTeamPurchase(students, new ArtifactDAO().selectArtifact(artifactName));

        return displayTeamPurchase(message);
    }

    private String acceptTeamPurchase(HttpExchange httpExchange) {

        Integer id = parseIntFromURL(httpExchange, ARTIFACT_INDEX);
        String message;

        if (new TeamPurchaseController().acceptPurchaseRequest(student, id)) {
            message = "Request accepted";
        } else {
            message = "Not enough money!";
        }
        return displayTeamPurchase(message);
    }

    private String cancelTeamPurchase(HttpExchange httpExchange) {

        Integer id = parseIntFromURL(httpExchange, ARTIFACT_INDEX);
        new TeamPurchaseController().cancelTeamPurchase(id);
        String message = "Request canceled for all students";

        return displayTeamPurchase(message);
    }

    private String displayTeamPurchase(String message) {

        JtwigTemplate template = JtwigTemplate.classpathTemplate(MAIN);
        JtwigModel model = getContent(TEAM);

        model.with("message", message);
        model.with("student", student);

        return template.render(model);
    }

    protected String displayEditPassword(String message) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(MAIN);
        JtwigModel model = getContent(CHANGE_PASSWORD);

        model.with("msg", message);

        return template.render(model);
    }
}