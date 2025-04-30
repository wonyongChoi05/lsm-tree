package org.tree.sst;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class SortedStringTable {

    private final RandomAccessFile file = new RandomAccessFile("sst.txt", "rw");
    private final FileChannel channel = file.getChannel();

    public SortedStringTable() throws FileNotFoundException {

    }

    public void write(ByteBuffer byteBuffer) throws IOException {
        channel.write(byteBuffer);
    }
}
