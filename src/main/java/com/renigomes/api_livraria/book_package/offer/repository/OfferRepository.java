package com.renigomes.api_livraria.book_package.offer.repository;

import com.renigomes.api_livraria.book_package.offer.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}
