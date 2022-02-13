package com.frostbear.edu.kommunity.service;

import com.frostbear.edu.kommunity.entity.Comment;
import com.frostbear.edu.kommunity.entity.Thread;
import com.frostbear.edu.kommunity.exception.NotFoundException;
import com.frostbear.edu.kommunity.repo.AccountRepository;
import com.frostbear.edu.kommunity.repo.CommentRepository;
import com.frostbear.edu.kommunity.repo.ThreadRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ThreadService {

    private final ThreadRepository repo;
    private final AccountRepository accountRepository;
    private final CommentRepository commentRepository;

    /**
     * Creates a new {@link Thread}
     *
     * @param idAccount ID of the owning account
     * @param title     Title of the thread
     * @param text      Body text
     * @return New thread
     */
    @Transactional
    public Thread create(UUID idAccount, String title, String text) {
        var owner = this.accountRepository.findById(idAccount)
                .orElseThrow(() -> new NotFoundException("Unknown owner for ID " + idAccount.toString()));

        var thread = new Thread(
                UUID.randomUUID(),
                title,
                text,
                owner,
                LocalDateTime.now(),
                List.of()
        );

        this.repo.saveAndFlush(thread);
        return thread;
    }

    /**
     * Returns a page of {@link Thread threads}
     *
     * @param pageable Paging information
     * @return Page
     */
    public Page<Thread> getPage(Pageable pageable) {
        if (!pageable.getSort().isSorted()) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(Sort.Direction.DESC, "createdAt")
            );
        }

        return this.repo.findAll(pageable);
    }

    /**
     * Returns the {@link Thread} for the given ID with its comments
     *
     * @param idThread ID of the thread
     * @return Thread
     * @throws NotFoundException If the thread does not exist
     */
    @Transactional
    public Thread forId(@NotNull UUID idThread) {
        var thread = this.repo.findById(idThread).orElseThrow(() -> new NotFoundException("Unknown thread"));

        Hibernate.initialize(thread.getComments());

        return thread;
    }

    /**
     * Creates a new comment on the referenced thread
     *
     * @param idThread  ID of the thread
     * @param idAccount ID of the owner
     * @param comment   Comment text
     * @throws NotFoundException if the account or thread do not exist
     */
    @Transactional
    public void createComment(@NotNull UUID idThread, @NotNull UUID idAccount, @NotNull String comment) {
        var thread = this.forId(idThread);
        var owner = this.accountRepository.findById(idAccount)
                .orElseThrow(() -> new NotFoundException("Unknown author"));

        var com = new Comment(
                UUID.randomUUID(),
                comment,
                owner,
                LocalDateTime.now(),
                thread
        );

        this.commentRepository.saveAndFlush(com);
    }

}