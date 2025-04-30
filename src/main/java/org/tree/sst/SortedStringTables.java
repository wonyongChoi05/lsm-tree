package org.tree.sst;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SortedStringTables {
    private final List<SortedStringTable> ssts;

    public SortedStringTables() {
        this.ssts = new ArrayList<>();
    }

    public void flush(ByteBuffer byteBuffer) throws IOException {
        final SortedStringTable sst = new SortedStringTable();
        sst.write(byteBuffer);
        this.ssts.add(sst);
    }

    public void runAutoCompacting() {
        Runnable compactionTask = () -> {
            while (true) {
                try {
                    Thread.sleep(300_000);
                    if (ssts.size() >= 2) {
                        compaction();
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(compactionTask).start();
    }

    public void compaction() throws IOException {
        TreeMap<String, String> mergedData = new TreeMap<>();
        for (SortedStringTable sst : ssts) {
            RandomAccessFile file = sst.getFile();
            Map<String, String> sstData = sst.readData();
            for (Map.Entry<String, String> entry : sstData.entrySet()) {
                mergedData.put(entry.getKey(), entry.getValue());
            }
        }
        ByteBuffer mergedByteBuffer = ByteBuffer.wrap(serializeData(mergedData));
        SortedStringTable newSST = new SortedStringTable();
        newSST.write(mergedByteBuffer);

        this.ssts.clear();
        this.ssts.add(newSST);
    }

    private byte[] serializeData(Map<String, String> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        return sb.toString().getBytes();
    }
}
