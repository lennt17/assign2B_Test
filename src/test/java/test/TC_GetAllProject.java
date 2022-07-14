package test;

import accessToken.Token;
import api.APIBase;
import api.ApiProject;
import com.google.gson.JsonArray;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TC_GetAllProject {
    public ApiProject apiProject;
    public APIBase apiBase;
    Token token = new Token();

    @Test(description = "Get all project with valid token successfully")
    public void Test01_getAllProject() {
        String accessToken = token.getToken();
        apiProject = new ApiProject();
        apiBase = new APIBase();

        Response res = apiProject.getAllProjects(accessToken);
        int statusCode = apiBase.getStatusCode(res);
        JsonArray arr = apiBase.getJsonArray(res);
        System.out.println(arr);

        assertEquals(statusCode, 200);
    }

    @Test(description = "Get all project without token")
    public void Test04_getAllProjectWithoutToken() {
        String accessToken = "";
        apiProject = new ApiProject();
        apiBase = new APIBase();

        Response res = apiProject.getAllProjects(accessToken);
        int statusCode = apiBase.getStatusCode(res);
        assertEquals(statusCode, 401);
    }

    @Test(description = "Get all project with non-existing token")
    public void Test05_getAllProjectWithNonExistingToken() {
        String accessToken = "!@#123";
        apiProject = new ApiProject();
        apiBase = new APIBase();

        Response res = apiProject.getAllProjects(accessToken);
        int statusCode = apiBase.getStatusCode(res);
        assertEquals(statusCode, 401);
    }
}
