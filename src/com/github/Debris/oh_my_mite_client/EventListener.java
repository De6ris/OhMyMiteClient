package com.github.Debris.oh_my_mite_client;

import com.google.common.eventbus.Subscribe;
import net.xiaoyu233.fml.reload.event.PlayerLoggedInEvent;

import static com.github.Debris.oh_my_mite_client.OhMyMiteClient.MOD_ID;

public class EventListener {
    public EventListener() {
    }

    @Subscribe
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        String message = String.format("§f[Debris]:§r§b%s 已加载§r", MOD_ID);
        event.getPlayer().addChatMessage(message);
    }
}
