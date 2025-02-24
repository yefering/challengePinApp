package com.qaautomation.Services;

import com.qaautomation.models.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PetStoreService {

    private static final String BASE_URI = "https://petstore.swagger.io/v2";
    private static final String SWAGGER_PATH = "/swagger.json";
    private static final String USER_PATH = "/user";
    private static final String FIND_BY_STATUS_ENDPOINT = "/pet/findByStatus";


    public static Integer statusService() {
        try {
            Response response = given()
                    .get(BASE_URI + SWAGGER_PATH);
            // Imprimir detalles de la respuesta para debugging (opcional)
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Response Time: " + response.getTime() + " ms");
            return response.getStatusCode();
        } catch (Exception e) {
            // Manejar excepciones y retornar un código especial
            System.err.println("Error al conectar con el servicio: " + e.getMessage());
            return 500; // Código genérico de error
        }
    }

    public static Response crearUsuario(User user) {
        return given()
                .header("Content-Type", "application/json")
                .body(user)
                .baseUri(BASE_URI)
                .basePath(USER_PATH)
                .log()
                .all()
                .post();
    }

    public static String getResponse(Response response) {
        return response.asString();
    }

    public static Response getUsuario(String usertName) {
        return given()
                .header("Content-Type", "application/json")
                .baseUri(BASE_URI)
                .basePath(USER_PATH + "/" + usertName)
                .log()
                .all()
                .get();
    }

    public static Response actualizarUsuario(String usertName, User user) {
        return given()
                .header("Content-Type", "application/json")
                .baseUri(BASE_URI)
                .basePath(USER_PATH + "/" + usertName)
                .body(user)
                .log()
                .all()
                .put();
    }

    public static Response findPetByStatu(String status) {
        return given()
                .header("Content-Type", "application/json")
                .queryParam("status", status)
                .baseUri(BASE_URI)
                .basePath(FIND_BY_STATUS_ENDPOINT)
                .log()
                .all()
                .get();
    }

    public static Response eliminarUsuario(String nomUsuario) {
        return given()
                .header("Content-Type", "application/json")
                .baseUri(BASE_URI)
                .basePath(USER_PATH + "/" + nomUsuario)
                .log()
                .all()
                .delete();
    }
}
