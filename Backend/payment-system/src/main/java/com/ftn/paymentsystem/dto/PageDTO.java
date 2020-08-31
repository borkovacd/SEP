package com.ftn.paymentsystem.dto;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> {

    private int currentPage;
    private int itemsPerPage;
    private long totalItems;
    private Collection<T> items;

}