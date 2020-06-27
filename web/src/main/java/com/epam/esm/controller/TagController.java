package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.TagsDto;
import com.epam.esm.service.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8070")
@RequestMapping(value = "/tags", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {
    private final ITagService tagService;

    /**
     * This method finds a tag in database by id
     *
     * @param id - id of tag
     * @return {link} TagDTO
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto findTagById(@PathVariable("id") final long id) {
        return tagService.findById(id);
    }

    /**
     * This method finds a tag in database by id
     *
     * @param id - id of tag
     * @return {link} TagDTO
     */
    @GetMapping(value = "/{id}/certificates")
    @ResponseStatus(HttpStatus.OK)
    public TagDto findTagByIdIncludeCertificates(@PathVariable("id") final long id) {
        return tagService.findTagByIdIncludeCertificates(id);
    }

    /**
     * This method finds a tag in database by part name
     *
     * @param allParams
     * @return {link} TagDTO
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public TagsDto findAllTags(@RequestParam Map<String, String> allParams) {
        return tagService.findAll(allParams);
    }

    /**
     * This method finds all tags include certificates
     *
     * @return {link} TagsDTO
     */
    @GetMapping(value = "/certificates")
    @ResponseStatus(HttpStatus.OK)
    public TagsDto findAllTagsIncludeCertificates() {
        return tagService.findAllTagsIncludeCertificates();
    }
    /**
     * THis method creates a new tag
     *
     * @param tagDto - the new tag to save
     * @return {link void}
     */
    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public TagDto createTag(@RequestBody(required = false) final TagDto tagDto) {
        return tagService.create(tagDto);
    }

    /**
     * This method removes a tag from database by id
     *
     * @param id - id of tag
     * @return {link void}
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeTagById(@PathVariable("id") final Long id) {
        tagService.delete(id);
    }
}
