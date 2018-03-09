package com.squadfinder.brend.squadandroidcalculator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by brend on 3/6/2018.
 */

public class RawResourceLoader {
    public static String readRawResourceAsString(InputStream is) {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            int n;
            while ((n = br.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return writer.toString();
    }
}
