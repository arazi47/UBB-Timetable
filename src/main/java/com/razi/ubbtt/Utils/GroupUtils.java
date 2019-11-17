package com.razi.ubbtt.Utils;

import java.util.ArrayList;
import java.util.List;

public class GroupUtils {
    public static String getYearFromGroup(String group) {
        if (group.length() != 3) {
            // Not ok
            return "";
        }

        StringBuilder result = new StringBuilder();
        if (group.startsWith("2")){
            result.append("IR");
        }
        else if (group.startsWith("7")) {
            result.append("IG");
        }
        else if (group.startsWith("9")) {
            result.append("IE");
        }

        result.append(group.charAt(1));

        return result.toString();
    }

    public static List<String> getGroupAndSubgroupsAsList(String group) {
        return new ArrayList<>(List.of(group, group + "/1", group + "/2"));
    }

    public static List<String> getGroupAndSubgroupAndYearAsList(String group) {
        List<String> groupList = getGroupAndSubgroupsAsList(group);
        groupList.add(getYearFromGroup(group));
        return groupList;
    }

}
