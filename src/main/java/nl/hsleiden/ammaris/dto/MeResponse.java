package nl.hsleiden.ammaris.dto;

public record MeResponse(
        String email,
        String role // "ADMIN" or "USER"
) {}
