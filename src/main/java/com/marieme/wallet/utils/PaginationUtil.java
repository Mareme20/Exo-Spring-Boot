package com.marieme.wallet.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Utilitaire centralisant les parametres de pagination de l'API.
 */
public final class PaginationUtil {

    private PaginationUtil() {
        // classe utilitaire, pas d'instanciation
    }

    /** Taille de page par defaut */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /** Taille de page maximale autorisee */
    public static final int MAX_PAGE_SIZE = 100;

    /** Tri par defaut : id ascendant */
    public static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "id");

    /**
     * Cree un Pageable avec validation de la taille.
     * Si la taille depasse MAX_PAGE_SIZE, elle est limitee.
     */
    public static Pageable of(int page, int size, Sort sort) {
        int validatedSize = Math.min(size, MAX_PAGE_SIZE);
        return PageRequest.of(page, validatedSize, sort);
    }

    /**
     * Cree un Pageable avec les valeurs par defaut.
     */
    public static Pageable defaultPageable() {
        return PageRequest.of(0, DEFAULT_PAGE_SIZE, DEFAULT_SORT);
    }

    /**
     * Valide et normalise un Pageable.
     * Garantit que la taille ne depasse pas MAX_PAGE_SIZE.
     */
    public static Pageable normalize(Pageable pageable) {
        if (pageable == null) {
            return defaultPageable();
        }
        int size = Math.min(pageable.getPageSize(), MAX_PAGE_SIZE);
        return PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());
    }
}
