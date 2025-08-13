package br.com.codeelevate.ce_sage_catalog.service;

import br.com.codeelevate.ce_sage_catalog.client.ConsultBooksClient;
import br.com.codeelevate.ce_sage_catalog.model.Book;
import br.com.codeelevate.ce_sage_catalog.model.dto.consult.Counter;
import br.com.codeelevate.ce_sage_catalog.repository.BookConsultRepository;
import br.com.codeelevate.ce_sage_catalog.service.impl.BookConsultServiceImpl;
import br.com.codeelevate.ce_sage_catalog.service.impl.BookInsertServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookInsertServiceImplTest {

    @InjectMocks
    private BookInsertServiceImpl service;

    @Mock
    private BookConsultRepository repository;

    @Mock
    private ConsultBooksClient consultBooksClient;

    @Mock
    private MongoOperations mongoOperations;

    @Test
    void insertBooksByAuthor_shouldInsertOnlyNewBooks() throws Exception {
        String genre = "fiction";

        String booksJson = "{\n" +
                "    \"key\": \"/subjects/action\",\n" +
                "    \"name\": \"action\",\n" +
                "    \"subject_type\": \"subject\",\n" +
                "    \"work_count\": 522,\n" +
                "    \"works\": [\n" +
                "        {\n" +
                "            \"key\": \"/works/OL82586W\",\n" +
                "            \"title\": \"Harry Potter and the Deathly Hallows\",\n" +
                "            \"edition_count\": 125,\n" +
                "            \"cover_id\": 10110415,\n" +
                "            \"cover_edition_key\": \"OL28172760M\",\n" +
                "            \"subject\": [\n" +
                "                \"the Elder Wand\",\n" +
                "                \"children's books\",\n" +
                "                \"dementors\",\n" +
                "                \"good and evil\",\n" +
                "                \"Juvenile literature\",\n" +
                "                \"Juvenile works\",\n" +
                "                \"Death\",\n" +
                "                \"Fiction\",\n" +
                "                \"Wizards\",\n" +
                "                \"New York Times bestseller\",\n" +
                "                \"nyt:series_books=2006-09-16\",\n" +
                "                \"Schools\",\n" +
                "                \"Magic\",\n" +
                "                \"Magia\",\n" +
                "                \"Ficción juvenil\",\n" +
                "                \"Novela fantástica\",\n" +
                "                \"Magos\",\n" +
                "                \"Escuelas\",\n" +
                "                \"Juvenile fiction\",\n" +
                "                \"dark magic\",\n" +
                "                \"Coming of age\",\n" +
                "                \"heroics\",\n" +
                "                \"fantasy\",\n" +
                "                \"action\",\n" +
                "                \"adventure\",\n" +
                "                \"orphans\",\n" +
                "                \"foster homes\",\n" +
                "                \"young adult\",\n" +
                "                \"children\",\n" +
                "                \"children's literature\",\n" +
                "                \"boarding school\",\n" +
                "                \"wizardry\",\n" +
                "                \"mystery\",\n" +
                "                \"kids\",\n" +
                "                \"witchcraft\",\n" +
                "                \"war\",\n" +
                "                \"Magie\",\n" +
                "                \"Magiciens\",\n" +
                "                \"Roman pour la jeunesse\",\n" +
                "                \"Mort\",\n" +
                "                \"Sorcellerie\",\n" +
                "                \"Romans, nouvelles, etc. pour la jeunesse\",\n" +
                "                \"Internats\",\n" +
                "                \"Fantasy fiction\",\n" +
                "                \"Roman fantastique\",\n" +
                "                \"Ecoles\",\n" +
                "                \"Boarding schools\",\n" +
                "                \"Sorciers\",\n" +
                "                \"Hogwarts School of Witchcraft and Wizardry (Imaginary place) -- Juvenile fiction\",\n" +
                "                \"Hogwarts School of Witchcraft and Wizardry (Imaginary place)\",\n" +
                "                \"Harry Potter (Fictitious character)\",\n" +
                "                \"Potter, Harry (Fictitious character) -- Juvenile fiction\",\n" +
                "                \"Wizards -- Fiction\",\n" +
                "                \"Magic -- Fiction\",\n" +
                "                \"Schools -- Fiction\",\n" +
                "                \"Magos -- Ficción juvenil\",\n" +
                "                \"Magia -- Ficción juvenil\",\n" +
                "                \"Escuelas -- Ficción juvenil\",\n" +
                "                \"England -- Fiction\",\n" +
                "                \"Inglaterra -- Ficción juvenil\",\n" +
                "                \"Hermione Granger (Fictitious character)\",\n" +
                "                \"Hogwarts School of Witchcraft and Wizardry (Imaginary organization)\",\n" +
                "                \"Ron Weasley (Fictitious character)\",\n" +
                "                \"School stories\",\n" +
                "                \"Family\",\n" +
                "                \"Orphans & Foster Homes\",\n" +
                "                \"Social Themes\",\n" +
                "                \"Fantasy & Magic\",\n" +
                "                \"Fictional Works\",\n" +
                "                \"Bildungsromans\",\n" +
                "                \"Witches\",\n" +
                "                \"Friendship\",\n" +
                "                \"Reading Level-Grade 9\",\n" +
                "                \"Reading Level-Grade 11\",\n" +
                "                \"Reading Level-Grade 10\",\n" +
                "                \"Reading Level-Grade 12\",\n" +
                "                \"Potter, harry (fictitious character), fiction\",\n" +
                "                \"Wizards, fiction\",\n" +
                "                \"Hogwarts school of witchcraft and wizardry (imaginary organization), fiction\",\n" +
                "                \"England, fiction\",\n" +
                "                \"Schools, fiction\",\n" +
                "                \"Magic, fiction\",\n" +
                "                \"Children's fiction\",\n" +
                "                \"English literature\",\n" +
                "                \"Fiction, fantasy, general\",\n" +
                "                \"Méchanceté\",\n" +
                "                \"Quête (Littérature)\",\n" +
                "                \"Potter, Harry (Personnage fictif)\",\n" +
                "                \"Large type books\",\n" +
                "                \"New York Times reviewed\",\n" +
                "                \"Poudlard (Organisation imaginaire)\",\n" +
                "                \"Écoles\",\n" +
                "                \"Illusion (performing art)\",\n" +
                "                \"Schools (institutions)\",\n" +
                "                \"Schools (buildings)\",\n" +
                "                \"Child and youth fiction\",\n" +
                "                \"death eater\",\n" +
                "                \"ron\",\n" +
                "                \"Severus Snape (Fictitious character)\"\n" +
                "            ],\n" +
                "            \"ia_collection\": [\n" +
                "                \"JaiGyan\",\n" +
                "                \"ServantsOfKnowledge-Print\",\n" +
                "                \"americana\",\n" +
                "                \"americanuniversity-ol\",\n" +
                "                \"bannedbooks\",\n" +
                "                \"barryuniversity-ol\",\n" +
                "                \"belmont-ol\",\n" +
                "                \"bpljordan-ol\",\n" +
                "                \"cnusd-ol\",\n" +
                "                \"cua-ol\",\n" +
                "                \"dartmouthlibrary-ol\",\n" +
                "                \"delawarecountydistrictlibrary\",\n" +
                "                \"delawarecountydistrictlibrary-ol\",\n" +
                "                \"denverpubliclibrary-ol\",\n" +
                "                \"drakeuniversity-ol\",\n" +
                "                \"framingham-ol\",\n" +
                "                \"goffstownlibrary-ol\",\n" +
                "                \"gwulibraries-ol\",\n" +
                "                \"internetarchivebooks\",\n" +
                "                \"ithacacollege-ol\",\n" +
                "                \"johnshopkins-ol\",\n" +
                "                \"marygrovecollege\",\n" +
                "                \"marymount-ol\",\n" +
                "                \"occidentalcollegelibrary-ol\",\n" +
                "                \"openlibrary-d-ol\",\n" +
                "                \"popularchinesebooks\",\n" +
                "                \"printdisabled\",\n" +
                "                \"rochester-ol\",\n" +
                "                \"salisburyfreelibrary-ol\",\n" +
                "                \"spokanepubliclibrary-ol\",\n" +
                "                \"the-claremont-colleges-ol\",\n" +
                "                \"tulsacc-ol\",\n" +
                "                \"uhmauicollege-ol\",\n" +
                "                \"unb-ol\",\n" +
                "                \"uni-ol\",\n" +
                "                \"universityofarizona-ol\",\n" +
                "                \"universityofcoloradoboulder-ol\",\n" +
                "                \"universityofoklahoma-ol\",\n" +
                "                \"universityofthewest-ol\",\n" +
                "                \"wilsoncollege-ol\",\n" +
                "                \"worthingtonlibraries-ol\"\n" +
                "            ],\n" +
                "            \"printdisabled\": true,\n" +
                "            \"lending_edition\": \"\",\n" +
                "            \"lending_identifier\": \"\",\n" +
                "            \"authors\": [\n" +
                "                {\n" +
                "                    \"key\": \"/authors/OL23919A\",\n" +
                "                    \"name\": \"J. K. Rowling\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"first_publish_year\": 2007,\n" +
                "            \"ia\": \"harrypotter07har0000joan\",\n" +
                "            \"public_scan\": false,\n" +
                "            \"has_fulltext\": true,\n" +
                "            \"availability\": {\n" +
                "                \"status\": \"private\",\n" +
                "                \"available_to_browse\": false,\n" +
                "                \"available_to_borrow\": false,\n" +
                "                \"available_to_waitlist\": false,\n" +
                "                \"is_printdisabled\": true,\n" +
                "                \"is_readable\": false,\n" +
                "                \"is_lendable\": false,\n" +
                "                \"is_previewable\": true,\n" +
                "                \"identifier\": \"harrypotter07har0000joan\",\n" +
                "                \"isbn\": \"9783551313171\",\n" +
                "                \"oclc\": null,\n" +
                "                \"openlibrary_work\": \"OL35271370W\",\n" +
                "                \"openlibrary_edition\": \"OL47682327M\",\n" +
                "                \"last_loan_date\": null,\n" +
                "                \"num_waitlist\": null,\n" +
                "                \"last_waitlist_date\": null,\n" +
                "                \"is_restricted\": true,\n" +
                "                \"is_browseable\": false,\n" +
                "                \"__src__\": \"core.models.lending.get_availability\"\n" +
                "            }\n" +
                "        }]}\n";

        // Mock do método que consulta os livros
        when(consultBooksClient.consultBooksByAuthor(genre)).thenReturn(booksJson);
        when(repository.findByTitle("Harry Potter and the Deathly Hallows")).thenReturn(Optional.of(new ArrayList<Book>()));
        Counter counter = new Counter();
        counter.setSeq(1); // valor de exemplo

        when(mongoOperations.findAndModify(
                argThat(query -> query.getQueryObject().get("_id").equals("subject_seq")),
                any(Update.class),
                any(FindAndModifyOptions.class),
                eq(Counter.class)))
                .thenReturn(counter);
        service.insertBooksByGenre(genre);

    }
}
