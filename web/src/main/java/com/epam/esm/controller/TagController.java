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
     * This method finds all tags in database
     *
     * @return {link} TagsDTO
     */
    @GetMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    public TagsDto findAllTags() {
        return tagService.findAll();
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
        tagService.removeById(id);
    }

    /**
     * This method finds a tag in database by part name
     *
     * @param name - name of tag
     * @return {link} TagDTO
     */
    @GetMapping(value = "/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto findTagByName(@PathVariable("name") final String name) {
        return tagService.findTagByName(name);
    }

    /**
     * This method finds a tag in database by part name
     *
     * @param allParams - name or name's part of tag
     * @return {link} TagDTO
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public TagsDto findAllCertificates(@RequestParam Map<String, String> allParams) {
        return tagService.findTagByPartNAme(allParams);
    }
}
