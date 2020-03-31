package com.example.mymovieslist;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyHelper {
    //constants for parsing JSON response
    private final static String RESULTS_LABEL="results";
    private final static String POSTERPATH_LABEL="poster_path";
    private final static String TITLE_LABEL="title";
    private final static String RATING_LABEL="vote_average";
    private final static String OVERVIEW_LABEL="overview";
    private final static String DATE_LABEL="release_date";
    //constructor
    public MyHelper(){}
    //to request and parse the data from the api
    public static List<Movie> requestresponse(String link)
    {
        String jSONResponse="";
        URL requestUrl=createUrl(link);
        try {
            jSONResponse=httpRequest(requestUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Movie> myMoviesList=parseResponse(jSONResponse);
        return myMoviesList;
    }
    //to create URL object from String
    private static URL createUrl(String stringUrl)
    {
        URL url=null;
        try {
            url=new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    //to make a HttpRequest from the URL object
    private static String httpRequest(URL url) throws IOException
    {
        String response="";
       if (url==null)
       {
           return response;
       }
        HttpURLConnection connection =null;
        InputStream inputStream= null;
        try {
            connection=(HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200)
            {
                inputStream=connection.getInputStream();
                response=readFromJson(inputStream);
            }
            else {
                Log.e("Error on response","Code "+connection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (connection!=null)
            {
                connection.disconnect();
            }
            if (inputStream!=null)
            {
                inputStream.close();
            }
        }

        return response;
    }
    //to read the data from input stream got from the API
    private static String readFromJson(InputStream inputStream) throws IOException
    {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream!=null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader= new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line!=null)
            {
                stringBuilder.append(line);
                line=bufferedReader.readLine();
            }

        }
        return stringBuilder.toString();
    }
    //parse the response from the JSON response string
    public static List<Movie> parseResponse(String response)
    {
        List<Movie> moviesList = new ArrayList<Movie>();
        try {
            JSONObject baseObject = new JSONObject(response);
            JSONArray detailArray = baseObject.getJSONArray(RESULTS_LABEL);
            for (int i=0;i<detailArray.length();i++)
            {
                JSONObject mainObject = detailArray.getJSONObject(i);
               //variable to store the movie attributers
                String image=mainObject.optString(POSTERPATH_LABEL);
                String title = mainObject.optString(TITLE_LABEL);
                double rating=mainObject.optInt(RATING_LABEL);
                String overview =  mainObject.optString(OVERVIEW_LABEL);
                String date=mainObject.optString(DATE_LABEL);
                //adding the movie object to the list
                moviesList.add(new Movie(title,image,overview,rating,date));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return moviesList;
    }
}
