package com.example.PicYou_app;


import org.scribe.model.Verifier;

public class Helper {
    public static Verifier parseVerifier(String url) {
        String beacon = "verifier=";
        int startIndex = url.indexOf(beacon) + beacon.length();
        String verifierString = url.substring(startIndex);

        return new Verifier(verifierString);
    }
}
