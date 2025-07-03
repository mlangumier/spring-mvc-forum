package fr.hb.mlang.trainingforum.posts.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewPostDTO {

    @NotBlank(message = "Use a title to preface your post")
    @Size(min = 5, message = "Your title is too short")
    @Size(max = 128, message = "Your title is too long")
    private String title;

    @NotBlank(message = "Your post must have some content in order to be submitted")
    @Lob
    private String content;

    /**
     * Empty constructor to prepare the form
     */
    public NewPostDTO() {
    }

    /**
     * Full constructor with the form's data
     *
     * @param title   Title of the post written by the user
     * @param content Content of the post written by the user
     */
    public NewPostDTO(String title, String content) {
        this.title = title;
        this.content = content;
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
}
