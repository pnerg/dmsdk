package org.dmonix.io;

import java.io.IOException;

import org.dmonix.AbstractTestCase;

public class TestNullOutputStream extends AbstractTestCase {

    private byte[] testData = "Testing write to TestNullOutputStream".getBytes();

    public void testWriteSingleBytes() throws IOException {
        NullOutputStream ostream = new NullOutputStream();
        for (int i = 0; i < testData.length; i++)
            ostream.write(testData[i]);
        ostream.close();
    }

    public void testWriteByteArray() throws IOException {
        NullOutputStream ostream = new NullOutputStream();
        ostream.write(testData);
        ostream.close();
    }

    public void testWriteByteArrayWithOffset() throws IOException {
        NullOutputStream ostream = new NullOutputStream();
        ostream.write(testData, 0, testData.length);
        ostream.close();
    }

    public void testWriteByteArrayWithIllegalOffset() throws IOException {
        NullOutputStream ostream = new NullOutputStream();
        ostream.write(testData, testData.length + 100, testData.length);
        ostream.close();
    }

    public void testWriteByteArrayWithIllegalLength() throws IOException {
        NullOutputStream ostream = new NullOutputStream();
        ostream.write(testData, 0, testData.length + 100);
        ostream.close();
    }
}
