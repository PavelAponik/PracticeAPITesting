package api.resources;

import org.json.JSONObject;

public class Json {

    private void json() {
        JSONObject geo = new JSONObject()
                .put("lat", "-31.8129")
                .put("lng", "62.5342");

        JSONObject address = new JSONObject()
                .put("street", "Skiles Walks")
                .put("suite", "Suite 351")
                .put("city", "Roscoeview")
                .put("zipcode", "33263")
                .put("geo", geo);

        JSONObject company = new JSONObject()
                .put("name", "Keebler LLC")
                .put("catchPhrase", "User-centric fault-tolerant solution")
                .put("bs", "revolutionize end-to-end systems");

        JSONObject user5 = new JSONObject()
                .put("id", 5)
                .put("name", "Chelsey Dietrich")
                .put("username", "Kamren")
                .put("email", "Lucio_Hettinger@annie.ca")
                .put("address", address)
                .put("phone", "(254)954-1289")
                .put("website", "demarco.info")
                .put("company", company);
    }
}