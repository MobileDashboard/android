package com.ondrejd.mobilninastenka;

/**
 * Class that represents single dashboard message.
 */
public class DashboardMessage {
    public static int PRIORITY_EXTRA = 3;
    public static int PRIORITY_LOW = 1;
    public static int PRIORITY_NORMAL = 2;

    private String boardHash;
    private String boardName;
    private int id;
    private String title;
    private String content;
    //Timestamp with target data
    private int timestamp;
    private String link;
    private int priority;
    private boolean deleted;
    //Timestamp when message will expire.
    private int expiration;
    private String author;
    //Timestamp when was message sent.
    private int sent;

    /**
     * Construct dashboard message from a JSON string.
     * @param json JSON from which should be message constructed.
     */
    DashboardMessage(String json) {
        //TODO Finish this!
    }

    /**
     * Construct dashboard message from given parameters
     * @param boardHash Hash of message's board.
     * @param boardName Name of message's board
     * @param id Numeric identifier of the message
     * @param title Title of the message.
     * @param content Content of the message.
     * @param timestamp Timestamp when...
     * @param link Link attached to the message.
     * @param priority Message's priority.
     * @param deleted TRUE if message is marked as deleted.
     * @param expiration Timestamp when the message will expire.
     * @param author Author of the message.
     * @param sent Timestamp when was message sent.
     */
    DashboardMessage(String boardHash, String boardName, int id, String title, String content,
                     int timestamp, String link, int priority, boolean deleted, int expiration,
                     String author, int sent) {
        this.boardHash = boardHash;
        this.boardName = boardName;
        this.id = id;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
        this.link = link;
        this.priority = priority;
        this.deleted = deleted;
        this.expiration = expiration;
        this.author = author;
        this.sent = sent;
    }

    public String getBoardHash() { return boardHash; }
    public String getBoardName() { return boardName; }
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getTimestamp() { return timestamp; }
    public String getLink() { return link; }
    public int getPriority() { return priority; }
    public boolean getDeleted() { return deleted; }
    public int getExpiration() { return expiration; }
    public String getAuthor() { return author; }
    public int getSent() { return sent; }
}
