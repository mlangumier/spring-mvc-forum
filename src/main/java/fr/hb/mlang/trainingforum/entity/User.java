package fr.hb.mlang.trainingforum.entity;

import fr.hb.mlang.trainingforum.enums.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "user_table")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "author")
  private Set<Post> posts = new HashSet<>();

  //INFO: We don't need to get the user's reactions, and the @ManyToOne is the generator of this relation.
//    @OneToMany(mappedBy = "author")
//    private Set<Reaction> reactions = new HashSet<>();

  /**
   * Default constructor
   */
  public User() {
  }

  /**
   * Full constructor
   *
   * @param id       UUID identifier of the entity
   * @param username Public name of the user
   * @param password Password of the user
   */
  public User(String id, String username, String password, Role role) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.getLabel()));
  }

  public Set<Post> getPosts() {
    return posts;
  }

  public void setPosts(Set<Post> posts) {
    this.posts = posts;
  }

  /**
   * Adds a {@link Post} to this user's list of posts.
   *
   * @param post
   */
  public void addPost(Post post) {
    if (!posts.contains(post)) {
      post.setAuthor(this);
      this.posts.add(post);
    }
  }

  /**
   * Removes a {@link Post} from this user's list of posts.
   *
   * @param post
   */
  public void removePost(Post post) {
    this.posts.remove(post);
    post.setAuthor(null);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof User user)) {
      return false;
    }
    return Objects.equals(getId(), user.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }
}
