package test;

import api.ApiProject;
import api.ApiTask;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DemoTest extends TestNGListener {
    private String nameProject = "Project 0607";
    private String nameTask = "Task 0607";
    private String nameProjectExpected = "Shopping List";
    String nameProjectUpdate = "Things To Buy";
    private HomePage homePage;
    private LoginPage loginPage;
    private TodayPage todayPage;
    private ProjectPage projectPage;
    private ConfigSettings configSettings;
    public ApiProject apiProject;
    public ApiTask apiTask;
    String idProjectCreated;
    String nameProjectCreated;
    String accessToken;
    Gson g = new Gson();

    public DemoTest() {
        super();
        this.token = new Token();
        configSettings = new ConfigSettings(System.getProperty("user.dir"));
    }

    @Test(description = "Create project")
    public void Test01_createProject() {
        accessToken = token.getToken();
        apiProject = new ApiProject();
        Map<String, Object> mapPost = new HashMap<>();
        mapPost.put("name", nameProjectExpected);

        // verify status code, body, response schema
        JsonObject ObjectProjectCreated = apiProject.create(accessToken, mapPost);
        idProjectCreated = ObjectProjectCreated.get("id").getAsString();
        nameProjectCreated = ObjectProjectCreated.get("name").getAsString();
        assertEquals(nameProjectExpected, nameProjectCreated);
    }

    @Test(description = "Get a project")
    public void Test02_getProject() {
        String idProjectGet = idProjectCreated;

        JsonObject ObjectProjectGot = apiProject.get(accessToken, idProjectGet);
        String idProjectGot = ObjectProjectGot.get("id").getAsString();
        assertEquals(idProjectGet, idProjectGot);
    }

    @Test(description = "Update project")
    public void Test03_updateProject() {
        String idProjectUpdate = idProjectCreated;
        Map<String, Object> mapPut = new HashMap<>();
        mapPut.put("name", nameProjectUpdate);

        JsonObject ObjectProjectUpdated = apiProject.update(accessToken, idProjectUpdate, mapPut);
        String nameProjectUpdated = ObjectProjectUpdated.get("name").getAsString();
        assertEquals(nameProjectUpdate, nameProjectUpdated);
    }

    @Test(description = "Get all project")
    public void Test04_getAllProjects() {
        JsonArray arr = apiProject.getAll(accessToken);
        System.out.println(arr);
    }

// assignment 2B 
    @Test(description = "Create project and task through API and then verify in WebUI")
    public void Test100_TestAPI(){
        // Init
        apiProject = new ApiProject();
        apiTask = new ApiTask();

        // Create project through API
        Map<String, Object> mapPostProject = new HashMap<>();
        mapPostProject.put("name", nameProject);
        JsonObject objectProjectCreated = apiProject.create(accessToken, mapPostProject) ;
        String idProjectCreated = objectProjectCreated.get("id").getAsString();

        // Create task through API
        Map<String, Object> mapPostTask = new HashMap<>();
        mapPostTask.put(project_id, idProjectCreated);
        mapPostTask.put(content, nameTask);
        mapPostTask.put(dueString, "tomorrow at 13:00");
        mapPostTask.put(dueLang, "en");
        mapPostTask.put(priority, 4);
        JsonObject objectTaskCreated = apiTask.create(accessToken, mapPostTask);
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
