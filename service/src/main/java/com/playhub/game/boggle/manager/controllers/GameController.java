package com.playhub.game.boggle.manager.controllers;

import com.playhub.game.boggle.manager.dto.GameDto;
import com.playhub.game.boggle.manager.dto.NewGameRequestDto;
import com.playhub.game.boggle.manager.dto.RoundAnswerDto;
import com.playhub.game.boggle.manager.dto.RoundDto;
import com.playhub.game.boggle.manager.mappers.GameDtoMapper;
import com.playhub.game.boggle.manager.models.Game;
import com.playhub.game.boggle.manager.models.NewGameRequest;
import com.playhub.game.boggle.manager.models.Round;
import com.playhub.game.boggle.manager.rest.ApiPaths;
import com.playhub.game.boggle.manager.services.GameService;
import com.playhub.security.rest.PlayHubUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    private final GameDtoMapper mapper;

    @PostMapping(ApiPaths.V1_GAMES)
    public ResponseEntity<GameDto> createGame(@AuthenticationPrincipal PlayHubUser user,
                                              @Valid @RequestBody NewGameRequestDto dto) {
        NewGameRequest request = mapper.toNewGameRequest(dto, user.getId());
        Game game = gameService.createGame(request);
        GameDto gameDto = mapper.toGameDto(game);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(gameDto.id())
                .toUri();
        return ResponseEntity.created(uri).body(gameDto);
    }

    @PostMapping(ApiPaths.V1_NEXT_ROUND)
    public ResponseEntity<RoundDto> nextRound(@PathVariable(name = "gameId") UUID gameId) {
        Round round = gameService.startNextRound(gameId);
        RoundDto dto = mapper.toRoundDto(round);
        return ResponseEntity.ok(dto);
    }

    @PostMapping(ApiPaths.V1_ADD_ANSWER)
    public ResponseEntity<Void> addAnswer(@AuthenticationPrincipal PlayHubUser user,
                                          @PathVariable("gameId") UUID gameId,
                                          @PathVariable("roundNumber") int roundNumber,
                                          @RequestBody @Valid RoundAnswerDto dto) {
        gameService.addAnswer(gameId, roundNumber, user.getId(), dto.answer());
        return ResponseEntity.noContent().build();
    }

}
