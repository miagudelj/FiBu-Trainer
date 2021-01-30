package ch.fibutrainer.fibu.src.data;

import java.util.List;

public interface Dao<T, K>{
    default List<T> getAll() {
        throw new UnsupportedOperationException();
    }
    default T getEntity(K k) {
        throw new UnsupportedOperationException();
    }
    /*default Result save(T t) {
        throw new UnsupportedOperationException();
    }
    default Result delete(K k) {
        throw new UnsupportedOperationException();
    }*/
    default Integer count() {
        return 0;
    }
}
