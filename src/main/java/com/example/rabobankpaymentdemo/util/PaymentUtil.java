package com.example.rabobankpaymentdemo.util;

import com.example.rabobankpaymentdemo.exception.InvalidSignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import static com.example.rabobankpaymentdemo.model.ErrorReasonCode.INVALID_SIGNATURE;

@Slf4j
public class PaymentUtil {

    private static String PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEowIBAAKCAQEAryLyouTQr1dvMT4qvek0eZsh8g0DQQLlOgBzZwx7iInxYEAg\n" +
            "MNxCKXiZCbmWHBYqh6lpPh+BBmrnBQzB+qrSNIyd4bFhfUlQ+htK08yyL9g4nyLt\n" +
            "0LeKuxoaVWpInrB5FRzoEY5PPpcEXSObgr+pM71AvyJtQLxZbqTao4S7TRKecUm3\n" +
            "2Wwg+FWY/StSKlox3QmEaxEGU7aPkaQfQs4hrtuUePwKrbkQ2hQdMpvI5oXRWzTq\n" +
            "afvEQvND+IyLvZRqf0TSvIwsgtJd2tch2kqPoUwng3AmUFleJbMjFNzrWM7TH9Lk\n" +
            "KPItYtSuMTzeSe9o0SmXZFgcEBh5DnETZqIVuQIDAQABAoIBAAfjgxpjRCMhxRhq\n" +
            "vHIhdwOjQTwt6d+bycd7DbeukEHuNLkpKkoJIdHMZNhTS7eoJ/JEZ0EtGhq35gAM\n" +
            "OxCXcTB8xP/NhZ6nFsatRAmWRtBw5NwGLsAgBFe5LUZ7qxm6yTlyO+HOjzM6ii/H\n" +
            "0tFo4K478Bar7k/kLAMX2eFTsTwfb23J8KhbVCXK/Oh02lqlqbf8/T4MowS1px25\n" +
            "LGQlS7KFW9CkuUVQ83IyCw9EbDJSMMr+Hkw8Bo5VllJ1s8RK++Fn3KwveCjOmZub\n" +
            "6luzMAJFdeDPvCi0mNctZzkB0abOmOcJQt1fHTJQJ9x69q4JRD9N+FvG3QwqgMLL\n" +
            "z0i63IECgYEA5v6G52aBUzKTLl3kn/P0WJ85RteOo2BHsIP+0toD6RMKNREdSCLs\n" +
            "itwsF/DKH3t4id0thiAK2cLRdY2SbWp0l0jXLyXd2VcZwTkEA2iQJJ9OLrGvDka/\n" +
            "j1CzdTjAj9ZBHsZzH93OoL+xSYeOhUMnIoWRzVe1cRSz3G83HeCBhGUCgYEAwhhz\n" +
            "3AszQjpjIt2P/eBeiEZALVsmt3lFm3cSuvNlo3b05it3OP/aVSfABSS5xfi4XqGB\n" +
            "YgDk4UbiRQxGlixht8ZoQWqdTDWKnSJi56uGEFg9F361kAVfZb7zVIBxddGfEuga\n" +
            "OaigNGe0M7J0fbWdwbx8EVsZOwXWp/TbbSblJMUCgYBKc3EBtj0qlptvj1233EY+\n" +
            "Jhus5J8Zs1eH4hNI3HH0NmnMztZUQMViwDIKCVbsLLyeGsaoez1kEHG4ZMf0MiKf\n" +
            "/B83GApYGcW4TGspuhLzatElJanZfR4S0Bz3RDJ0accVZzsF41TM5Nv8ag+ajhlX\n" +
            "/BsRRxq49sY93y6xl4HHLQKBgF9E8VmIhdh0IET0y8CpaL0q/kVFAHP+KpRsldz9\n" +
            "q13Y/cwceaCYtOonYLElnan2s0h/raoVFkMdL+MEa4E6t5wk3vd9BUhq32bRggqE\n" +
            "voE3ToVBxIy0lmaym21Wvlo+Uf5NvtGeW0Rdwq29YkBx7MUzZxJ9zJyT+RDntuyU\n" +
            "stShAoGBAOQMrfyQCFcInYo0aNdtm6spUbTEfGNnMVKq4hdLsKv5Yv1LotcELeWs\n" +
            "Avx59tOuhYNOod7oAWfGLQjjKZk1FOHjTD+CUg1Iixw2gwJ8Kz8OQZsSvNjMIJX8\n" +
            "qfeCXIBPrtblS37vxqk0t+V2vREC0575yzkckQmWaGBFPEALxI3t\n" +
            "-----END RSA PRIVATE KEY-----";

    private static PrivateKey privateKey;

    private static PrivateKey createPrivateKey() {
        if(privateKey == null){
            java.security.Security.addProvider(
                    new org.bouncycastle.jce.provider.BouncyCastleProvider()
            );
            String stripPrivateKey = PRIVATE_KEY.replaceAll("-----BEGIN RSA PRIVATE KEY-----", "")
                    .replaceAll("-----END RSA PRIVATE KEY-----", "")
                    .replaceAll("\n", "");
            PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(stripPrivateKey));

            KeyFactory keyFactory = null;
            try {
                keyFactory = KeyFactory.getInstance("RSA");
                privateKey = keyFactory.generatePrivate(keySpecPrivate);
            } catch (Exception e) {
                log.error("Exception for createPrivateKey: {}",
                        e.getMessage());
            }

        }
        return privateKey;
    }

    public static String generateSignature(String plainText) {
        byte[] signature = {'0'};
        PrivateKey privateKey = createPrivateKey();
        try {
            Signature privateSignature = Signature.getInstance("SHA256withRSA");
            privateSignature.initSign(privateKey);
            privateSignature.update(plainText.getBytes(StandardCharsets.UTF_8));
            signature = privateSignature.sign();
        } catch (Exception e){
            log.error("Exception for signature: {}",
                    e.getMessage());

        }
        return  Base64.getEncoder().encodeToString(signature);
    }

}
