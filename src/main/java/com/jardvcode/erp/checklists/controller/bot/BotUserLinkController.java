package com.jardvcode.erp.checklists.controller.bot;

import com.jardvcode.erp.checklists.domain.PlatformEnum;
import com.jardvcode.erp.checklists.dto.bot.BotLinkStatusDTO;
import com.jardvcode.erp.checklists.service.bot.BotErpUserLinkService;
import com.jardvcode.erp.shared.domain.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "checklists/bot")
public final class BotUserLinkController {

    private final BotErpUserLinkService service;

    public BotUserLinkController(BotErpUserLinkService service) {
        this.service = service;
    }

    @PostMapping(value = "/{platform}/unlink")
    public ResponseEntity<ResponseWrapper<BotLinkStatusDTO>> unlink(@PathVariable PlatformEnum platform) {
        return ResponseWrapper.ok(new BotLinkStatusDTO(service.unlink(platform.toString())));
    }

    @GetMapping(value = "/{platform}/linked")
    public ResponseEntity<ResponseWrapper<BotLinkStatusDTO>> isLinked(@PathVariable PlatformEnum platform) {
        return ResponseWrapper.ok(new BotLinkStatusDTO(service.isLinked(platform.toString())));
    }

}
