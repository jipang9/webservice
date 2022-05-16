package com.jihwan.book.webservice.web;

import com.jihwan.book.webservice.config.auth.dto.SessionUser;
import com.jihwan.book.webservice.service.PostsService;
import com.jihwan.book.webservice.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts",postsService.findAllDesc());

        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user!=null){
            model.addAttribute("userName",user.getName());
        }
        return "index";
    }
    @GetMapping("posts/save")
    public String postsSave(){
        return "posts-save";
    } // index.mustache와 마찬가지로 /posts/save를 호출하면 posts-save.mustache를 호출하도록 메서드를 추가함.

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findByid(id);
        model.addAttribute("post",dto);
        return "posts-update";
    }



}
