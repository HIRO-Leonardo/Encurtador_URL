package com.encurtadorlink.V1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ShortenUrlRequest(@NotBlank(message = "...")
                                @Pattern(regexp = "...", message = "...")
                                String url) {
}
