package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties(value = {"userId", "completionId"}, allowSetters = true)
public class Completion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long completionId;
    private long userId;
    private long id;
    private LocalDateTime completedAt;

    public Completion() {
    }

    public Completion(long userId, long id, LocalDateTime completedAt) {
        this.userId = userId;
        this.id = id;
        this.completedAt = completedAt;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
