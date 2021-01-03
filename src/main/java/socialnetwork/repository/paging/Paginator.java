package socialnetwork.repository.paging;


import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class Paginator<E> {
    protected Pageable pageable;

    public Paginator(Pageable pageable) {
        this.pageable = pageable;
    }

    public abstract  Page<E> paginate();

}
