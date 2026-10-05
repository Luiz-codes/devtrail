package com.luiz.devtrail.study;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateStudyLogRequest(
        @NotBlank(message = "O tópico é obrigatório")
        @Size(max = 120, message = "O tópico deve ter no máximo 120 caracteres")
        String topic,

        String description,

        @NotNull(message = "A duração em minutos é obrigatória")
        @Min(value = 1, message = "A duração mínima é de 1 minuto")
        Integer durationMinutes,

        @NotNull(message = "A data do estudo é obrigatória")
        LocalDate studyDate
) {
}