package ca.freedommobile.api.framework.apis;

import ca.freedommobile.api.framework.config.Config;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import java.util.Map;

public class APIManger {

    private static APIManger apiManger;

    private APIManger() {
        RestAssured.basePath = Config.getProperty("host");
    }

    public  Response doGet(String path, Map<String, Object> pathParams, Map<String, Object> queryParams){
        if(null==pathParams && null==queryParams) {
            return RestAssured.given().contentType(ContentType.JSON).then().get(path);
        }
        if(null!=pathParams){
            RequestSpecBuilder specification = new RequestSpecBuilder();
            pathParams.forEach((k,v)->{
                specification.addParameter(k,v);
            });
            if(null==queryParams){
                return  RestAssured.given(specification.build()).then().get(path);
            }else {
                queryParams.forEach((k,v)->{
                    specification.addQueryParam(k,v);
                });
                return  RestAssured.given(specification.build()).then().get(path);
            }

        }
        return RestAssured.given().contentType(ContentType.JSON).then().get(path);
    }


    public  Response doPost(String url, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams ){

        if(null==pathParams && null==queryParams) {
            return RestAssured.given().body(body).contentType(ContentType.JSON).then().post(url);
        }
        if(null!=pathParams){
            RequestSpecBuilder specification = new RequestSpecBuilder();
            pathParams.forEach((k,v)->{
                specification.addParameter(k,v);
            });
            if(null==queryParams){
                return  RestAssured.given(specification.build()).body(body).post(url);
            }else {
                queryParams.forEach((k,v)->{
                    specification.addQueryParam(k,v);
                });
                return  RestAssured.given(specification.build()).body(body).post(url);
            }

        }
        return RestAssured.given().contentType(ContentType.JSON).then().get(url);
    }

    public  Response doPut(String url, Object body, Map<String, Object> pathParams, Map<String, Object> queryParams ){

        if(null==pathParams && null==queryParams) {
            return RestAssured.given().body(body).contentType(ContentType.JSON).then().post(url);
        }
        if(null!=pathParams){
            RequestSpecBuilder specification = new RequestSpecBuilder();
            pathParams.forEach((k,v)->{
                specification.addParameter(k,v);
            });
            if(null==queryParams){
                return  RestAssured.given(specification.build()).body(body).put(url);
            }else {
                queryParams.forEach((k,v)->{
                    specification.addQueryParam(k,v);
                });
                return  RestAssured.given(specification.build()).body(body).put(url);
            }

        }
        return RestAssured.given().contentType(ContentType.JSON).then().get(url);
    }


    public  Response doDelete(String path, Map<String, Object> pathParams, Map<String, Object> queryParams){
        if(null==pathParams && null==queryParams) {
            return RestAssured.given().contentType(ContentType.JSON).then().delete(path);
        }
        if(null!=pathParams){
            RequestSpecBuilder specification = new RequestSpecBuilder();
            pathParams.forEach(specification::addParam);
            if(null==queryParams){
                return  RestAssured.given(specification.build()).then().delete(path);
            }else {
                queryParams.forEach(specification::addQueryParam);
                return  RestAssured.given(specification.build()).then().delete(path);
            }

        }
        return RestAssured.given().contentType(ContentType.JSON).then().get(path);
    }

    public static APIManger getInstance(){
        synchronized (apiManger){
            if(apiManger==null){
                apiManger = new APIManger();
            }
        }
        return apiManger;
    }
}
