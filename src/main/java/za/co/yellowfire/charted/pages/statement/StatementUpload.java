package za.co.yellowfire.charted.pages.statement;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.upload.services.UploadedFile;
import za.co.yellowfire.charted.domain.MenuItem;
import za.co.yellowfire.charted.domain.MenuSection;
import za.co.yellowfire.charted.domain.Statement;
import za.co.yellowfire.charted.pages.budget.AbstractBudgetPage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class StatementUpload extends AbstractBudgetPage {
    @Property
    private UploadedFile file;

    @PersistenceContext
    private EntityManager em;

    @Persist(PersistenceConstants.FLASH)
    @Property
    private String message;


    Object onUploadException(FileUploadException ex) {
        message = "Upload exception: " + ex.getMessage();
        return this;
    }

    public void onSuccess() throws Exception {
        Statement.importStatement(em, file.getStream());
        message = "Uploaded " + file.getFileName() + " successfully";
    }

//    public List<MenuSection> getMenuSections() {
//        return Arrays.asList(
//                new MenuSection(
//                        "Budgeting",
//                        new MenuItem[]{
//                                new MenuItem("Current Budget", "budget/current"),
//                                new MenuItem("Statement Upload", "statement/upload"),
//                        })
//        );
//    }
}
