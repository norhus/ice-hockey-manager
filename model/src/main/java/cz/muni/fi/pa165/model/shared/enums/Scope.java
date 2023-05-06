package cz.muni.fi.pa165.model.shared.enums;

public enum Scope {

    TEST_WRITE("SCOPE_test_write"),
    TEST_READ("SCOPE_test_read");

    public static final String SCOPE_TEST_READ = "SCOPE_test_read";
    public static final String SCOPE_TEST_WRITE = "SCOPE_test_write";
    public final String label;

    private Scope(String label) {
        this.label = label;
    }
}
