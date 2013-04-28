package za.co.yellowfire.charted.domain;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.annotations.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
@Entity(name = "budgetCategory")
@Table(name = "budget_category")
@Access(AccessType.FIELD)
//@ChangeTracking(ChangeTrackingType.OBJECT)
@NamedQueries({
        @NamedQuery(
                name = "qry.budget.categories",
                query = "select b from budgetCategory b"
        ),
        @NamedQuery(
                name = "qry.budget.category.forId",
                query = "select b from budgetCategory b where b.id = :id"
        ),
        @NamedQuery(
                name = "qry.budget.category.forSectionName",
                query = "select b from budgetCategory  b where b.section.name = :section"
        )
})
public class BudgetCategory implements Serializable, Comparable<BudgetCategory> {

    public static final String QRY_BUDGET_CATEGORIES = "qry.budget.categories";
    public static final String QRY_BUDGET_CATEGORY_FOR_ID = "qry.budget.category.forId";
    public static final String QRY_BUDGET_CATEGORY_FOR_SECTION_NAME = "qry.budget.category.forSectionName";

    public static final String PARAM_ID = "id";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_SECTION = "section";

    public static final String DEFAULT_UNALLOCATED = "Unallocated";
    private static final String MATCHES_SEPARATOR = "|";
    private static final String ANY = "%";
    private static final BigDecimal ZERO = new BigDecimal("0.00");

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_identity")
    @Column(name = "category_id")
    private Long id;

    @Column(name = "name", length = 64)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction")
    private CashFlowDirection direction;

    @Column(name = "color", length = 32)
    private String color;

    @Column(name = "budget_amount")
    private BigDecimal budgetAmount = ZERO;

    @Transient
    private BigDecimal actualAmount = ZERO;

    @ManyToOne
    @JoinFetch(JoinFetchType.OUTER)
    @JoinColumns({
        @JoinColumn(name = "budget_section", referencedColumnName = "name"),
        @JoinColumn(name = "budget_date", referencedColumnName = "budget_date")
    })
    private BudgetSection section;

    @Column(name = "matches", length = 2048)
    private String matches;

    private Date created;

    private Date updated;

    public BudgetCategory() { }

    public BudgetCategory(Long id) {
        this.id = id;
    }

    public BudgetCategory(String name, CashFlowDirection direction, Color color, BudgetSection budgetSection) {
        this(null, name, direction, color, ZERO, budgetSection, new String[0]);
    }

    public BudgetCategory(Long id, String name, CashFlowDirection direction, Color color, BigDecimal budgetAmount, BudgetSection budgetSection, String matches) {
        this(id, name, direction, color, budgetAmount, budgetSection, StringUtils.split(matches, MATCHES_SEPARATOR));
    }

    public BudgetCategory(Long id, String name, CashFlowDirection direction, Color color, BigDecimal budgetAmount, BudgetSection budgetSection, String[] matches) {
        this.id = id;
        this.name = name;
        this.direction = direction;
        this.section = budgetSection;
        this.color = color.name();
        this.budgetAmount = budgetAmount;
        this.actualAmount = ZERO;
        this.matches = StringUtils.join(matches, MATCHES_SEPARATOR);
    }

    public static BudgetCategory save(EntityManager entityManager, BudgetCategory category) {
        entityManager.persist(category);
        return category;
    }

    public static za.co.yellowfire.charted.database.NamedQuery<BudgetCategory> forBudgetCategories(EntityManager entityManager) {
        return new za.co.yellowfire.charted.database.NamedQuery<BudgetCategory>(entityManager, BudgetCategory.class, QRY_BUDGET_CATEGORIES);
    }

    public static za.co.yellowfire.charted.database.NamedQuery<BudgetCategory> forUniqueId(EntityManager entityManager, String name) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put(PARAM_NAME, name);
        return new za.co.yellowfire.charted.database.NamedQuery<BudgetCategory>(entityManager, BudgetCategory.class, QRY_BUDGET_CATEGORY_FOR_ID, params);
    }

    public static BudgetCategory forId(EntityManager entityManager, Long id) {
        return entityManager.find(BudgetCategory.class, id);
    }

    public static za.co.yellowfire.charted.database.NamedQuery<BudgetCategory> forSectionName(EntityManager entityManager, String name) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put(PARAM_SECTION, name);
        return new za.co.yellowfire.charted.database.NamedQuery<BudgetCategory>(entityManager, BudgetCategory.class, QRY_BUDGET_CATEGORY_FOR_SECTION_NAME, params);
    }

    public static BudgetCategory findUnallocatedBudgetCategory(EntityManager entityManager) {
        return entityManager.find(BudgetCategory.class, DEFAULT_UNALLOCATED);
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public BudgetSection getSection() {
        return section;
    }

    public void setSection(BudgetSection section) {
        this.section = section;
    }

    public String getSectionName() {
        return getSection() != null ? getSection().getName() : null;
    }

    public CashFlowDirection getDirection() {
        return direction;
    }

    public String getColor() {
        return color;
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public BigDecimal getVarianceAmount() {
        if (this.direction == CashFlowDirection.EXPENSE) {
            return (budgetAmount != null ? budgetAmount.subtract((actualAmount != null ? actualAmount : ZERO)) : ZERO);
        } else {
            return (actualAmount != null ? actualAmount.subtract((budgetAmount != null ? budgetAmount : ZERO)) : ZERO);
        }
    }

    public String getMatchesValue() {
        return this.matches;
    }

    public List<String> getMatches() {
        if (StringUtils.isNotEmpty(this.matches)) {
            return new ArrayList<String>(Arrays.asList(StringUtils.split(this.matches, MATCHES_SEPARATOR)));
        } else {
            return new ArrayList<String>(0);
        }
    }

    public void setMatches(final List<String> matches) {
        List<String> x = new ArrayList<String>(matches);
        x.add(name);
        setRawMatches(x);
    }

    private void setRawMatches(List<String> matches) {
        this.matches = StringUtils.join(matches, MATCHES_SEPARATOR);
    }

    public void addMatch(String match) {
        List<String> x = getMatches();
        x.add(match);
        setRawMatches(x);
    }

    public void addRegExMatch(String match) {
        List<String> x = getMatches();
        x.add(match);
        setRawMatches(x);
    }

    public void allocate(List<Transaction> transactions) {
        this.actualAmount = ZERO;

//        for (Transaction transaction : transactions) {
//            if (transaction.getCategory().equals(this)) {
//                this.actualAmount = this.actualAmount.add(transaction.getAmount().setScale(2).abs());
//            }
//        }
    }

    /**
     * Determines if a Transaction is meant to be allocated to this BudgetCategory by determining if this category has a RegEx match to the Memo of the Transaction.
     * @param candidate The candidate transaction for matching
     * @return True if the transaction should be allocated to this category
     */
    public boolean matches(Transaction candidate) {
        if (candidate == null || candidate.getMemo() == null) { return false; }

        final String x = candidate.getMemo().toUpperCase();
        for(String match : getMatches()) {
            if (x.contains(match.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetCategory that = (BudgetCategory) o;

        return !(name != null ? !name.equals(that.name) : that.name != null);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public int compareTo(BudgetCategory o) {
        String other = o == null ? "" : (o.getName() != null ? o.getName() : "");
        String current = name != null ? name : "";
        return current.compareTo(other);
    }

    @Override
    public String toString() {
        return "BudgetCategory {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", direction=" + direction +
                '}';
    }
}
