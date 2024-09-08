package com.playhub.game.boggle.manager.rest;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiPaths {

    public static final String V1 = "/api/v1";

    public static final String V1_GAMES = V1 + "/games";

    public static final String V1_NEXT_ROUND = V1 + "/games/{gameId}/next-round";

    public static final String V1_ADD_ANSWER = V1 + "/games/{gameId}/rounds/{roundNumber}/answers";
}
