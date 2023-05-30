package com.project.uncleedikbot.repository;

import com.project.uncleedikbot.entity.StickerContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StickerContextRepository extends JpaRepository<StickerContext,Long> {

    StickerContext findTopByChatId(long chatId);


}
