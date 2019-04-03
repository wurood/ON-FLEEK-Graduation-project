package com.example;

/**
 * Created by wurood on 10/24/2017.
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class CommonMethod {

        public static String convertWeekDays(String date) {
            String formattedDate = null;
            try {
                SimpleDateFormat originalFormat = new SimpleDateFormat(
                        "yyyy-MM-dd", Locale.ENGLISH);
                SimpleDateFormat targetFormat = new SimpleDateFormat("dd");
                Date date12 = originalFormat.parse(date);
                formattedDate = targetFormat.format(date12);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return formattedDate;

        }



        public static String convertWeekDaysMouth(String date)
        {
            String formattedDate = null;
            try
            {
                SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd" , Locale.ENGLISH);
                SimpleDateFormat targetFormat = new SimpleDateFormat("MMM yyyy");
                Date date12 = originalFormat.parse(date);
                formattedDate = targetFormat.format(date12);
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            return formattedDate;

        }

    }
