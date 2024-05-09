package Controller.Activite;


import com.google.gson.*;
import okhttp3.*;

import java.io.IOException;

public class Translator {
    private static final String ENDPOINT_URL = "https://api.cognitive.microsofttranslator.com/";
    private static final String API_KEY = "473d5254653d47bb8054f4e9c3c737d4";

    public static String translate(String text, String sourceLang, String targetLang) throws Exception {
        OkHttpClient client = new OkHttpClient();
        System.out.println("translating" + text);

        HttpUrl.Builder urlBuilder = HttpUrl.parse(ENDPOINT_URL + "/translate").newBuilder();
        urlBuilder.addQueryParameter("api-version", "3.0");
        urlBuilder.addQueryParameter("from", sourceLang);
        urlBuilder.addQueryParameter("to", targetLang);

        String url = urlBuilder.build().toString();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "[{\"Text\": \"" + text + "\"}]");

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Ocp-Apim-Subscription-Region", "southafricanorth")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String responseBody = response.body().string();
            String translation = parseTranslation(responseBody);
            return translation;
        }
    }

    private static String parseTranslation(String responseBody) {
        // Utilize Gson to parse the JSON response
        JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
        JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
        JsonArray translations = jsonObject.getAsJsonArray("translations");
        JsonObject translationObject = translations.get(0).getAsJsonObject();
        String translatedText = translationObject.get("text").getAsString();

        // Return the translated text
        return translatedText;
    }

    public static void main(String[] args) throws Exception {
        String translatedText = translate("Hello", "en", "fr");
        System.out.println(translatedText);
    }


}