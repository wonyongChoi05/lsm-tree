package org.tree;

import org.tree.mem.MemTable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        MemTable memT = new MemTable();

        while (true) {
            System.out.print("Input Data (exit 입력 시 종료): ");
            input = br.readLine();

            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Good Bye!");
                break;
            }
            memT.setByteBuffer(input.getBytes(StandardCharsets.UTF_8));
            memT.flush();
        }
    }
}
