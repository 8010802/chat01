package com.example.chat05;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OpenAIService {

//    GPT-3 ("davinci"):
//    Документация: https://platform.openai.com/docs/guides/chat
//    private static final String BASE_URL = "https://api.openai.com/v1/engines/davinci/completions";

//    ChatGPT ("gpt-3.5-turbo"):
//    Документация: https://platform.openai.com/docs/guides/chat
    private static final String BASE_URL = "https://api.openai.com/v1/engines/davinci-codex/completions";

//    GPT-2:
//    private static final String BASE_URL = "https://api.openai.com/v1/engines/text-davinci-003/completions";




    private static final String API_KEY = "YOUR_API_KEY";

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient mClient;

    public OpenAIService(Context context) {
        mClient = new OkHttpClient();
    }

    public void getAIResponse(String message, AIServiceCallback callback) {
        try {
            OkHttpClient client = new OkHttpClient();

            JSONObject jsonInput = new JSONObject()
                    .put("prompt", message)
                    .put("max_tokens", 50);

            RequestBody requestBody = RequestBody.create(JSON, jsonInput.toString());

            Request request = new Request.Builder()
                    .url(BASE_URL)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onError("Failed to connect to server: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseString = response.body().string();
                    callback.onSuccess(responseString);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onError("Failed to create request body: " + e.getMessage());
        }
    }



    public OkHttpClient getmClient() {
        return mClient;
    }

    public interface AIServiceCallback {
        void onSuccess(String response);

        void onError(String errorMessage);
    }
}
