package com.project.blog.payloads;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@NoArgsConstructor
@Getter
public class CategoryDto {

    private Integer categoryId;
    @NotEmpty
    @Size(min=2,message="Title min should have 2 characters")
    private String categoryTitle;
    @NotEmpty
    @Size(min=5,message="Description min should have 5 characters")
    private String categoryDescription;
}
