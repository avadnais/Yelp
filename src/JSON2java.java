import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.Scanner;

public class JSON2java {

    public static YelpProfile[] businessConvert(String path) throws JSONException, IOException {
        System.out.println(path);
        File file = new File(path);
        Scanner scan = new Scanner(file);
        scan.useDelimiter("\\Z");
        String linestr = scan.nextLine();
        JSONObject obj = new JSONObject(linestr.trim());
        YelpProfile[] profiles;
        profiles = new YelpProfile[10000];
        for (int i = 0; i < 10000; i++) {
            String catStr;
            linestr = scan.nextLine();
            obj = new JSONObject(linestr.trim());
            JSONArray catsAry = obj.getJSONArray("categories");
            catStr = catsAry.toString();
            catStr = catStr.replaceAll("[\\[\\]\"]", "");
            String[] cats = catStr.split(",");
            profiles[i] = new YelpProfile(
                    obj.getString("business_id"),
                    obj.getString("name"),
                    obj.getString("neighborhood"),
                    obj.getString("address"),
                    obj.getString("city"),
                    obj.getString("state"),
                    obj.getString("postal_code"),
                    obj.getDouble("latitude"),
                    obj.getDouble("longitude"),
                    obj.getInt("stars"),
                    cats
            );
        }
        return profiles;
    }

    public static int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

    public static YelpProfile searchProfiles(int index, YelpProfile[] profiles) {
        if (index < profiles.length) return profiles[index];
        else return null;
    }


}

