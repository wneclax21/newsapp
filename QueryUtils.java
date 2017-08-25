package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;
import android.util.StringBuilderPrinter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by colli on 8/24/2017.
 */

public final class QueryUtils {

    private static final String  LOG_TAG = QueryUtils.class.getSimpleName();

    public static List<Article> fetchArticleData(String requestUrl){

        URL url = createUrl(requestUrl);
        String jsonResponse = null;

        try{
            jsonResponse = makeHttpRequest(url);
        } catch(IOException e){
            Log.e(LOG_TAG, "Not able to complete the HTTP resuest.", e);
        }

        List<Article> articles = extractFeatureFromJson(jsonResponse);

        return articles;
    }

    private static URL createUrl(String stringUrl){
        URL url = null;

        try{
            url = new URL(stringUrl);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem with the URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse ="";

        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else{
                Log.e(LOG_TAG, "Resonse code error: "+ urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG, " Could not retrieve the article results.", e);
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();

        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader((inputStream), Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    private static List<Article> extractFeatureFromJson(String articleJSON){

        if(TextUtils.isEmpty(articleJSON)){
            return null;
        }

        List<Article> articles = new ArrayList<>();

        try{
            JSONObject baseJsonResponse = new JSONObject(articleJSON);

            JSONObject articleResult = baseJsonResponse.getJSONObject("response");

            JSONArray articleArray = articleResult.getJSONArray("results");

            for(int i = 0; i < articleArray.length(); i++){

                JSONObject currentArticle = articleArray.getJSONObject(i);

                    String Title = currentArticle.getString("webTitle");

                    String Section = currentArticle.getString("sectionName");

                    String Author = currentArticle.getString("sectionId");

                    String Date = currentArticle.getString("type");

                    Article article = new Article(Title, Section, Author, Date);

                    articles.add(article);
            }

        } catch (JSONException e){
            Log.e("QueryUtils", "Can't parse the article JSON results", e);
        }

        return null;
    }
}
