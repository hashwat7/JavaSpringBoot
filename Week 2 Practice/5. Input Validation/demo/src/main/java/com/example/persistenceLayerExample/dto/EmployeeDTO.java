package com.example.persistenceLayerExample.dto;

import com.example.persistenceLayerExample.annotations.EmployeeRoleValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "Required field in employee can't be blank")
    @Size(min = 3, max = 10)
    private String name;

    @NotBlank(message = "Email of employee can't be blank")
    @Email(message = "Email should be a valid email")
    private String email;

    @NotBlank(message = "Age of employee can't be blank")
    @Max(value = 80, message = "Age can't be greater than 80")
    @Min(value = 18, message = "Age can't be less than 18")
    private Integer age;

    @NotBlank(message = "Role of employee can't be blank")
//    @Pattern(regexp = "^(ADMIN|USER)$",message = "Role can either ADMIN or USER")
    @EmployeeRoleValidation
    private String role;


    @NotNull(message = "Salary of employee can't be null")
    @Positive(message = "Salary of employee can't be negative")
    @Digits(integer = 6,fraction = 2, message = "The salary can be in the form XXXXXX.YY")
    @DecimalMax(value = "100000.99")
    @DecimalMin(value = "1000.50")
    private Double salary;

    @PastOrPresent(message = "DOJ can't be in the future")
    private LocalDate dateOfJoining;

    @AssertTrue(message = "Employee should be active")
    @JsonProperty("isActive")
    private Boolean isActive;


}
