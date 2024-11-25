package com.example.homework2.dtos;

import com.example.homework2.annotations.PrimeOrNotValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    @PrimeOrNotValidation
    private Long prime;
    private  Long id;
    @NotBlank(message = "Title is required & can't be blank")
    @Size(min = 4, max = 15, message = "Tittle size should range between 4 to 15")
    private String title;
    @AssertTrue(message = "Department should be active")
    @JsonProperty("isActive")
    private Boolean isActive;
    @PastOrPresent(message = "Department creation can't be in future")
    private LocalDate createdAt;
}
