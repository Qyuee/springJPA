package com.qyuee.jpa2.springJPA.service;

import com.qyuee.jpa2.springJPA.domain.item.Book;
import com.qyuee.jpa2.springJPA.domain.item.Item;
import com.qyuee.jpa2.springJPA.execption.NotEnoughStockException;
import com.qyuee.jpa2.springJPA.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void 상품_저장_테스트() {
        // given
        Item book = new Book();
        book.setName("책 이름");
        book.setPrice(10000);
        book.setStockQuantity(10);

        // when
        itemService.saveItem(book);

        // then
        Assertions.assertEquals(book, itemRepository.findOne(book.getId()));
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void 재고_증감_테스트() {
        // given
        Item book = new Book();
        book.setName("책 이름");
        book.setPrice(10000);
        book.setStockQuantity(10);

        // when
        itemService.saveItem(book);
        book.removeStock(9);

        // then
        Assertions.assertEquals(itemRepository.findOne(book.getId()).getStockQuantity(), 1);
        Assertions.assertNotEquals(itemRepository.findOne(book.getId()).getStockQuantity(), 10);
    }

    @Test
    @DisplayName("재고가 0보다 작으면 NotEnoughStockException이 발생하는지 테스트")
    @Transactional
    @Rollback(value = false)
    void 재고_증감_예외_테스트() {
        // given
        Item book = new Book();
        book.setName("책 이름");
        book.setPrice(10000);
        book.setStockQuantity(10);

        // when
        itemService.saveItem(book);

        // then
        Assertions.assertThrows(NotEnoughStockException.class, () -> {
            book.removeStock(11);
        });
        Assertions.assertEquals(itemRepository.findOne(book.getId()).getStockQuantity(), 10);
    }
}