package br.com.codeelevate.ce_sage_catalog.model.redis;

import kotlinx.serialization.Serializable;
import lombok.Builder;

@Serializable
@Builder
public class RedisBook {
    private String _id;
    private String title;
    private String author;
    private String publisher;
    private String genre;
    private String subGenre;

    private RedisBook(String _id, String title, String author, String publisher, String genre, String subGenre){
        this._id = _id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.subGenre = subGenre;
    }
}
