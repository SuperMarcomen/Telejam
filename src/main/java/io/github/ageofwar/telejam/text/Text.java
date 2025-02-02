package io.github.ageofwar.telejam.text;

import io.github.ageofwar.telejam.messages.Message;
import io.github.ageofwar.telejam.messages.MessageEntity;
import io.github.ageofwar.telejam.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

/**
 * Class representing a text with entities.
 *
 * @author Michi Palazzo
 * @see TextBuilder
 */
public final class Text implements CharSequence {
  
  public static final Text EMPTY = new Text("");
  
  private final String text;
  private final List<MessageEntity> entities;
  
  /**
   * Converts an HTML String into a Text.
   * The following tags are currently supported:
   * <pre>
   * &lt;b&gt;bold&lt;/b&gt;, &lt;strong&gt;bold&lt;/strong&gt;
   * &lt;i&gt;italic&lt;/i&gt;, &lt;em&gt;italic&lt;/em&gt;
   * &lt;a href="http://www.example.com/"&gt;inline URL&lt;/a&gt;
   * &lt;a href="tg://user?id=123456789"&gt;inline mention of a user&lt;/a&gt;
   * &lt;code&gt;inline fixed-width code&lt;/code&gt;
   * &lt;pre&gt;pre-formatted fixed-width code block&lt;/pre&gt;
   * </pre>
   * All &lt;, &gt; and &amp; symbols that are not a part of a tag or an HTML
   * entity must be replaced with the corresponding HTML entities
   * (&lt; with &amp;lt;, &gt; with &amp;gt; and &amp; with &amp;amp;)
   *
   * @param text the string to convert
   * @return the parsed text
   * @throws TextParseException if an error occurs when parsing the string
   */
  public static Text parseHtml(String text) throws TextParseException {
    if (text == null) {
      return null;
    }
    return Html.parseText(text);
  }
  
  /**
   * Converts an Markdown String into a Text.
   * Use the following syntax in your message:
   * <pre>
   * *bold text*
   * _italic text_
   * [inline URL](http://www.example.com/)
   * [inline mention of a user](tg://user?id=123456789)
   * `inline fixed-width code`
   * `pre-formatted fixed-width
   * code block`
   * </pre>
   * All Markdown symbols that are not a part of a Markdown
   * entity must be escaped with the <code>\</code> character.
   *
   * @param text the string to convert
   * @return the parsed text
   * @throws TextParseException if an error occurs when parsing the string
   */
  public static Text parseMarkdown(String text) throws TextParseException {
    if (text == null) {
      return null;
    }
    return Markdown.parseText(text);
  }
  
  /**
   * Creates a bold text.
   *
   * @param text the bold text
   * @return the created text
   */
  public static Text bold(String text) {
    return new TextBuilder().appendBold(text).build();
  }
  
  /**
   * Creates an italic text.
   *
   * @param text the italic text
   * @return the created text
   */
  public static Text italic(String text) {
    return new TextBuilder().appendItalic(text).build();
  }
  
  /**
   * Creates a code.
   *
   * @param text the code
   * @return the created text
   */
  public static Text code(String text) {
    return new TextBuilder().appendCode(text).build();
  }
  
  /**
   * Creates a code block.
   *
   * @param text the code block
   * @return the created text
   */
  public static Text codeBlock(String text) {
    return new TextBuilder().appendCodeBlock(text).build();
  }
  
  /**
   * Creates a link.
   *
   * @param link the link
   * @return the created text
   */
  public static Text link(Link link) {
    return new TextBuilder().appendLink(link).build();
  }
  
  /**
   * Creates a link.
   *
   * @param text the text of the link
   * @param url  the url of the link
   * @return the created text
   */
  public static Text link(String text, String url) {
    return new TextBuilder().appendLink(text, url).build();
  }
  
  /**
   * Creates a link.
   *
   * @param text    the text of the link
   * @param message the message to link
   * @return the created text
   */
  public static Text link(String text, Message message) {
    return new TextBuilder().appendLink(text, message).build();
  }
  
  /**
   * Creates an url.
   *
   * @param url the url
   * @return the created text
   */
  public static Text url(String url) {
    return new TextBuilder().appendUrl(url).build();
  }
  
  /**
   * Creates an url.
   *
   * @param message the message to link
   * @return the created text
   */
  public static Text url(Message message) {
    return new TextBuilder().appendUrl(message).build();
  }
  
  /**
   * Creates an email.
   *
   * @param email the email
   * @return the created text
   */
  public static Text email(String email) {
    return new TextBuilder().appendEmail(email).build();
  }
  
