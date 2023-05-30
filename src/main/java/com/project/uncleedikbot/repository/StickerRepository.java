package com.project.uncleedikbot.repository;

import com.project.uncleedikbot.entity.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface StickerRepository extends PagingAndSortingRepository<Sticker,Long> {

    List<Sticker> findAllByChatId(long chatId);

    long count();

    @Query(value = "select * from  sticker  where is_active = true and chat_id = :chatId ",nativeQuery = true)
    Sticker findByChatIdAndIsActiveTrue(@Param("chatId") long chatId);
}
