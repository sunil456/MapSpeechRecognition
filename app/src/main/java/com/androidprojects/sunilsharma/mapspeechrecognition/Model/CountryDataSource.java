package com.androidprojects.sunilsharma.mapspeechrecognition.Model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by sunil sharma on 11/5/2017.
 */

public class CountryDataSource
{
    //22.626316, 78.912201
    public static final String COUNTRY_KEY = "country";
    public static final float MINIMUM_CONFIDENCE_LEVE = 0.04f;
    public static final String DEFAULT_COUNTRY_NAME = "India";
    public static final Double DEFUALT_COUNTRY_LATITUDE = 22.626316;
    public static final Double DEFUALT_COUNTRY_LONGITUDE = 78.912201;
    public static final String DEFUALT_MESSAGE = "Be Happy!";


    private Hashtable<String , String> countriesAndMessages;

    public CountryDataSource(Hashtable<String, String> countriesAndMessages)
    {
        this.countriesAndMessages = countriesAndMessages;
    }


    public String matchWithMinimumConfidenceLevelOfUserWords(ArrayList<String> userWords , float[] confidenceLevels)
    {
        /** Here First We have to Check the Argument which we are passing though
         * this Method Is Actually NULL or NOT*/

        if(userWords == null || confidenceLevels == null)
        {
            return DEFAULT_COUNTRY_NAME;
        }

        int numberOfUserWords = userWords.size();

        Enumeration<String> countries;

        for(int index = 0 ; index < numberOfUserWords && index < confidenceLevels.length ; index++)
        {
            if(confidenceLevels[index] < MINIMUM_CONFIDENCE_LEVE)
            {
                break;
            }

            String acceptedUserWords = userWords.get(index);

            countries = countriesAndMessages.keys();

            while (countries.hasMoreElements())
            {
                String selectedCountry = countries.nextElement();

                if(acceptedUserWords.equalsIgnoreCase(selectedCountry))
                {
                    return acceptedUserWords;
                }
            }
        }

        return DEFAULT_COUNTRY_NAME;
    }


    public String getTheInfoOfTheCountry(String country)
    {
        return countriesAndMessages.get(country);
    }
}
