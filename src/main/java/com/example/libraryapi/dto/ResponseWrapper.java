package com.example.libraryapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;

@Schema(description = "Wrapper for API responses, including data and version information")
public class ResponseWrapper<T> {
    @Nonnull
    @Schema(description = "Data of the response", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private T data;

    @Min(1)
    @Schema(description = "API version", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
    private int version;

    public ResponseWrapper(@Nonnull T data, @Min(1) int version) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (version < 1) {
            throw new IllegalArgumentException("Version must be at least 1");
        }

        this.data = data;
        this.version = version;
    }

    public T getData() {
        return data;
    }

    public void setData(@Nonnull T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        this.data = data;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(@Min(1) int version) {
        if (version < 1) {
            throw new IllegalArgumentException("Version must be at least 1");
        }
        this.version = version;
    }
}
