package com.codeleg.rockpaperscissor;

import java.security.SecureRandom;

public class Game {

    SecureRandom random = new SecureRandom();

    public int  generateRandom(){
        return random.nextInt(3);
    }

}
