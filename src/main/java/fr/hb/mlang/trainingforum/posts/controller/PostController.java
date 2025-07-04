package fr.hb.mlang.trainingforum.posts.controller;

import fr.hb.mlang.trainingforum.entity.Post;
import fr.hb.mlang.trainingforum.entity.User;
import fr.hb.mlang.trainingforum.posts.dto.NewPostDTO;
import fr.hb.mlang.trainingforum.repository.PostRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/posts")
public class PostController {

  private final PostRepository postRepository;

  public PostController(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @GetMapping
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
    model.addAttribute("paginatedPosts", paginatedPosts);
    model.addAttribute("currentPage", page);

    // Get a list of pages to create or pagination
    int totalPages = paginatedPosts.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
      model.addAttribute("pageNumbers", pageNumbers);
    }

    return "posts/results";
  }

  @GetMapping("/new")
  public String displayPostForm(Model model) {
    model.addAttribute("post", new NewPostDTO());

    return "posts/new";
  }

  @PostMapping("/new")
  public String processPostForm(
      @ModelAttribute("post") @Valid NewPostDTO postDTO,
      BindingResult bindingResult,
      @AuthenticationPrincipal User user
  ) {
    if (bindingResult.hasErrors()) {
      return "posts/new";
    }

    Post post = new Post();
    post.setTitle(postDTO.getTitle());
    post.setContent(postDTO.getContent());
    post.setPostedAt(LocalDateTime.now());
    post.setAuthor(user);

    postRepository.save(post);

    return "redirect:/posts";
  }
}
