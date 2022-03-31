package api.pojo;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UserData {
    private Integer userId;
    private Integer id;
    private String title;
    private String body;

    private static final String pathToPayload = "src/test/java/api/resources/payload.json";

    public UserData(Integer userId, Integer id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public static String payload;
    static {
        try {
            payload = FileUtils.readFileToString(new File(pathToPayload), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}

