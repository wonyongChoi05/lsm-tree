package org.tree;

import org.tree.mem.MemTable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        // insert into user(user_no, username, birth) values (0, "nyong", "2005-06-25")
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        MemTable memT = new MemTable();
        memT.enableAutoCompaction();

        Map<String, String> dataMap = new HashMap<>();

        while (true) {
            System.out.print("\nInput Data (exit 입력 시 종료): ");
            input = br.readLine();

            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Good Bye!");
                break;
            }

            parseInput(input, dataMap);

            String dataStr = mapToString(dataMap);
            memT.appendToBuffer(dataStr.getBytes(StandardCharsets.UTF_8));
            memT.flush();
        }
    }

    private static void parseInput(String input, Map<String, String> dataMap) {
        String[] parts = input.split("values");
        if (parts.length == 2) {
            String[] keys = parts[0].replace("insert into user(", "").replace(")", "").split(",");
            String[] values = parts[1].replace("(", "").replace(")", "").split(",");

            for (int i = 0; i < keys.length; i++) {
                dataMap.put(keys[i].trim(), values[i].trim().replace("\"", ""));
            }
        }
    }

    private static String mapToString(Map<String, String> dataMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}
