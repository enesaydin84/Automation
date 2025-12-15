package api.client;

import api.config.ApiConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiClient {
    private final ThreadLocal<RequestSpecification> spec = ThreadLocal.withInitial(() ->
            new RequestSpecBuilder()
                    .setBaseUri(ApiConfig.BASE_URL)
                    .setContentType(ContentType.JSON)
                    .log(LogDetail.URI)
                    .log(LogDetail.BODY)
                    .build()
    );

    protected RequestSpecification request() {
        return given().spec(spec.get());
    }

    /**
     * Authorization header'ı otomatik eklemek için kullan.
     */
    protected RequestSpecification authRequest() {
        return request()
                .header("Authorization", "Bearer " + TokenProvider.getToken());
    }
}