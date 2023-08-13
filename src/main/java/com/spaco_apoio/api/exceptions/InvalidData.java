package com.spaco_apoio.api.exceptions;

import java.io.Serial;

public class InvalidData extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidData(String msg){
        super(msg.concat(" inválido!"));
    }
}
