package socialnetwork.repository.paging;
import java.util.stream.Stream;

public interface Page<E> {
    Pageable getPageable();

    Stream<E> getContent();


}
