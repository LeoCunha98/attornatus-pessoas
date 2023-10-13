package com.tecnico.attornatus.pessoas.exception;

import lombok.Data;

import java.io.Serializable;

@Data
public class StandardError implements Serializable {
    private Integer status;
    private String msg;
    private Long timeStamp;

    public StandardError(Integer status, String msg, Long timeStamp) {
        super();
        this.status = status;

        int indiceHashtag = msg.indexOf("#") + 1;
        if (indiceHashtag != 0) {
            this.msg = "[" + msg.substring(indiceHashtag);
        } else {
            this.msg = msg;
        }
        this.timeStamp = timeStamp;
    }

}
