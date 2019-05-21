package dev.dowell.website.markdown;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/markdown")
public class MarkdownController {

    private final MarkdownRepository markdownRepository;

    @Autowired
    public MarkdownController(MarkdownRepository markdownRepository) {
        this.markdownRepository = markdownRepository;
    }

    @PostMapping
    public ResponseEntity<String> postMarkdown(@RequestBody String markdown) {
        var markdownPost = MarkdownPost.builder()
                .content(markdown)
                .localDateTime(LocalDateTime.now(ZoneId.systemDefault()))
                .build();
        markdownRepository.save(markdownPost);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<MarkdownPost>> getMarkdown() {
        var markdownPosts = StreamSupport.stream(markdownRepository.findAll().spliterator(), false).collect(Collectors.toList());
        return ResponseEntity.ok(markdownPosts);
    }
}
