package fr.hb.mlang.trainingforum.posts.controller;

import fr.hb.mlang.trainingforum.entity.Post;
import fr.hb.mlang.trainingforum.entity.User;
import fr.hb.mlang.trainingforum.posts.dto.NewPostDTO;
import fr.hb.mlang.trainingforum.repository.PostRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/post")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/new")
    public String displayPostForm(Model model) {
        model.addAttribute("post", new NewPostDTO());

        return "post-form";
    }

    @PostMapping("/new")
    public String processPostForm(@ModelAttribute("post") @Valid NewPostDTO postDTO, BindingResult bindingResult, Model model, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            return "post-form";
        }

        System.err.println("> POST: " + postDTO);
        System.err.println("> USER: " + user);

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setPostedAt(LocalDateTime.now());
        post.setAuthor(user);

        postRepository.save(post);

        return "redirect:/post";
    }
}
