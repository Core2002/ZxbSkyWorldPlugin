package fun.fifu.nekokecore.zxbskyworld.utils;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author FiFuServer
 */
public class KeyGeter {


    /**
     *
     * 1->"青铜"
     * 2->"白银"
     * 3->"黄金"
     * GetDesig(2)->"白银"
     */
    public static String GetDesig(int SCORE, String path) {
        String designat = "Error";
        try {
            JSONObject jsonObject = IOTools.getJSONObject(path);
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            for (Object key : jsonObject.keySet()) {
                arrayList.add((Integer.parseInt(key.toString())));
            }
            Collections.sort(arrayList);
            int temp = arrayList.get(0);
            for (int score : arrayList) {
                if (SCORE >= score) {
                    temp = score;
                }

            }
            String temp2 = temp + "";
            designat = jsonObject.get(temp2).toString() + "";
        } catch (Exception e) {
            e.printStackTrace();

        }
        return designat;
    }
}