package test;

import accessToken.Token;
import api.ApiProject;
import api.ApiTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import listener.TestNGListener;
import org.testng.annotations.Test;
import utils.configs.ConfigSettings;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class CreateProject {
    private String nameProject = "Project 0607";
    private String nameTask = "Task 0607";
    private String nameProjectExpected = "Shopping List";
    private ConfigSettings configSettings;
    public ApiProject apiProject;
    public Token token;
    String idProjectCreated;
    String nameProjectCreated;
    String accessToken;
    Gson g = new Gson();

    public CreateProject() {
        this.token = new Token();
        configSettings = new ConfigSettings(System.getProperty("user.dir"));
    }

    // Test api create project
    @Test(description = "Create project successfully with valid token")
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
}