  /**
   * Creates an hashtag.
   *
   * @param hashtag the hashtag
   * @return the created text
   */
  public static Text hashtag(String hashtag) {
    return new TextBuilder().appendHashtag(hashtag).build();
  }
  
  /**
   * Creates a mention.
   *
   * @param mention the mentioned user
   * @return the created text
   */
  public static Text mention(User mention) {
    return new TextBuilder().appendMention(mention).build();
  }
  
  /**
   * Creates a mention.
   *
   * @param mention the mentioned username
   * @return the created text
   */
  public static Text mention(String mention) {
    return new TextBuilder().appendMention(mention).build();
  }
  
  /**
   * Creates a text mention.
   *
   * @param mention the mention
   * @return the created text
   */
  public static Text textMention(Mention mention) {
    return new TextBuilder().appendTextMention(mention).build();
  }
  
  /**
   * Creates a text mention.
   *
   * @param text    the text of the mention
   * @param mention the mentioned user
   * @return the created text
   */
  public static Text textMention(String text, User mention) {
    return new TextBuilder().appendTextMention(text, mention).build();
  }
  
  /**
   * Creates a text mention.
   *
   * @param mention the mentioned user
   * @return the created text
   */
  public static Text textMention(User mention) {
    return new TextBuilder().appendTextMention(mention).build();
  }
  
  /**
   * Creates a bot command.
   *
   * @param botCommand the bot command
   * @return the created text
   */
  public static Text botCommand(String botCommand) {
    return new TextBuilder().appendBotCommand(botCommand).build();
  }
  
  /**
   * Creates a phone number.
   *
   * @param phoneNumber the phone number
   * @return the created text
   */
  public static Text phoneNumber(String phoneNumber) {
    return new TextBuilder().appendPhoneNumber(phoneNumber).build();
  }
  
  /**
   * Creates a cashtag.
   *
   * @param cashtag the cashtag
   * @return the created text
   */
  public static Text cashtag(String cashtag) {
    return new TextBuilder().appendCashtag(cashtag).build();
  }
  
  
  /**
   * Constructs a text.
   *
   * @param text     the text
   * @param entities text entities
   * @see TextBuilder
   */
  public Text(String text, List<MessageEntity> entities) {
    this.text = text;
    this.entities = entities != null ? unmodifiableList(entities) : emptyList();
  }
  
  /**
   * Constructs a text.
   *
   * @param text the text
   * @see TextBuilder
   */
  public Text(String text) {
    this(text, emptyList());
  }
  
  
  /**
   * Returns {@code true} if, and only if, {@link #text} length is {@code 0}.
   *
   * @return {@code true} if {@link #text} length is {@code 0}, otherwise {@code false}
   */
  public boolean isEmpty() {
    return text.isEmpty();
  }
  
  /**
   * Concatenates the specified text to the end of this text.
   *
   * @param other the text that is concatenated to the end of this text.
   * @return a text that represents the concatenation of this object to the other.
   */
  public Text concat(Text other) {
    if (other.isEmpty()) return this;
    if (isEmpty()) return other;
    return new TextBuilder()
        .append(this)
        .append(other)
        .build();
  }
  
  /**
   * Returns a list containing all the bold strings in this text.
   *
   * @return a list containing all the bold strings in this text
   */
  public List<String> getBoldText() {
    return getEntities(MessageEntity.Type.BOLD);
  }
  
  /**
   * Returns a list containing all the italic strings in this text.
   *
   * @return a list containing all the italic strings in this text
   */
  public List<String> getItalicText() {
    return getEntities(MessageEntity.Type.ITALIC);
  }
  
  /**
   * Returns a list containing all the codes in this text.
   *
   * @return a list containing all the codes in this text
   */
  public List<String> getCodeText() {
    return getEntities(MessageEntity.Type.CODE);
  }
  
  /**
   * Returns a list containing all the code blocks in this text.
   *
   * @return a list containing all the code blocks in this text
   */
  public List<String> getCodeBlocks() {
    return getEntities(MessageEntity.Type.CODE_BLOCK);
  }
  
  /**
   * Returns a list containing all the links in this text with their relative urls.
   *
   * @return a list containing all the links in this text with their relative urls
   */
  public List<Link> getLinks() {
    return getEntities(MessageEntity.Type.LINK, entity -> new Link(
        text.substring(entity.getOffset(), entity.getOffset() + entity.getLength()),
        entity.getUrl().orElseThrow(AssertionError::new)
    ));
  }
  
  /**
   * Returns a list containing all the urls in this text.
   *
   * @return a list containing all the urls in this text
   */
  public List<String> getUrls() {
    return getEntities(MessageEntity.Type.URL);
  }
  
