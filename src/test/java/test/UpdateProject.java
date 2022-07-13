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

public class UpdateProject {
    private String nameProject = "Project 0607";
    private String nameTask = "Task 0607";
    private String nameProjectExpected = "Shopping List";
    private String nameProjectUpdate = "Things To Buy";
    private ConfigSettings configSettings;
    public ApiProject apiProject;
    public Token token;
    String idProjectCreated;
    String nameProjectCreated;
    String accessToken;
    Gson g = new Gson();

    public UpdateProject() {
        this.token = new Token();
        configSettings = new ConfigSettings(System.getProperty("user.dir"));
    }

    // Test api update project
    @Test(description = "Update project")
    public void Test01_updateProject() {
        accessToken = token.getToken();
        apiProject = new ApiProject();

        String idProjectUpdate = idProjectCreated;
        Map<String, Object> mapPut = new HashMap<>();
        mapPut.put("name", nameProjectUpdate);

        JsonObject ObjectProjectUpdated = apiProject.update(accessToken, idProjectUpdate, mapPut);
        String nameProjectUpdated = ObjectProjectUpdated.get("name").getAsString();
        assertEquals(nameProjectUpdate, nameProjectUpdated);
    }
}
