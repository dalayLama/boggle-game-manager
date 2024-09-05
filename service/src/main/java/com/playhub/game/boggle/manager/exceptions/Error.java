package com.playhub.game.boggle.manager.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
public enum Error {

    LETTERS_DICTIONARY_NOT_FOUND_BY_LOCALE,
    PLAYERS_ALREADY_ASSIGNED;

    private final String titleCode;

    private final String messageCode;

    private final String code;

    Error() {
        this.code = name();
        this.titleCode = titleCode(name().toLowerCase());
        this.messageCode = messageCode(name().toLowerCase());
    }

    Error(Error parent, String messageCode) {
        this.code = parent.code;
        this.titleCode = parent.titleCode;
        this.messageCode = messageCode(parent, messageCode);
    }

    Error(HttpStatusCode statusCode, String code, String titleCode, String messageCode) {
        this.code = code;
        this.titleCode = titleCode(titleCode);
        this.messageCode = messageCode(messageCode);
    }

    private String titleCode(String code) {
        return "%s.%s".formatted(code, "title");
    }

    private String messageCode(String code) {
        return "%s.%s".formatted(code, "message");
    }

    private String messageCode(Error parent, String messageCode) {
        return "%s.%s".formatted(parent.code.toLowerCase(), messageCode(messageCode));
    }

}
