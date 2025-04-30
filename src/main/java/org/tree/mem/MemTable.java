package org.tree.mem;

import org.tree.sst.SortedStringTable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MemTable {

    private final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    private final SortedStringTable sst = new SortedStringTable();

    public MemTable() throws FileNotFoundException {
    }

    public void setByteBuffer(byte[] data) {
        byteBuffer.put(data);
    }

    public void flush() throws IOException {
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            sst.write(byteBuffer);
        }
        byteBuffer.clear();
    }

}
