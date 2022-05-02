package com.zemoso.springboot.demo.project.controller;

import com.zemoso.springboot.demo.project.entity.Anime;
import com.zemoso.springboot.demo.project.service.AnimeService;
import com.zemoso.springboot.demo.project.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class WatchListController {


    private WatchListService watchListService;




    @Autowired
    public WatchListController(WatchListService theWatchListService) {
        watchListService = theWatchListService;
    }

    @GetMapping("/hello")
    public String hello(@CurrentSecurityContext(expression="authentication?.name")
                        String username) {
        //Users theUser = userService.findByUserame(username);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);

        return "Hello, " + username + "!";

    }

    @GetMapping("/anime-weeb/admin/watch-list")
    public String showWatchList(Model theModel2, @CurrentSecurityContext(expression="authentication?.name") String username) {
        // get anime from db
        List<Anime> theAnime2 = watchListService.getWatchListByUser(username);
        // add to the spring model
        theModel2.addAttribute("watchList", theAnime2);
//        System.out.println("\n watch list \n");
//        System.out.println(theAnime2);
//        System.out.println("\n");
        return "anime/watch-list";
    }


    @GetMapping("/anime-weeb/admin/w-delete")
    public String delete(@RequestParam("watchListId") int theId,  @CurrentSecurityContext(expression="authentication?.name") String username) {

        watchListService.removeFromWatchList(username,theId);

        return "redirect:/anime-weeb/admin/watch-list";

    }

    @GetMapping("/anime-weeb/admin/add-to-watch-list")
    public String add(@RequestParam("watchListId") int theId,  @CurrentSecurityContext(expression="authentication?.name") String username) {

        watchListService.addToWatchList(username,theId);

        return "redirect:/anime-weeb/admin/anime-list";

    }


    @GetMapping("/anime-weeb/user/watch-list")
    public String showWatchListUserView(Model theModel2, @CurrentSecurityContext(expression="authentication?.name") String username) {
        // get anime from db
        List<Anime> theAnime2 = watchListService.getWatchListByUser(username);
        // add to the spring model
        theModel2.addAttribute("watchList", theAnime2);
//        System.out.println("\n watch list \n");
//        System.out.println(theAnime2);
//        System.out.println("\n");
        return "anime/watch-list-user";
    }


    @GetMapping("/anime-weeb/user/w-delete")
    public String deleteUserView(@RequestParam("watchListId") int theId,  @CurrentSecurityContext(expression="authentication?.name") String username) {

        watchListService.removeFromWatchList(username,theId);

        return "redirect:/anime-weeb/admin/watch-list-user";

    }

    @GetMapping("/anime-weeb/user/add-to-watch-list")
    public String addUserView(@RequestParam("watchListId") int theId,  @CurrentSecurityContext(expression="authentication?.name") String username) {

        watchListService.addToWatchList(username,theId);

        return "redirect:/anime-weeb/user/anime-list";

    }
}
