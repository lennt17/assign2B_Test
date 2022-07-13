package api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import utils.configs.ConfigSettings;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class ApiProject extends APIBase {
    public ConfigSettings configSettings;
    public APIBase apiBase;
    public JsonObject ObjectCreated;
    public JsonObject ObjectGot;
    public JsonObject ObjectUpdated;

    Gson g = new Gson();
    public ApiProject(){
        configSettings = new ConfigSettings(System.getProperty("user.dir"));
    }

    public JsonObject create(String accessToken, Map mapPost){
        apiBase = new APIBase();
        basePath = configSettings.getBasePathProject();
        return ObjectCreated = apiBase.getJsonObject(sendPost(accessToken, basePath, mapPost));
    }

    public JsonObject get(String accessToken, String idProjectGet){
        basePath = configSettings.getBasePathProject();
        return ObjectGot = apiBase.getJsonObject(sendGet(accessToken, basePath, idProjectGet));
    }

    public JsonArray getAll(String accessToken){
        basePath = configSettings.getBasePathProject();
        String idProjectGet = "";
        JsonArray jsonArray;
        return jsonArray = apiBase.getJsonArray(sendGet(accessToken, basePath, idProjectGet));
    }

    public JsonObject update(String accessToken, String idProject, Map mapUpdate){
        basePath = configSettings.getBasePathProject() + "/" + idProject;
        String basePathGet = configSettings.getBasePathProject();
        sendPost(accessToken, basePath ,mapUpdate);
        return ObjectUpdated = apiBase.getJsonObject(sendGet(accessToken, basePathGet, idProject));
    }
}
