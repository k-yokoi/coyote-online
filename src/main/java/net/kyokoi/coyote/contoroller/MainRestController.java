package net.kyokoi.coyote.contoroller;

import net.kyokoi.coyote.entity.Game;
import net.kyokoi.coyote.entity.GameDTO;
import net.kyokoi.coyote.entity.User;
import net.kyokoi.coyote.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MainRestController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    GameCreateService gameCreateService;

    @Autowired
    JoinGameService joinGameService;

    @Autowired
    GameInfoService gameInfoService;

    @Autowired
    GameMasterService gameMasterService;

    @Autowired
    RaiseGameService raiseGameService;

    @Autowired
    CoyoteGameService coyoteGameService;

    @GetMapping("/create")
    public Game createGame(@RequestParam(value = "name") String name) {
        return gameCreateService.createGame(name);
    }

    @GetMapping("/join")
    public UUID joinGame(@RequestParam("roomId") int roomId, @RequestParam(value = "name") String name) {
        return joinGameService.joinGame(roomId, name);
    }

    @GetMapping("/start")
    public boolean startGame(@RequestParam("roomId") int roomId, @RequestParam(value = "token") UUID token) {
        return gameMasterService.startGame(roomId, token);
    }

    @GetMapping("/restart")
    public boolean restartGame(@RequestParam("roomId") int roomId, @RequestParam(value = "token") UUID token) {
        return gameMasterService.restartGame(roomId, token);
    }

    @GetMapping("/raise")
    public boolean raise(@RequestParam("roomId") int roomId, @RequestParam(value = "value") int value, @RequestParam(value = "token") UUID token) {
        return raiseGameService.raise(roomId, value, token);
    }

    @GetMapping("/coyote")
    public User coyote(@RequestParam("roomId") int roomId, @RequestParam(value = "token") UUID token) {
        return coyoteGameService.coyote(roomId, token);
    }

    @GetMapping("/gameinfo")
    public GameDTO getGameInfo(@RequestParam("roomId") int roomId, @RequestParam(value = "token") UUID token) {
        return gameInfoService.getGameInfo(roomId, token);
    }

    @GetMapping("/ver/{roomId}")
    public Integer getGameInfo(@PathVariable("roomId") int roomId) {
        return gameInfoService.getVersion(roomId);
    }


}