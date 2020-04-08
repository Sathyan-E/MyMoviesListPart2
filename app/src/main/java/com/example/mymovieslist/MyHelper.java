package com.example.mymovieslist;

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
    private final static String ID_LABEL="id";
    //constructor
    public MyHelper(){}
    //to request and parse the data from the api
    public static String requestresponse(String link)
    {
        String jSONResponse="";
        URL requestUrl=createUrl(link);
        Log.i("link check","At helper requesting method"+link);
        try {
            jSONResponse=httpRequest(requestUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jSONResponse;
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
                int id=mainObject.optInt(ID_LABEL);
                boolean fav=false;
                Log.i("id checking","at parsing fisr detail"+id);
                moviesList.add(new Movie(id,title,image,overview,rating,date,fav));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return moviesList;
    }

    public static List<Trailer> trailerParseResponse(String response)
    {
        List<Trailer> trailerList = new ArrayList<Trailer>();

        try {
            Log.i("link check","At helper method"+response);
            JSONObject baseTrailerObject = new JSONObject(response);
            JSONArray resultsArray = baseTrailerObject.getJSONArray("results");
            for (int i=0;i<resultsArray.length();i++)
            {
                JSONObject mainObject =  resultsArray.getJSONObject(i);
                String type= mainObject.optString("type");

                if (type.equals("Trailer"));
                {
                    String key=mainObject.optString("key");
                    String name =mainObject.optString("name");
                    Log.i("attributes check","At helper parwsing method :"+name+" "+key);
                    trailerList.add(new Trailer(name,key));
                    Log.i("trailer list checking","At MyHelper method"+trailerList.size());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailerList;
    }

    public static List<UserReview> userReviewResponseParsing(String reponse)
    {
        List<UserReview> reviewList = new ArrayList<UserReview>();

        try{
            JSONObject basereviewObject = new JSONObject(reponse);
            JSONArray reviewArray =basereviewObject.getJSONArray("results");
            for (int i=0;i<reviewArray.length();i++)
            {
                JSONObject arrayObejct = reviewArray.getJSONObject(i);
                String userReview = arrayObejct.optString("content");
                String reviewUrl=arrayObejct.optString("url");
                reviewList.add(new UserReview(userReview,reviewUrl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviewList;
    }
}
