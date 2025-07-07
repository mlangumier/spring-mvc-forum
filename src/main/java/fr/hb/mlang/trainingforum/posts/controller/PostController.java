package fr.hb.mlang.trainingforum.posts.controller;

import fr.hb.mlang.trainingforum.entity.Post;
import fr.hb.mlang.trainingforum.entity.User;
import fr.hb.mlang.trainingforum.posts.dto.NewPostDTO;
import fr.hb.mlang.trainingforum.repository.PostRepository;
import fr.hb.mlang.trainingforum.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
public class PostController {
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public PostController(PostRepository postRepository, UserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  @GetMapping("/")
  public String displayPosts(
      Model model,
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "5") int pageSize
  ) {
    // Get paginated posts & current page (easier to get that way)
    Page<Post> paginatedPosts = postRepository.findAll(PageRequest.of(
        page - 1,
        pageSize,
        Sort.by("postedAt").descending()
    ));

    // Use this custom method to display the paginated posts
    displayPaginatedPosts(model, paginatedPosts, page);

    return "index";
  }

  @GetMapping("/new-post")
  public String displayPostForm(Model model) {
    model.addAttribute("post", new NewPostDTO());

    return "post-form";
  }

  @PostMapping("/new-post")
  public String processPostForm(
      @ModelAttribute("post") @Valid NewPostDTO postDTO,
      BindingResult bindingResult,
      @AuthenticationPrincipal User user
  ) {
    if (bindingResult.hasErrors()) {
      return "post-form";
    }

    Post post = new Post();
    post.setTitle(postDTO.getTitle());
    post.setContent(postDTO.getContent());
    post.setPostedAt(LocalDateTime.now());
    post.setAuthor(user);

    postRepository.save(post);

    return "redirect:/";
  }

  @GetMapping("/author/{username}")
  public String displayPostsByAuthor(
      @PathVariable("username") String username,
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "5") int pageSize,
      Model model
  ) {
    Optional<User> foundAuthor = userRepository.findByUsername(username);

    if (foundAuthor.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    model.addAttribute("selectedUser", foundAuthor.get());

    Page<Post> paginatedPosts = postRepository.findByAuthor(
        foundAuthor.get(), PageRequest.of(
            page - 1,
            pageSize,
            Sort.by("postedAt").descending()
        )
    );

    displayPaginatedPosts(model, paginatedPosts, page);

    return "index";
  }

  /**
   * Sets up the information to be displayed on the posts result page, using the Model to send the paginated data to the template
   *
   * @param model          Model that sends the data to the Thymeleaf template
   * @param paginatedPosts Data regarding pagination and the list of posts
   * @param page           The current requested page result.
   */
  private void displayPaginatedPosts(Model model, Page<Post> paginatedPosts, int page) {
    model.addAttribute("paginatedPosts", paginatedPosts);
    model.addAttribute("currentPage", page);

    int totalPages = paginatedPosts.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
      model.addAttribute("pageNumbers", pageNumbers);
    }
  }
}
