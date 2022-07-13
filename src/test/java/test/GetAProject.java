package test;

import accessToken.Token;
import api.ApiProject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.testng.annotations.Test;
import utils.configs.ConfigSettings;

import static org.testng.Assert.assertEquals;

public class GetAProject {
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

    public GetAProject() {
        this.token = new Token();
        configSettings = new ConfigSettings(System.getProperty("user.dir"));
    }

    // Test api get a project
    @Test(description = "Get a project")
    public void Test01_getAProject() {
        accessToken = token.getToken();
        apiProject = new ApiProject();

        String idProjectGet = idProjectCreated;

        JsonObject ObjectProjectGot = apiProject.get(accessToken, idProjectGet);
        String idProjectGot = ObjectProjectGot.get("id").getAsString();
        assertEquals(idProjectGet, idProjectGot);
    }
}
