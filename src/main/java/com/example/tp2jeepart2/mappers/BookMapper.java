package com.example.tp2jeepart2.mappers;

import com.example.tp2jeepart2.dtos.BookDTO;
import com.example.tp2jeepart2.entities.Book;
import com.example.tp2jeepart2.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {

    // 1. Map Author's Name: Look inside 'author' and find 'name'
    @Mapping(source = "author.name", target = "authorName")
    // 2. Map Category Names: We use a custom method for the list (see below)
    @Mapping(target = "categoryNames", expression = "java(mapCategories(book.getCategories()))")
    BookDTO toDto(Book book);

    Book toEntity(BookDTO dto);

    // Helper method to turn Set<Category> into List<String>
    default List<String> mapCategories(Set<Category> categories) {
        if (categories == null) return null;
        return categories.stream()
                .map(Category::getLabel)
                .collect(Collectors.toList());
    }
}