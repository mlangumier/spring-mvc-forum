package fr.hb.mlang.trainingforum.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(name = "posted_at", nullable = false)
    private LocalDateTime postedAt;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "post")
    private Set<Reaction> reactions = new HashSet<>();

    /**
     * Default constructor
     */
    public Post() {
    }

    /**
     * Full constructor
     *
     * @param id
     * @param title
     * @param content
     * @param postedAt
     * @param author
     * @param reactions
     */
    public Post(String id, String title, String content, LocalDateTime postedAt, User author, Set<Reaction> reactions) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postedAt = postedAt;
        this.author = author;
        this.reactions = reactions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDateTime postedAt) {
        this.postedAt = postedAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(Set<Reaction> reactions) {
        this.reactions = reactions;
    }


    /**
     * @param reaction
     */
    public void addReaction(Reaction reaction) {
        if (!reactions.contains(reaction)) {
            reaction.setPost(this);
            reactions.add(reaction);
        }
    }

    /**
     * @param reaction
     */
    public void removeReaction(Reaction reaction) {
        reactions.remove(reaction);
        reaction.setPost(null);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Post post)) return false;
        return Objects.equals(getId(), post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
