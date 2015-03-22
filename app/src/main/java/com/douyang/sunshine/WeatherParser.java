package com.douyang.sunshine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by douyang on 3/21/15.
 */
public class WeatherParser {

    /**
     * Given a string of the form returned by the api call:
     * http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7
     * retrieve the maximum temperature for the day indicated by dayIndex
     * (Note: 0-indexed, so 0 would refer to the first day).
     */
    public static double getMaxTemperatureForDay(String weatherJsonStr, int dayIndex)
            throws JSONException {
        JSONObject weather = new JSONObject(weatherJsonStr);
        JSONArray weatherList = weather.getJSONArray("list");

        JSONObject day = weatherList.getJSONObject(dayIndex);
        JSONObject dayTemp = day.getJSONObject("temp");
        Double dayTempMax = dayTemp.getDouble("max");

        return dayTempMax;
    }
}
