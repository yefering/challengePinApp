package com.qaautomation.Services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.qaautomation.models.Response.EntriesResponse.Item;

import com.qaautomation.models.Response.EntriesResponse.ResponseWrapper;
import com.qaautomation.models.User;
import io.restassured.response.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class DemoblazeService {
    private static final String BASE_URI = "https://api.demoblaze.com";
    private static final String USER_PATH = "/entries";
    private static final String BYCAT_PATH = "/bycat";

    public static Response entries() {
        return given()
                .header("Content-Type", "application/json")
                .baseUri(BASE_URI)
                .basePath(USER_PATH)
                .log()
                .all()
                .get();
    }

    public static Response bycat(String categoria) {
        String requestBody = "{ \"cat\": \"" + categoria + "\" }";
        return given()
                .header("Content-Type", "application/json")
                .baseUri(BASE_URI)
                .basePath(BYCAT_PATH)
                .body(requestBody)
                .log()
                .all()
                .post();
    }

    public static List<Item> getListItems(Response resp) {
        String jsonResponse = resp.asPrettyString();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ResponseWrapper response = gson.fromJson(jsonResponse, ResponseWrapper.class);
        return response.getItems();
    }
}
