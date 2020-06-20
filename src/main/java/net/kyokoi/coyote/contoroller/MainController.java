package net.kyokoi.coyote.contoroller;

import net.kyokoi.coyote.entity.Game;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/game")
public class MainController {
    @GetMapping("/{roomId}")
    public String game(@PathVariable("roomId") int roomId, Model model) {
        model.addAttribute("roomId", roomId);
        return "game";
    }
}
