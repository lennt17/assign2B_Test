package api;

import java.util.Map;
import static constant.Constant.*;
import io.restassured.response.Response;

public class ApiTask extends APIBase {
    String basePathTask = basePath_task;

    public Response createTask(String accessToken, Map mapPost){
        return sendPost(accessToken, basePathTask, mapPost);
    }

    public Response reOpenTask(String accessToken, String str_id_task) {
        return sendPostReopen(accessToken,basePathTask, str_id_task);
    }
}
