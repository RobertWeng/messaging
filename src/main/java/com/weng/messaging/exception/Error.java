package com.weng.messaging.exception;

public class Error {

    public enum Code {
        NOT_FOUND,
        INVALID_REQUEST,
        INVALID_STATE,
        INTERNAL_SERVER_ERROR,
        WS_CONNECT_FAILED,
    }

    public enum Msg {
        NOT_FOUND,
        INTERNAL_SERVER_ERROR,
        INVALID_STATE,
        WS_CONNECT_FAILED,
        INVALID_WIND_DIRECTION,
        INVALID_COUNTRY
    }
}
