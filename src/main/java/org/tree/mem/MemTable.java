package org.tree.mem;

import org.tree.sst.SortedStringTables;

import java.io.IOException;
import java.nio.ByteBuffer;

public class MemTable {

    private final ByteBuffer byteBuffer;
    private final SortedStringTables ssts;

    public MemTable() {
        this.byteBuffer = ByteBuffer.allocate(1024);
        this.ssts = new SortedStringTables();
    }

    public void appendToBuffer(byte[] data) {
        byteBuffer.put(data);
    }

    public void flush() throws IOException {
        while (byteBuffer.hasRemaining()) {
            ssts.flush(byteBuffer);
        }
        byteBuffer.clear();
    }

    public void enableAutoCompaction() {
        ssts.runAutoCompacting();
    }

}
