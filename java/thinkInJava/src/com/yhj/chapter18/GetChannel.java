package com.yhj.chapter18;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class GetChannel {
    private static final int BSIZE = 1024;

    public static void main(String[] args) {
        //   RandomAccessFile
        try {
            FileChannel fileChannel = new FileOutputStream("D:\\s.txt").getChannel();
            fileChannel.write(ByteBuffer.wrap("hello".getBytes()));
            fileChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
