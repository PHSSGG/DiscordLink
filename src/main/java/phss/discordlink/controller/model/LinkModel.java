package phss.discordlink.controller.model;

import java.util.UUID;

public class LinkModel {

    String playerName;
    UUID uuid;
    String code;

    public LinkModel(String playerName, UUID uuid, String code) {
        this.playerName = playerName;
        this.uuid = uuid;
        this.code = code;
    }

    public String getPlayerName() {
        return playerName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getCode() {
        return code;
    }

}
