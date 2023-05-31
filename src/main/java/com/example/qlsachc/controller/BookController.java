package com.example.qlsachc.controller;

import com.example.qlsachc.entity.Book;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.lang.Long;

//mport java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Controller
//@RequestMapping("/books")
public class BookController {

    @Autowired
    private List<Book> books;

    @GetMapping("/")
    public  String listBooks(Model model){
        model.addAttribute("books",books);
        model.addAttribute("title","Book List");
        return "book/list";

    }

    @GetMapping("/add")
    public String addBookForm(Model model){
        model.addAttribute("book",new Book());
        return "book/add";
    }
    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book){
        books.add(book);
        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable("id") Long id, Model model) {
        Optional<Book> editBook = books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
        if (editBook.isPresent()) {
            model.addAttribute("book", editBook.get());
            return "book/edit";

        } else {
            return "not-found";
        }

    }
    @PostMapping("/edit")
    public String editBook (@ModelAttribute("book") Book updateBook){
        books.stream()
                .filter(book -> book.getId() == updateBook.getId())
                .findFirst()
                .ifPresent(book -> books.set(books.indexOf(book), updateBook));
        return "redirect:/";
    }

    @GetMapping("delete/{id}")
    public  String deleteBook(@PathVariable("id") Long id){
        books.removeIf(book -> book.getId().equals(id));
        return "redirect:/";
    }
}
