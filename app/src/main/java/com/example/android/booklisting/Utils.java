package com.example.android.booklisting;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


class Utils {

    public Utils() {
    }


    private static final String LOG_TAG = Utils.class.getSimpleName();

    static ArrayList<Books> booksLog(String urlReq) {

        URL url = createUrl(urlReq);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
        }

        return fromJson(jsonResponse);
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "URL Error", e);
        }
        return url;
    }

    private static ArrayList<Books> fromJson(String jsonBooks) {
        if (TextUtils.isEmpty(jsonBooks)) {
            return null;
        }

        ArrayList<Books> books = new ArrayList<>();
        try {
            JSONObject respJson = new JSONObject(jsonBooks);
            if (respJson.has("items")) {
                JSONArray booksArray = respJson.getJSONArray("items");

                for (int j = 0; j < booksArray.length(); j++) {

                    JSONObject book = booksArray.getJSONObject(j);
                    JSONObject vInfo = book.getJSONObject("volumeInfo");
                    JSONObject image = vInfo.getJSONObject("imageLinks");
                    String tittle = vInfo.getString("title");

                    String images;

                    if (image.has("thumbnail")) {
                        images = image.getString("thumbnail");
                    } else {
                        images = "No Image";
                    }

                    JSONArray authorsArr;
                    ArrayList<String> author = new ArrayList<>();

                    if (vInfo.has("authors")) {
                        authorsArr = vInfo.getJSONArray("authors");
                        for (int i = 0; i < authorsArr.length(); i++) {
                            author.add(authorsArr.getString(i));
                        }
                    } else {
                        author.add("Unknow name");
                    }

                    String description;

                    if (vInfo.has("description")) {
                        description = vInfo.getString("description");
                    } else {
                        description = "No description.";
                    }

                    Books booksNew = new Books(images, tittle, author, description);
                    books.add(booksNew);
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the book JSON results", e);
        }
        return books;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(8000);
            urlConnection.setConnectTimeout(20000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


}

