package api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.configs.ConfigSettings;

import java.util.Map;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;

public class APIBase {
    Gson g = new Gson();
    private ConfigSettings configSettings;
    public APIBase(){
        configSettings = new ConfigSettings(System.getProperty("user.dir"));
    }

    public Response sendPost(String accessToken, String basePathPT, Map mapPost) {
        RestAssured.baseURI = configSettings.getBaseURI();
        basePath = basePathPT;
        Response re = given()
                .header("authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .when()
                .body(mapPost)
                .post();
        re.prettyPrint();

        return re;
    }

    public Response sendGet(String accessToken, String basePathPT, String id) {
        RestAssured.baseURI = configSettings.getBaseURI();
        basePath = basePathPT;
        final String GET = "/" + id;
        Response respon = given()
                .contentType(ContentType.JSON)
                .headers("authorization", "Bearer " + accessToken)
                .get(GET);

        respon.prettyPrint();

        return respon;
    }

    public void sendPostReopen(String accessToken, String basePathPT, String str_id) {
        RestAssured.baseURI = configSettings.getBaseURI();
        basePath = basePathPT + "/" + str_id;
        final String REOPEN = "/reopen";
        Response resp = given()
                .header("authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .post(REOPEN);
        resp.prettyPrint();
    }

    public String getJsonAsString(Response Response){
        return Response.body().asString();
    }

    public JsonObject getJsonObject(Response re){
        Object res = re.as(Object.class);
        String a = g.toJson(res);
        JsonObject k = g.fromJson(a, JsonObject.class);
        return k;
    }

    public JsonArray getJsonArray(Response re){
        Object res = re.as(Object.class);
        String a = g.toJson(res);
        JsonArray k = g.fromJson(a, JsonArray.class);
        return k;
    }
}
