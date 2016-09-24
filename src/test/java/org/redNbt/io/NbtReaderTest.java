package org.redNbt.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redNbt.util.TagInfoPrinter;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by test_ on 2016/9/23.
 */
public class NbtReaderTest {

    private InputStream inputStream;
    private GZIPInputStream gzipInputStream;
    private BufferedInputStream bufferedInputStream;
    private NbtReader nbtReader;

    private String results;

    @Before
    public void setUp() throws Exception {
        inputStream = getClass().getResourceAsStream("/test_1.schematic");
        gzipInputStream = new GZIPInputStream(inputStream);
        bufferedInputStream = new BufferedInputStream(gzipInputStream);
        nbtReader = new NbtReader(bufferedInputStream);

        StringBuilder sb = new StringBuilder();
        try(InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("/test_1_nbt_info"))) {
            char[] buffer = new char[1024];
            int length = 0;
            while ((length = isr.read(buffer)) > 0)
                sb.append(buffer, 0, length);
        }

        results = sb.toString().replace("\r\n", "\n");
    }

    @Test
    public void readTag() throws Exception {
        StringBuilder tagInfo = new StringBuilder();
        TagInfoPrinter tagInfoPrinter = new TagInfoPrinter(tagInfo, false);

        nbtReader.readTag(tagInfoPrinter);
        String testResults = tagInfo.toString();
        assertEquals(results, testResults);
    }

    @After
    public void tearDown() throws Exception {
        nbtReader.close();

        inputStream = null;
        gzipInputStream = null;
        bufferedInputStream = null;
        nbtReader = null;
        results = null;
    }

}