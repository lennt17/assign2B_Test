package test;

import accessToken.Token;
import api.ApiProject;
import api.ApiTask;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import listener.TestNGListener;
import org.testng.annotations.Test;
import utils.configs.ConfigSettings;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class GetAllProject {
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

    public GetAllProject() {
        this.token = new Token();
        configSettings = new ConfigSettings(System.getProperty("user.dir"));
    }

    // Test api create project
    @Test(description = "Get all project")
    public void Test01_getAllProject() {
        accessToken = token.getToken();
        apiProject = new ApiProject();

        JsonArray arr = apiProject.getAll(accessToken);
        System.out.println(arr);
    }
}
