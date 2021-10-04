    package Comparator;

    import Elements.Movie;

    import java.util.Comparator;

    class MovieComparator implements Comparator<Movie> {
        public int compare(Movie s1, Movie s2) {
            if (s1.hashCode() > s2.hashCode())
                return 1;
            else if (s1.hashCode() < s2.hashCode())
                return -1;
            return 0;
        }
    }