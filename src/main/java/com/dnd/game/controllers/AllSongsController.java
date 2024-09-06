package com.dnd.game.controllers;

import com.dnd.game.entities.Song;
import com.dnd.game.other.Option;
import com.dnd.game.services.SongService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AllSongsController {

    private final SongService songService;

    public AllSongsController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/all-songs")
    public String getSongs(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "15") int size,
                           @RequestParam(value="sortField", required = false, defaultValue="songName") String sortField,
                           @RequestParam(value="sortOrder", required = false, defaultValue="asc") String sortOrder,
                           @RequestParam(value = "search", required = false, defaultValue = "") String search,
                           Model model) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Page<Song> songs;
        if (search == null || search.isEmpty()) {
            songs = songService.getAllSongs(page, size, sort);
        } else {
            songs = songService.search(page, size, search, sort);
        }

        model.addAttribute("search", search);

        int totalPages = songs.getTotalPages();

        //ебанутый костыль какой-то, переписать надо

        List<Option> sortFieldOptions = new ArrayList<>();
        sortFieldOptions.add(new Option("rating", "Rating", sortField.equals("rating")));
        sortFieldOptions.add(new Option("createdDate", "Date", sortField.equals("createdDate")));
        sortFieldOptions.add(new Option("songName", "Song", sortField.equals("songName")));
        sortFieldOptions.add(new Option("bandName", "Band", sortField.equals("bandName")));

        List<Option> sortOrderOptions = new ArrayList<>();
        sortOrderOptions.add(new Option("asc", "Ascending", sortOrder.equals("asc")));
        sortOrderOptions.add(new Option("desc", "Descending", sortOrder.equals("desc")));

        model.addAttribute("sortFieldOptions", sortFieldOptions);
        model.addAttribute("sortOrderOptions", sortOrderOptions);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);

        model.addAttribute("songs", songs);
        model.addAttribute("currentPage", page + 1); // передаем как 1-based индекс
        model.addAttribute("totalPages", totalPages);

        // Условие для наличия ссылок "First" и "Previous"
        model.addAttribute("showFirst", page > 0);
        model.addAttribute("showPrevious", page > 0);
        model.addAttribute("previousPage", page > 0 ? page - 1 : 0);

        // Условие для наличия ссылок "Next" и "Last"
        model.addAttribute("showNext", page < totalPages - 1);
        model.addAttribute("nextPage", page < totalPages - 1 ? page + 1 : totalPages - 1);

        return "all-songs";
    }


}
