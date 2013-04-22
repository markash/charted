package za.co.yellowfire.charted.domain;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
@Entity(name = "budgetSection")
@Table(name = "budget_section")
@Access(AccessType.FIELD)
//@IdClass(BudgetSectionId.class)
//@ChangeTracking(ChangeTrackingType.OBJECT)
@NamedQueries({
        @NamedQuery(
                name = "qry.budget.sections",
                query = "select bs from budgetSection bs"
        ),
        @NamedQuery(
                name = "qry.budget.section.forId",
                query = "select bs from budgetSection bs where bs.id = :id"
        )
})
public class BudgetSection implements Serializable, Comparable<BudgetSection> {

    public static final String QRY_BUDGET_SECTIONS = "qry.budget.sections";
    public static final String QRY_BUDGET_SECTION_FOR_ID = "qry.budget.section.forId";

    public static final String PARAM_NAME = "name";

    public static final String DEFAULT_UNALLOCATED = "Unallocated";

    private static final BigDecimal ZERO = new BigDecimal("0.00");

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_identity")
    @Column(name = "section_id")
    private Long id;

    @Column(name = "name", length = 64)
    private String name;

    //@Id
    @ManyToOne(targetEntity = Budget.class)
    @JoinFetch(JoinFetchType.OUTER)
    @JoinColumn(name = "budget_date")
    private Budget budget;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction")
    private CashFlowDirection direction;

    @Column(name = "color", length = 32)
    private String color;

    @OneToMany(targetEntity = BudgetCategory.class, cascade = CascadeType.ALL)
    private List<BudgetCategory> categories;

    @Transient
    private BigDecimal actualAmount = ZERO;

    public BudgetSection() { }

    public BudgetSection(Long id) {
        this.id = id;
    }

    public BudgetSection(String name, CashFlowDirection direction, String color) {
        this.name = name;
        this.direction = direction;
        this.color = color;
    }

    public BudgetSection(Long id, String name, CashFlowDirection direction, Color sectionColor, Color categoryColor, String...categories) {
        this.id = id;
        this.name = name;
        this.direction = direction;
        this.color = sectionColor.name();
        this.categories = new ArrayList<BudgetCategory>();

        if (categories!= null && categories.length > 0) {
            for (String category : categories) {
                this.categories.add(new BudgetCategory(category, direction, categoryColor, this));
            }
        }
    }

    public static BudgetSection save(EntityManager entityManager, BudgetSection section) {
        entityManager.persist(section);
        return section;
    }

    public static za.co.yellowfire.charted.database.NamedQuery<BudgetSection> forBudgetSections(EntityManager entityManager) {
        return new za.co.yellowfire.charted.database.NamedQuery<BudgetSection>(entityManager, Transaction.class, QRY_BUDGET_SECTIONS);
    }

    public static za.co.yellowfire.charted.database.NamedQuery<BudgetSection> forUniqueId(EntityManager entityManager, String name) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put(PARAM_NAME, name);
        return new za.co.yellowfire.charted.database.NamedQuery<BudgetSection>(entityManager, Transaction.class, QRY_BUDGET_SECTION_FOR_ID, params);
    }

    public static za.co.yellowfire.charted.database.NamedQuery<BudgetSection> forId(EntityManager entityManager, String id) {
        Map<String, Object> params = new HashMap<String, Object>(1);
        params.put(PARAM_NAME, id);
        return new za.co.yellowfire.charted.database.NamedQuery<BudgetSection>(entityManager, Transaction.class, QRY_BUDGET_SECTION_FOR_ID, params);
    }

    public static BudgetSection findUnallocatedBudgetSection(EntityManager entityManager) {
        return entityManager.find(BudgetSection.class, DEFAULT_UNALLOCATED);
    }

    public static BudgetSection persistUnallocatedBudgetSection(EntityManager entityManager) {
        BudgetSection section = findUnallocatedBudgetSection(entityManager);
        if (section == null) {
            section = new BudgetSection(null, DEFAULT_UNALLOCATED, CashFlowDirection.EXPENSE, Color.medium_faded_red, Color.medium_faded_red, BudgetCategory.DEFAULT_UNALLOCATED);
            save(entityManager, section);
        }
        return section;
    }

    public Long getId() {
        return id;
    }

    void setBudget(Budget budget) {
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public CashFlowDirection getDirection() {
        return direction;
    }

    public List<BudgetCategory> getCategories() {
        return new ArrayList<BudgetCategory>(categories);
    }

    public void setCategories(List<BudgetCategory> categories) {
        this.categories = new ArrayList<BudgetCategory>(categories);
        for (BudgetCategory category : this.categories) {
            category.setSection(this);
        }
    }

    public BudgetCategory getCategory(String name) {
        for (BudgetCategory category : categories) {
            if (category != null && category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public String getColor() {
        return color;
    }

    public BigDecimal getBudgetAmount() {
        BigDecimal amount = ZERO;
        for (BudgetCategory category : categories) {
            amount = amount.add(category.getBudgetAmount());
        }
        return amount;
    }

    public BigDecimal getActualAmount() {
        BigDecimal amount = ZERO;
        for (BudgetCategory category : categories) {
            amount = amount.add(category.getActualAmount());
        }
        return amount;
    }

    public BigDecimal getVarianceAmount() {
        final BigDecimal budgetAmount = getBudgetAmount();
        final BigDecimal actualAmount = getActualAmount();

        if (this.direction == CashFlowDirection.EXPENSE) {
            return (budgetAmount != null ? budgetAmount.subtract((actualAmount != null ? actualAmount : ZERO)) : ZERO);
        } else {
            return (actualAmount != null ? actualAmount.subtract((budgetAmount != null ? budgetAmount : ZERO)) : ZERO);
        }
    }

    public void allocate(List<Transaction> transactions) {
        this.actualAmount = new BigDecimal("0.00");

        for (BudgetCategory category : categories) {
            category.allocate(transactions);
            this.actualAmount = this.actualAmount.add(category.getActualAmount().abs());
        }
    }

    @Override
    public int compareTo(BudgetSection o) {
        String other = o == null ? "" : (o.getName() != null ? o.getName() : "");
        String current = name != null ? name : "";
        return current.compareTo(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BudgetSection that = (BudgetSection) o;

        return !(name != null ? !name.equals(that.name) : that.name != null);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BudgetSection {" +
                "name='" + name + '\'' +
                ", direction=" + direction +
                '}';
    }
}
