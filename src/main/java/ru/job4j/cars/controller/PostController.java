package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.dto.PostCreateDto;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.*;

import java.io.IOException;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private final BodyService bodyService;

    private final ModelCarService modelCarService;

    private final EngineService engineService;

    private final TransmissionService transmissionService;



    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("posts", postService.findAllOrderById());
        return "posts/list";
    }

    @GetMapping("/createPost")
    public String createPost(Model model) {
        model.addAttribute("modelCars", modelCarService.findAllOrderById());
        model.addAttribute("bodies", bodyService.findAllOrderById());
        model.addAttribute("engines", engineService.findAllOrderById());
        model.addAttribute("transmissions", transmissionService.findAllOrderById());
        return "posts/createPost";
    }

    @PostMapping("/createPost")
    public String save(@ModelAttribute PostCreateDto postDto,
                       @RequestParam MultipartFile fileDto, Model model,
                       @SessionAttribute User user) throws IOException {
        postDto.setUser(user);
        var postOptional = postService.create(postDto, new PhotoDto(fileDto.getOriginalFilename(),
                fileDto.getBytes()));
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Ошибка при добавлении объявления");
            return "errors/404";
        }
        model.addAttribute("message", "Объявление успешно добавлено");
        return "posts/success";
    }

    @GetMapping("/editPost")
    public String getById(Model model, @RequestParam("id") int id) {
        Optional<PostDto> postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление с указанным идентификатором не найдено");
            return "errors/404";
        }
        model.addAttribute("post", postOptional.get());
        return "posts/info";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        boolean isDeleted = postService.delete(id);
        if (!isDeleted) {
            model.addAttribute("message", "Объявление с указанным идентификатором не найдено");
            return "errors/404";
        }
        return "redirect:/posts";
    }
}
