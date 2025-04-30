package org.tree.sst;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class SortedStringTable {

    private final File file;

    public SortedStringTable() throws IOException {
        this.file = File.createTempFile("sst_", ".db");
        System.out.println("Created SST At: " + file.getAbsolutePath());
        this.file.deleteOnExit();
    }

    public RandomAccessFile getFile() throws IOException {
        return new RandomAccessFile(file, "r");
    }

    public void write(ByteBuffer buffer) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            raf.write(bytes);
        }
    }

    public Map<String, String> readData() throws IOException {
        Map<String, String> dataMap = new HashMap<>();

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            String line;
            while ((line = raf.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length != 2) continue;

                String userNo = parts[0];
                String[] fields = parts[1].split(",");
                StringBuilder valueBuilder = new StringBuilder();

                for (String field : fields) {
                    valueBuilder.append(field).append(", ");
                }

                if (valueBuilder.length() > 0) {
                    valueBuilder.setLength(valueBuilder.length() - 2);
                }

                dataMap.put(userNo, valueBuilder.toString());
            }
        }

        return dataMap;
    }

}
