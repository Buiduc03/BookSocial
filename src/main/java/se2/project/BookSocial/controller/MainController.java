package se2.project.BookSocial.controller;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import se2.project.BookSocial.model.*;
import se2.project.BookSocial.repository.*;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    FollowRepository followRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookshelfRepository bookshelfRepository;

    @GetMapping("/")
    public String getHome(
            @AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        List<Book> books = bookRepository.findAll();
        List<Bookshelf> bookshelves = bookshelfRepository.findAll();
        List<User> otherUsers = userRepository.findAll();
        User currentUser = userRepository.getById(myUserDetails.getId());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("otherUsers", otherUsers);
        model.addAttribute("books", books);
        model.addAttribute("bookshelves", bookshelves);
        return "home";
    }

    @GetMapping("/browse")
    public String getBrowse(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "browse";
    }

    @GetMapping("/mybooks")
    public String getMyBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "mybooks";
    }
    @RequestMapping(value = "/user/{id}")
    public String getUserById(
            @PathVariable(value = "id") Long id,
            @AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        User user = userRepository.getById(id);
        User currentUser = userRepository.getById(myUserDetails.getId());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("otherUser", user);
        return "otherUser";
    }

    @RequestMapping(value = "/followUser")
    public String followUser(
            @Valid User user,
            @AuthenticationPrincipal MyUserDetails myUserDetails, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/user/" + user.getId();
        } else {
            User follower = userRepository.getById(user.getId());
            User followed = userRepository.getById(myUserDetails.getId());
            Follow checkIfExisted = followRepository.findByFollowerAndFollowed(followed, follower);
            Follow follow = new Follow();
            follow.setFollower(followed);
            follow.setFollowed(follower);
            model.addAttribute("checkIfExisted", checkIfExisted);
            if (checkIfExisted == null) {
                followRepository.save(follow);
            }
        }
        return "redirect:/user/" + user.getId();
    }
    @RequestMapping(value = "/unFollowUser")
    public String unFollowUser(
            @Valid User user,
            @AuthenticationPrincipal MyUserDetails myUserDetails, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/user/" + user.getId();
        }

        User follower = userRepository.findById(myUserDetails.getId()).orElse(null);
        User followed = userRepository.findById(user.getId()).orElse(null);

        if (follower != null && followed != null) {
            Follow follow = followRepository.findByFollowerAndFollowed(follower, followed);
            if (follow != null) {
                followRepository.delete(follow);
            }
        }
        return "redirect:/user/" + user.getId();
    }

    @GetMapping("/browse/toprated")
    public String getTopRatedBook(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "toprated";
    }

    @GetMapping("/user")
    public String manipulateUser(
            @AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        User user = userRepository.getById(myUserDetails.getId());
        model.addAttribute("user", user);
        return "user";
    }

    @RequestMapping(value = "/saveUser")
    public String saveUpdate(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user";
        }
        userRepository.save(user);
        return "redirect:/user";
    }

    @GetMapping("/browse/trending")
    public String getTrending(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "trending";
    }

    @GetMapping("/browse/genres")
    public String getGenres(Model model) {
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("genres", genres);
        return "genres";
    }

    @GetMapping("/browse/author")
    public String getAuthor() {
        return "author";
    }

    @GetMapping("/quotes")
    public String getQuotes() {
        return "quotes";
    }

    @GetMapping("/quotes/myquotes")
    public String getMyQuotes() {
        return "myquotes";
    }

    @GetMapping("/quotes/addquotes")
    public String getAddQuotes() {
        return "addquotes";
    }
}
