package dev.dowell.website.markdown;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkdownRepository extends CrudRepository<MarkdownPost, Long> {}
