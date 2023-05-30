package com.project.uncleedikbot.service.util;

import java.security.SecureRandom;

public class RandomUtil {

    public static int getRandomNumber(int max) {
        SecureRandom random = new SecureRandom();
        return random.nextInt(max);
    }
}
