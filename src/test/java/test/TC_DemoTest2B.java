package test;

import api.APIBase;
import api.ApiProject;
import api.ApiTask;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import listener.TestNGListener;
import org.testng.annotations.Test;

import pages.ProjectPage;
import pages.HomePage;
import pages.LoginPage;
import pages.TodayPage;
import utils.configs.ConfigSettings;
import accessToken.Token;

import java.util.HashMap;
import java.util.Map;

import static constant.Constant.*;
import static org.testng.Assert.assertTrue;

public class TC_DemoTest2B extends TestNGListener {
    private String nameProject = "Project 0607";
    private String nameTask = "Task 0607";
    private HomePage homePage;
    private LoginPage loginPage;
    private TodayPage todayPage;
    private ProjectPage projectPage;
    private ConfigSettings configSettings;
    private APIBase apiBase;
    public ApiProject apiProject;
    public ApiTask apiTask;
    Token token = new Token();

    public TC_DemoTest2B() {
        super();
        configSettings = new ConfigSettings(System.getProperty("user.dir"));
    }

    @Test(description = "Create project and task through API and then verify in WebUI")
    public void Test100_TestAPI(){
        apiBase = new APIBase();
        apiProject = new ApiProject();
        apiTask = new ApiTask();
        token = new Token();
        String accessToken = token.getToken();

        // Create project through API
        Map<String, Object> mapPostProject = new HashMap<>();
        mapPostProject.put("name", nameProject);

        Response res = apiProject.createProject(accessToken, mapPostProject);

        JsonObject objectProjectCreated = apiBase.getJsonObject(res);
        String idProjectCreate = objectProjectCreated.get("id").getAsString();

        // Create task through API
        Map<String, Object> mapPostTask = new HashMap<>();
        mapPostTask.put(project_id, idProjectCreate);
        mapPostTask.put(content, nameTask);
        mapPostTask.put(dueString, "tomorrow at 13:00");
        mapPostTask.put(dueLang, "en");
        mapPostTask.put(priority, 4);

        Response response = apiTask.createTask(accessToken, mapPostTask);
        JsonObject objectTaskCreated = apiBase.getJsonObject(response);
        String idTask = objectTaskCreated.get("id").getAsString();

        // Verify value project and task in Web UI
        homePage = new HomePage(action);
        loginPage = homePage.clickLogin();
        String email = configSettings.getEmail();
        String password = configSettings.getPassword();
        todayPage = loginPage.loginAccount(email, password);
        projectPage = todayPage.handleMenu.clickProject(nameProject);
        assertTrue(projectPage.shouldToBeHaveTask(nameTask));

        // Click checkbox task in order to not display task in WebUI and then verify
        projectPage.clickCheckboxTask(nameTask);
        assertTrue(projectPage.shouldToBeNotDisplayTask(nameTask));

        // ReOpen task through API and then verify task is reopened in WebUI (is displayed again)
        apiTask.reOpenTask(accessToken, idTask);
        action.refresh();
        assertTrue(projectPage.shouldToBeHaveTask(nameTask));
    }
}
