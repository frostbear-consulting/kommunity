package com.frostbear.edu.kommunity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "thread")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Thread {

    @Id
    @Column(nullable = false, name = "idThread")
    @Type(type = "pg-uuid")
    private UUID idThread;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "author")
    private Account author;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id")
    @OrderBy("createdAt asc")
    List<Comment> comments;
}