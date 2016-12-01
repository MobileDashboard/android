package com.ondrejd.mobilninastenka;

/**
 * Class that represents single dashboard message.
 */
public class DashboardMessage {
    public static String PRIORITY_EXTRA = "extra";
    public static String PRIORITY_LOW = "low";
    public static String PRIORITY_NORMAL = "normal";

    private String title;
    private String text;
    private String priority;
    private String author;
    private String created;
    private String updated;

    /**
     * Construct blank dashboard message.
     */
    DashboardMessage() {
        title = "";
        text = "";
        priority = PRIORITY_NORMAL;
        author = "";
        created = "";
        updated = "";
    }

    /**
     * Construct dashboard message from a JSON string.
     * @param json JSON from which should be message constructed.
     */
    DashboardMessage(String json) {
        //TODO Process given JSON string into the dashboard message.
    }

    /**
     * Construct dashboard message from given parameters
     * @param title Title of the message.
     * @param text Text of the message.
     * @param priority Message's priority.
     * @param author Author of the message.
     * @param created Datetime when was message created.
     * @param updated Datetime when was message updated.
     */
    DashboardMessage(String title, String text, String priority, String author, String created, String updated) {
        this.title = title;
        this.text = text;
        this.priority = priority;
        this.author = author;
        this.created = created;
        this.updated = updated;
    }

    public String getTitle() { return title; }
    public void setTitle(String val) { title = val; }

    public String getText() { return text; }
    public void setText(String val) { text = val; }

    public String getPriority() { return priority; }
    public void setPriority(String val) {
        if (val == PRIORITY_EXTRA || val == PRIORITY_LOW || val == PRIORITY_NORMAL) {
            priority = val;
        } else {
            priority = PRIORITY_NORMAL;
        }
    }

    public String getAuthor() { return author; }
    public void setAuthor(String val) { author = val; }

    public String getCreated() { return created; }
    public void setCreated(String val) { created = val; }

    public String getUpdated() { return updated; }
    public void setUpdated(String val) { updated = val; }
}
