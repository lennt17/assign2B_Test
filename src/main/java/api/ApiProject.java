package api;
import io.restassured.response.Response;

import java.util.Map;
import static constant.Constant.*;

public class ApiProject extends APIBase {
    String basePathProject = basePath_project;

    public Response createProject(String accessToken, Map mapPost){
        return sendPost(accessToken, basePathProject, mapPost);
    }

    public Response getAProject(String accessToken, String idProjectGet){
        return sendGet(accessToken, basePathProject, idProjectGet);
    }

    public Response getAllProjects(String accessToken){
        String idProjectGet = "";
        return sendGet(accessToken, basePathProject, idProjectGet);
    }

    public Response updateProject(String accessToken, String idProject, Map mapUpdate){
        String basePathUpdate = basePathProject + "/" + idProject;
        return sendPost(accessToken, basePathUpdate ,mapUpdate);
    }
}
