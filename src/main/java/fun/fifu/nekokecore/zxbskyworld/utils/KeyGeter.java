/*
 * Copyright (c) 2020 Core2002
 * ZxbSkyWorldPlugin is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

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