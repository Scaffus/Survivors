package com.scaffus.survivors;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

import java.util.List;

public class TextComponentBuilder {

    public static TextComponent createTextComponent(final String message) {
        return new TextComponent(TextComponent.fromLegacyText(message));
    }

    public static TextComponent createTextComponentUrlCLick(final String message, final String url) {
        TextComponent text = createTextComponent(message);

        text.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        return text;
    }

    public static TextComponent createTextComponentHoverMessage(final String message, final List<String> hoverListMessages) {
        TextComponent textComponent = createTextComponent(message);
        final StringBuilder stringBuilder = new StringBuilder();
        final int sizeMessages = hoverListMessages.size();

        for (int i = 0; i < sizeMessages; ++i) {
            stringBuilder.append(hoverListMessages.get(i));
            if (i + 1 < sizeMessages)
                stringBuilder.append('\n');
        }
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(stringBuilder.toString())));
        return textComponent;
    }

    public static TextComponent createTextComponentHoverMessageUrlClick(final String message, final List<String> hoverListMessages, final String url) {
        TextComponent messageWithHover = createTextComponentHoverMessage(message, hoverListMessages);

        messageWithHover.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        return messageWithHover;
    }

    public static TextComponent concatTextComponents(TextComponent ... textComponents) {
        TextComponent finalMessage = new TextComponent();

        for (TextComponent textComponent : textComponents) {
            finalMessage.addExtra(textComponent);
        }
        return finalMessage;
    }

//    public static void broadcastMessage(final String message) {
//        ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText(message));
//    }
//
//    public static void broadcastTitle(@NonNull final String title, @Nullable final String subtitle) {
//        final Title toSend = ProxyServer.getInstance().createTitle();
//        toSend.title(TextComponentBuilder.createTextComponent(title));
//        toSend.subTitle(TextComponentBuilder.createTextComponent(subtitle));
//
//        for (final ProxiedPlayer player : BungeeCord.getInstance().getPlayers()) {
//            toSend.send(player);
//        }
//    }

}
