package com.erp.filestorage.exception;

import com.erp.shared.domain.DomainError;

public final class FileStorageUploadException extends DomainError {
    public FileStorageUploadException(String filename) {
        super("No se pudo cargar el archivo " + filename + " en el servidor de almacenamiento.");
    }
}
