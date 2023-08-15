package com.spaco_apoio.api.exceptions;

import java.io.Serial;

public class UniqueException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public UniqueException(String msg){
        super(msg.concat(" já está sendo usado por outro registro!"));
    }
}
