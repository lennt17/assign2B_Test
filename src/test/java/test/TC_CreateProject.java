package test;

import accessToken.Token;
import api.APIBase;
import api.ApiProject;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TC_CreateProject {
    public ApiProject apiProject;
    public APIBase apiBase;
    Token token = new Token();
    public static String idProjectCreated;
    String idProjectCreate;
    String nameProjectCreated;
    String urlProjectCreated;

    @Test(description = "Create project successfully with valid token")
    public void Test01_createProject() {
        String accessToken = token.getToken();
        apiProject = new ApiProject();
        apiBase = new APIBase();

        String nameProject = "Shopping List";
        Map<String, Object> mapPost = new HashMap<>();
        mapPost.put("name", nameProject);
        mapPost.put("color", 45);
        mapPost.put("favorite", true);
//        mapPost.put("parent_id", 220330567);

        Response res = apiProject.createProject(accessToken, mapPost);

        JsonObject ObjectProjectCreated = apiBase.getJsonObject(res);
        int statusCode = apiBase.getStatusCode(res);
        idProjectCreated = ObjectProjectCreated.get("id").getAsString();
        nameProjectCreated = ObjectProjectCreated.get("name").getAsString();
        urlProjectCreated = ObjectProjectCreated.get("url").getAsString();

        // verify status code, body, response schema
        assertEquals(statusCode, 200);
        assertEquals(nameProject, nameProjectCreated);
        assertTrue(urlProjectCreated.contains(idProjectCreated));
    }

    @Test(description = "Create project with valid token and only require field")
    public void Test02_createProjectWithRequireField(){
        String accessToken = token.getToken();
        apiProject = new ApiProject();
        apiBase = new APIBase();

        Map<String, String> mapPost = new HashMap<>();
        String nameProject = "ki";
        mapPost.put("name", nameProject);

        Response res = apiProject.createProject(accessToken, mapPost);

        JsonObject ObjectProjectCreated = apiBase.getJsonObject(res);
        int statusCode = apiBase.getStatusCode(res);
        idProjectCreate = ObjectProjectCreated.get("id").getAsString();
        nameProjectCreated = ObjectProjectCreated.get("name").getAsString();
        urlProjectCreated = ObjectProjectCreated.get("url").getAsString();

        assertEquals(nameProjectCreated, nameProject);
        assertEquals(statusCode, 200);
        assertTrue(urlProjectCreated.contains(idProjectCreate));
    }

    @Test(description = "Create project without token")
    public void Test03_createProjectWithoutToken(){
        String accessToken = "";
        apiProject = new ApiProject();
        apiBase = new APIBase();

        Map<String, String> mapPost = new HashMap<>();
        String nameProject = "without token";
        mapPost.put("name", nameProject);

        Response res = apiProject.createProject(accessToken, mapPost);

        int statusCode = apiBase.getStatusCode(res);
        assertEquals(statusCode, 401);
    }

    @Test(description = "Create project with non-existing token")
    public void Test04_createProjectWithNonExistingToken(){
        String accessToken = "!@#";
        apiProject = new ApiProject();
        apiBase = new APIBase();

        Map<String, String> mapPost = new HashMap<>();
        String nameProject = "non-exist token";
        mapPost.put("name", nameProject);

        Response res = apiProject.createProject(accessToken, mapPost);

        int statusCode = apiBase.getStatusCode(res);
        assertEquals(statusCode, 401);
    }
}
