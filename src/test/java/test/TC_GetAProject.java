package test;

import accessToken.Token;
import api.APIBase;
import api.ApiProject;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TC_GetAProject {
    TC_CreateProject createProject;
    public ApiProject apiProject;
    public APIBase apiBase;
    Token token = new Token();

    @Test(description = "Get a project")
    public void Test01_getAProject() {
        String accessToken = token.getToken();
        apiProject = new ApiProject();
        apiBase = new APIBase();

        String idProjectGet = createProject.idProjectCreated;
        System.out.println("idProjectGet: " + idProjectGet);

        Response res = apiProject.getAProject(accessToken, idProjectGet);
        JsonObject ObjectProjectGot = apiBase.getJsonObject(res);
        String idProjectGot = ObjectProjectGot.get("id").getAsString();
        int statusCode = apiBase.getStatusCode(res);

        assertEquals(idProjectGet, idProjectGot);
        assertEquals(statusCode, 200);
    }

    @Test(description = "Get a project with invalid type of token")
    public void Test05_GetAProjectWithInvalidTypeOfToken(){
        apiProject = new ApiProject();
        apiBase = new APIBase();

        String accessToken = "";
        String idProjectGet = createProject.idProjectCreated;

        Response res = apiProject.getAProject(accessToken, idProjectGet);
        int statusCode = apiBase.getStatusCode(res);
        assertEquals(statusCode, 403);
    }
}
