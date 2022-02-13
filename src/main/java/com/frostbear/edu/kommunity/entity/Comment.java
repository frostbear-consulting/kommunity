package com.frostbear.edu.kommunity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @Column(nullable = false, name = "idComment")
    @Type(type = "pg-uuid")
    private UUID idComment;

    @Column(nullable = false, name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "author")
    private Account author;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id")
    private Thread thread;
}