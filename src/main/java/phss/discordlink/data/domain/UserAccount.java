package phss.discordlink.data.domain;

import java.util.UUID;

public class UserAccount {

    UUID uuid;
    String discordId;

    public UserAccount(UUID uuid, String discordId) {
        this.uuid = uuid;
        this.discordId = discordId;
    }

    public UUID getUUID() {
        return uuid;
    }
    public String getUUIDAsString() {
        return uuid.toString();
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

}
