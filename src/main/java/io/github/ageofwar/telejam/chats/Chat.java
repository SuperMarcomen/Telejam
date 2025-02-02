package io.github.ageofwar.telejam.chats;

import com.google.gson.annotations.SerializedName;
import io.github.ageofwar.telejam.TelegramObject;

/**
 * This class represents a chat.
 *
 * @author Michi Palazzo
 */
public abstract class Chat implements TelegramObject {
  
  static final String ID_FIELD = "id";
  static final String TYPE_FIELD = "type";
  
  /**
   * Unique identifier for this chat.
   */
  @SerializedName(ID_FIELD)
  private final long id;
  
  
  public Chat(long id) {
    this.id = id;
  }
  
  
  /**
   * Returns the title of the chat.
   *
   * @return the title of the chat
   */
  public abstract String getTitle();
  
  /**
   * Getter for property {@link #id}.
   *
   * @return value for property {@link #id}
   */
  public long getId() {
    return id;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    
    if (!(obj instanceof Chat)) {
      return false;
    }
    
    Chat chat = (Chat) obj;
    return id == chat.getId();
  }
  
  @Override
  public int hashCode() {
    return Long.hashCode(id);
  }
  
}
