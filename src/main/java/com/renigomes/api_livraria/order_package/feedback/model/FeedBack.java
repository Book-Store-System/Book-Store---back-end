package com.renigomes.api_livraria.order_package.feedback.model;

import com.renigomes.api_livraria.book_package.book.model.BookStock;
import com.renigomes.api_livraria.user_package.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "feedback")
@Getter
@Setter
public class FeedBack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_stock_id")
    private BookStock bookStock;

    private String feedback;
}
