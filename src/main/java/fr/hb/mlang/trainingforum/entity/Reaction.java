package fr.hb.mlang.trainingforum.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "reaction")
public class Reaction {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false)
  private String type;

  @ManyToOne
  @JoinColumn(name = "author_id", nullable = false)
  private User author;

  @ManyToOne
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;

  /**
   * Default constructor
   */
  public Reaction() {
  }

  /**
   * Full constructor
   *
   * @param id
   * @param type
   * @param author
   * @param post
   */
  public Reaction(String id, String type, User author, Post post) {
    this.id = id;
    this.type = type;
    this.author = author;
    this.post = post;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public Post getPost() {
    return post;
  }

  public void setPost(Post post) {
    this.post = post;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Reaction reaction)) {
      return false;
    }
    return Objects.equals(getId(), reaction.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }
}
