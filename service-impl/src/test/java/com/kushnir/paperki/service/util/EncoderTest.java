package com.kushnir.paperki.service.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static com.kushnir.paperki.service.util.Encoder.encoding;
import static com.kushnir.paperki.service.util.Encoder.matching;

public class EncoderTest {

    @Test
    public void encodeDecodeTest() throws IOException {
        String pass = "Pap11111";
        String hash = encoding(pass);
        Assert.assertTrue(matching(hash, pass));
    }

}
