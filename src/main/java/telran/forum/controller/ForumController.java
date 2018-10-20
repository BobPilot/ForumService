package telran.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.forum.domain.Post;
import telran.forum.dto.DatePeriodDto;
import telran.forum.dto.NewCommentDto;
import telran.forum.dto.NewPostDto;
import telran.forum.dto.PostUpdateDto;
import telran.forum.service.ForumService;
import telran.forum.api.Link;

@RestController
@RequestMapping(Link.FORUM)
public class ForumController {
	
	@Autowired
	ForumService service;
	
	@PostMapping(Link.POST)
	public Post addPost(@RequestBody NewPostDto newPost) {
		return service.addNewPost(newPost);
	}
	
	@GetMapping(Link.POST + "/{id}")
	public Post getPost(@PathVariable String id) {
		return service.getPost(id);
	}
	
	@DeleteMapping(Link.POST + "/{id}")
	public Post removePost(@PathVariable String id) {
		return service.removePost(id);
	}
	
	@PutMapping(Link.POST)
	public Post updatePost(@RequestBody PostUpdateDto postUpdateDto) {
		return service.updatePost(postUpdateDto);
	}
	
	@PutMapping(Link.POST + "/{id}" + Link.LIKE)
	public boolean addLike(@PathVariable String id) {
		return service.addLike(id);
	}
	
	@PutMapping(Link.POST + "/{id}" + Link.COMMENT)
	public Post addComment(@PathVariable String id, @RequestBody NewCommentDto newCommentDto) {
		return service.addComment(id, newCommentDto);
	}
	
	@PostMapping(Link.POST + Link.TAGS)
	public Iterable<Post> getPostsByTags(@RequestBody List<String> tags){
		return service.findByTags(tags);
	}
	
	@GetMapping(Link.POSTS + Link.AUTHOR + "/{author}")
	public Iterable<Post> getPostsByAuthor(String author){
		return service.findByAuthor(author);
	}
	
	@PostMapping(Link.POSTS + Link.PERIOD)
	public Iterable<Post> getPostsBetweenDate(@RequestBody DatePeriodDto periodDto){
		return service.findByDate(periodDto);
	}

}





