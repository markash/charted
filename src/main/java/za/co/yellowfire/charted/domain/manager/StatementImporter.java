package za.co.yellowfire.charted.domain.manager;

import java.io.InputStream;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public interface StatementImporter {
    /**
     * Imports the statement from the input stream
     * @param inputStream The input stream
     * @throws StatementImportException When there is an error importing the statement
     */
    void importStatement(InputStream inputStream) throws StatementImportException;
}
