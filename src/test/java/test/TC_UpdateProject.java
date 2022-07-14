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

public class TC_UpdateProject {
    public final String nameProjectUpdate = "Things To Buy";
    public TC_CreateProject createProject;
    public ApiProject apiProject;
    public APIBase apiBase;
    Token token = new Token();

    // Test api update project
    @Test(description = "Update project successfully with valid token and valid optional field")
    public void Test01_updateProject() {
        String accessToken = token.getToken();
        System.out.println("accessToken: " + accessToken);
        apiProject = new ApiProject();
        apiBase = new APIBase();

        String idProjectUpdate = createProject.idProjectCreated;
        Map<String, Object> mapPut = new HashMap<>();
        mapPut.put("name", nameProjectUpdate);

        Response res = apiProject.updateProject(accessToken, idProjectUpdate, mapPut);
        int statusCode = apiBase.getStatusCode(res);
        JsonObject ObjectProjectUpdated = apiBase.getJsonObject(apiProject.getAProject(accessToken, idProjectUpdate));
        String nameProjectUpdated = ObjectProjectUpdated.get("name").getAsString();

        assertEquals(statusCode, 204);
        assertEquals(nameProjectUpdate, nameProjectUpdated);
    }

    @Test(description = "Update project with field undefine")
    public void Test04_updateProjectWithFieldUndefine() {
        String accessToken = token.getToken();
        System.out.println("accessToken: " + accessToken);
        apiProject = new ApiProject();
        apiBase = new APIBase();

        String idProjectUpdate = createProject.idProjectCreated;
        Map<String, Object> mapPut = new HashMap<>();
        mapPut.put("name", nameProjectUpdate);
        mapPut.put("constant", 34);

        Response res = apiProject.updateProject(accessToken, idProjectUpdate, mapPut);
        int statusCode = apiBase.getStatusCode(res);

        assertEquals(statusCode, 400);
    }

    @Test(description = "Update project successfully with valid token and invalid type of optional field")
    public void Test05_updateProjectWithInvalidTypeOfOptionField() {
        String accessToken = token.getToken();
        System.out.println("accessToken: " + accessToken);
        apiProject = new ApiProject();
        apiBase = new APIBase();

        String idProjectUpdate = createProject.idProjectCreated;

        // map for update name
        Map<String, Object> mapPutName = new HashMap<>();
        mapPutName.put("name", true);

        Response resName = apiProject.updateProject(accessToken, idProjectUpdate, mapPutName);
        int statusCode1 = apiBase.getStatusCode(resName);

        assertEquals(statusCode1, 400);

        // map for update color
        Map<String, Object> mapPutColor = new HashMap<>();
        mapPutColor.put("color", "green");

        Response resColor = apiProject.updateProject(accessToken, idProjectUpdate, mapPutColor);
        int statusCode2 = apiBase.getStatusCode(resColor);

        assertEquals(statusCode2, 400);

        // map for update favorite
        Map<String, Object> mapPutFavorite = new HashMap<>();
        mapPutFavorite.put("favorite", 123);

        Response resFavorite = apiProject.updateProject(accessToken, idProjectUpdate, mapPutFavorite);
        int statusCode3 = apiBase.getStatusCode(resFavorite);

        assertEquals(statusCode3, 400);
    }
}
