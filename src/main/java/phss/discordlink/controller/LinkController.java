package phss.discordlink.controller;

import org.bukkit.entity.Player;
import phss.discordlink.controller.model.LinkModel;
import phss.discordlink.utils.WeakConcurrentHashMap;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class LinkController {

    public WeakConcurrentHashMap<String, LinkModel> generatedCodes = new WeakConcurrentHashMap<>(TimeUnit.MINUTES.toMillis(5));

    public Optional<LinkModel> getLinkModel(String code) {
        return Optional.ofNullable(generatedCodes.get(code));
    }
    public Optional<LinkModel> getLinkModel(UUID uuid) {
        return generatedCodes.values().stream().filter(model -> model.getUuid().equals(uuid)).findFirst();
    }

    public LinkModel create(Player player) {
        LinkModel model = new LinkModel(player.getName(), player.getUniqueId(), generateCode());
        generatedCodes.put(model.getCode(), model);

        return model;
    }

    public void remove(LinkModel model) {
        generatedCodes.remove(model.getCode());
    }

    private String generateCode() {
        StringBuilder builder = new StringBuilder();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (int i = 0; i < 10; i++) {
            double index = Math.random() * chars.length();
            builder.append(chars.charAt((int) index));
        }

        return builder.toString();
    }

}
