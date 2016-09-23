package org.redNbt.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redNbt.util.TagInfoPrinter;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

/**
 * Created by test_ on 2016/9/23.
 */
public class NbtReaderTest {

    private InputStream inputStream;
    private GZIPInputStream gzipInputStream;
    private BufferedInputStream bufferedInputStream;
    private NbtReader nbtReader;


    @Before
    public void setUp() throws Exception {
        inputStream = getClass().getResourceAsStream("/test_1.schematic");
        gzipInputStream = new GZIPInputStream(inputStream);
        bufferedInputStream = new BufferedInputStream(gzipInputStream);
        nbtReader = new NbtReader(bufferedInputStream);
    }

    @Test
    public void readTag() throws Exception {
        StringBuilder tagInfo = new StringBuilder();
        TagInfoPrinter tagInfoPrinter = new TagInfoPrinter(tagInfo, false);

        nbtReader.readTag(tagInfoPrinter);
        Logger logger= Logger.getLogger(NbtReaderTest.class.getName());
        //System.out.println(tagInfo.toString());
        logger.info(tagInfo.toString());

    }

    @After
    public void tearDown() throws Exception {
        nbtReader.close();

        inputStream = null;
        gzipInputStream = null;
        bufferedInputStream = null;
        nbtReader = null;
    }

}