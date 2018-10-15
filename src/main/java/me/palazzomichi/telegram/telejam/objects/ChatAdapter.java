package me.palazzomichi.telegram.telejam.objects;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * JSON Adapter for class {@link Chat}.
 *
 * @author Michi Palazzo
 */
public final class ChatAdapter implements JsonDeserializer<Chat>, JsonSerializer<Chat> {

  public static final ChatAdapter INSTANCE = new ChatAdapter();

  private ChatAdapter() {
  }

  @Override
  public Chat deserialize(JsonElement src, Type type, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject object = src.getAsJsonObject();
    String chatType = object.getAsJsonPrimitive(Chat.TYPE_FIELD).getAsString();
    switch (chatType) {
      case PrivateChat.TYPE:
        return context.deserialize(src, PrivateChat.class);
      case Group.TYPE:
        return context.deserialize(src, Group.class);
      case SuperGroup.TYPE:
        return context.deserialize(src, SuperGroup.class);
      case Channel.TYPE:
        return context.deserialize(src, Channel.class);
      default:
        throw new JsonParseException("Unknown chat: " + src);
    }
  }

  @Override
  public JsonElement serialize(Chat chat, Type type, JsonSerializationContext context) {
    return context.serialize(chat, chat.getClass());
  }

}