  /**
   * Returns a list containing all the emails in this text.
   *
   * @return a list containing all the emails in this text
   */
  public List<String> getEmails() {
    return getEntities(MessageEntity.Type.EMAIL);
  }
  
  /**
   * Returns a list containing all the hashtags in this text.
   *
   * @return a list containing all the hashtags in this text
   */
  public List<String> getHashtags() {
    return getEntities(
        MessageEntity.Type.HASHTAG,
        entity -> text.substring(entity.getOffset() + 1, entity.getOffset() + entity.getLength())
    );
  }
  
  /**
   * Returns a list containing all the mentions in this text.
   *
   * @return a list containing all the mentions in this text
   */
  public List<String> getMentions() {
    return getEntities(
        MessageEntity.Type.MENTION,
        entity -> text.substring(entity.getOffset() + 1, entity.getOffset() + entity.getLength())
    );
  }
  
  /**
   * Returns a list containing all the text mentions in this text with their relative users.
   *
   * @return a list containing all the text mentions in this text with their relative users
   */
  public List<Mention> getTextMentions() {
    return getEntities(MessageEntity.Type.TEXT_MENTION, entity -> new Mention(
        text.substring(entity.getOffset(), entity.getOffset() + entity.getLength()),
        entity.getUser().orElseThrow(AssertionError::new)
    ));
  }
  
  /**
   * Returns a list containing all the bot commands in this text.
   *
   * @return a list containing all the bot commands in this text
   */
  public List<String> getBotCommands() {
    return getEntities(
        MessageEntity.Type.BOT_COMMAND,
        entity -> text.substring(entity.getOffset() + 1, entity.getOffset() + entity.getLength())
    );
  }
  
  /**
   * Returns a list containing all the phone numbers in this text.
   *
   * @return a list containing all the phone numbers in this text
   */
  public List<String> getPhoneNumbers() {
    return getEntities(MessageEntity.Type.PHONE_NUMBER);
  }
  
  /**
   * Returns a list containing all the cashtags in this text.
   *
   * @return a list containing all the cashtags in this text
   */
  public List<String> getCashtags() {
    return getEntities(
        MessageEntity.Type.CASHTAG,
        entity -> text.substring(entity.getOffset() + 1, entity.getOffset() + entity.getLength())
    );
  }
  
  private <T> List<T> getEntities(MessageEntity.Type type, Function<MessageEntity, T> mapper) {
    return entities.stream()
        .filter(e -> e.getType() == type)
        .map(mapper)
        .collect(Collectors.toList());
  }
  
  private List<String> getEntities(MessageEntity.Type type) {
    return getEntities(
        type,
        entity -> text.substring(entity.getOffset(), entity.getOffset() + entity.getLength())
    );
  }
  
  /**
   * Return this text in HTML format.
   *
   * @return this text in HTML format
   */
  public String toHtmlString() {
    return Html.toString(this);
  }
  
  /**
   * Return this text in Markdown format.
   *
   * @return this text in Markdown format
   */
  public String toMarkdownString() {
    return Markdown.toString(this);
  }
  
  public Text trim() {
    int len = length();
    int start = 0;
    int end = len - 1;
    for (; start < len; start++) {
      if (charAt(start) != ' ') break;
    }
    if (start < len) {
      for (; end >= 0; end--) {
        if (charAt(end) != ' ') break;
      }
    }
    return subSequence(start, end + 1);
  }
  
  @Override
  public int length() {
    return text.length();
  }
  
  @Override
  public char charAt(int index) {
    return text.charAt(index);
  }
  
  @Override
  public Text subSequence(int start, int end) {
    if (start == 0 && end == text.length())
      return this;
    if (start == end)
      return EMPTY;
    String text = this.text.substring(start, end);
    
    List<MessageEntity> entities = new ArrayList<>();
    for (MessageEntity entity : this.entities) {
      int offset = entity.getOffset();
      int length = entity.getLength();
      if (offset >= end) {
        break;
      } else if (offset + length > start) {
        int oldEnd = offset + length;
        int newOffset = offset > start ? offset - start : 0;
        int newEnd = oldEnd < end ? oldEnd - start : end - start;
        entities.add(entity.move(newOffset, newEnd - newOffset));
      }
    }
    return new Text(text, entities);
  }
  
  public Text subSequence(int start) {
    return subSequence(start, length());
  }
  
  @Override
  public String toString() {
    return text;
  }
  
  /**
   * Returns the text entities.
   *
   * @return the text entities
   */
  public List<MessageEntity> getEntities() {
    return unmodifiableList(entities);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Text)) {
      return false;
    }
    Text text = (Text) obj;
    return this.text.equals(text.text) && entities.equals(text.entities);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(text, entities);
  }
  
}
