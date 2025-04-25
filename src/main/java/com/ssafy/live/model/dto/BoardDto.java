package com.ssafy.live.model.dto;

import java.sql.Timestamp;

public class BoardDto {
    private int id;
    private int memberId;          // 작성자 FK (members.id)
    private String writerName;     // 작성자 이름 (조인해서 사용 가능)
    private String title;
    private String content;
    private Timestamp createdAt;

    public BoardDto() {}

    public BoardDto(int memberId, String title, String content) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

    // Getter & Setter

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public String getWriterName() { return writerName; }
    public void setWriterName(String writerName) { this.writerName = writerName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "BoardDto{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", writerName='" + writerName + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
